/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.processors;

import org.apache.commons.lang3.StringUtils;
import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.api.definition.annotation.property.NameProperty;
import org.wirez.core.client.annotation.Shape;
import org.wirez.core.client.annotation.ShapeSet;
import org.wirez.core.client.util.SafeUriProvider;
import org.wirez.core.processors.definition.BindableDefinitionAdapterGenerator;
import org.wirez.core.processors.definitionset.BindableDefinitionSetAdapterGenerator;
import org.wirez.core.processors.definitionset.DefinitionSetProxyGenerator;
import org.wirez.core.processors.property.BindablePropertyAdapterGenerator;
import org.wirez.core.processors.propertyset.BindablePropertySetAdapterGenerator;
import org.wirez.core.processors.rule.*;
import org.wirez.core.processors.shape.BindableShapeFactoryGenerator;
import org.wirez.core.processors.shape.BindableShapeSetGenerator;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.*;

@SupportedAnnotationTypes({
        MainProcessor.ANNOTATION_DEFINITION_SET,
        MainProcessor.ANNOTATION_DEFINITION,
        MainProcessor.ANNOTATION_PROPERTY_SET,
        MainProcessor.ANNOTATION_PROPERTY, 
        MainProcessor.ANNOTATION_NAME_PROPERTY,
        MainProcessor.ANNOTATION_RULE_CAN_CONTAIN, 
        MainProcessor.ANNOTATION_RULE_ALLOWED_CONNECTION, 
        MainProcessor.ANNOTATION_RULE_ALLOWED_EDGE_OCCURRS,
        MainProcessor.ANNOTATION_RULE_ALLOWED_OCCS})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MainProcessor extends AbstractErrorAbsorbingProcessor {

    public static final String ANNOTATION_DESCRIPTION = "org.wirez.core.api.definition.annotation.Description";
    public static final String ANNOTATION_NAME = "org.wirez.core.api.definition.annotation.Name";

    public static final String ANNOTATION_DEFINITION_SET = "org.wirez.core.api.definition.annotation.definitionset.DefinitionSet";

    public static final String ANNOTATION_DEFINITION = "org.wirez.core.api.definition.annotation.definition.Definition";
    public static final String ANNOTATION_DEFINITION_CATEGORY = "org.wirez.core.api.definition.annotation.definition.Category";
    public static final String ANNOTATION_DEFINITION_LABELS = "org.wirez.core.api.definition.annotation.definition.Labels";
    public static final String ANNOTATION_DEFINITION_PROPERTY = "org.wirez.core.api.definition.annotation.definition.Property";
    public static final String ANNOTATION_DEFINITION_PROPERTYSET = "org.wirez.core.api.definition.annotation.definition.PropertySet";
    public static final String ANNOTATION_DEFINITION_TITLE = "org.wirez.core.api.definition.annotation.definition.Title";
    
    public static final String ANNOTATION_PROPERTY_SET = "org.wirez.core.api.definition.annotation.propertyset.PropertySet";
    public static final String ANNOTATION_PROPERTY_SET_PROPERTY = "org.wirez.core.api.definition.annotation.propertyset.Property";

    public static final String ANNOTATION_PROPERTY = "org.wirez.core.api.definition.annotation.property.Property";
    public static final String ANNOTATION_NAME_PROPERTY = "org.wirez.core.api.definition.annotation.property.NameProperty";
    public static final String ANNOTATION_PROPERTY_DEFAULT_VALUE = "org.wirez.core.api.definition.annotation.property.DefaultValue";
    public static final String ANNOTATION_PROPERTY_VALUE = "org.wirez.core.api.definition.annotation.property.Value";
    public static final String ANNOTATION_PROPERTY_CAPTION = "org.wirez.core.api.definition.annotation.property.Caption";
    public static final String ANNOTATION_PROPERTY_TYPE = "org.wirez.core.api.definition.annotation.property.Type";
    public static final String ANNOTATION_PROPERTY_READONLY= "org.wirez.core.api.definition.annotation.property.ReadOnly";
    public static final String ANNOTATION_PROPERTY_OPTIONAL = "org.wirez.core.api.definition.annotation.property.Optional";
    
    
    public static final String ANNOTATION_RULE_CAN_CONTAIN = "org.wirez.core.api.rule.annotation.CanContain";
    public static final String ANNOTATION_RULE_ALLOWED_CONNECTION = "org.wirez.core.api.rule.annotation.AllowedConnections";
    public static final String ANNOTATION_RULE_CAN_CONNECTION = "org.wirez.core.api.rule.annotation.CanConnect";
    public static final String ANNOTATION_RULE_ALLOWED_OCCS = "org.wirez.core.api.rule.annotation.AllowedOccurrences";
    public static final String ANNOTATION_RULE_OCCURRS = "org.wirez.core.api.rule.annotation.Occurrences";
    public static final String ANNOTATION_RULE_ALLOWED_EDGE_OCCURRS = "org.wirez.core.api.rule.annotation.AllowedEdgeOccurrences";

    public static final String ANNOTATION_SHAPE_SET = "org.wirez.core.client.annotation.ShapeSet";
    public static final String ANNOTATION_SHAPE = "org.wirez.core.client.annotation.Shape";
    
    public static final String RULE_CONTAINMENT_SUFFIX_CLASSNAME = "ContainmentRule";
    public static final String RULE_CONNECTION_SUFFIX_CLASSNAME = "ConnectionRule";
    public static final String RULE_CARDINALITY_SUFFIX_CLASSNAME = "CardinalityRule";
    public static final String RULE_EDGE_CARDINALITY_SUFFIX_CLASSNAME = "EdgeCardinalityRule";

    public static final String DEFINITIONSET_ADAPTER_CLASSNAME = "DefinitionSetAdapterImpl";
    public static final String DEFINITIONSET_PROXY_CLASSNAME = "DefinitionSetProxyImpl";
    public static final String DEFINITION_ADAPTER_CLASSNAME = "DefinitionAdapterImpl";
    public static final String PROPERTYSET_ADAPTER_CLASSNAME = "PropertySetAdapterImpl";
    public static final String PROPERTY_ADAPTER_CLASSNAME = "PropertyAdapterImpl";
    public static final String RULE_ADAPTER_CLASSNAME = "RuleAdapterImpl";

    public static final String SHAPE_FACTORY_CLASSNAME = "ShapeFactory";
    
    private final ProcessingContext processingContext = ProcessingContext.getInstance();
    private final ContainmentRuleGenerator containmentRuleGenerator;
    private final ConnectionRuleGenerator connectionRuleGenerator;
    private final CardinalityRuleGenerator cardinalityRuleGenerator;
    private final EdgeCardinalityRuleGenerator edgeCardinalityRuleGenerator;
    private BindableDefinitionSetAdapterGenerator definitionSetAdapterGenerator;
    private BindableDefinitionAdapterGenerator definitionAdapterGenerator;
    private BindablePropertySetAdapterGenerator propertySetAdapterGenerator;
    private BindablePropertyAdapterGenerator propertyAdapterGenerator;
    private BindableDefinitionSetRuleAdapterGenerator ruleAdapterGenerator;
    private DefinitionSetProxyGenerator definitionSetProxyGenerator;
    private BindableShapeSetGenerator shapeSetGenerator;
    private BindableShapeFactoryGenerator shapeFactoryGenerator;
    
    public MainProcessor() {
        ContainmentRuleGenerator ruleGenerator = null;
        ConnectionRuleGenerator connectionRuleGenerator = null;
        CardinalityRuleGenerator cardinalityRuleGenerator = null;
        EdgeCardinalityRuleGenerator edgeCardinalityRuleGenerator = null;
        BindableDefinitionSetAdapterGenerator definitionSetAdapterGenerator = null;
        BindableDefinitionAdapterGenerator definitionAdapterGenerator = null;
        BindablePropertySetAdapterGenerator propertySetAdapterGenerator = null;
        BindablePropertyAdapterGenerator propertyAdapter = null;
        BindableDefinitionSetRuleAdapterGenerator ruleAdapter = null;
        DefinitionSetProxyGenerator definitionSetProxyGenerator = null;
        BindableShapeSetGenerator shapeSetGenerator = null;
        BindableShapeFactoryGenerator shapeFactoryGenerator = null;
        
        try {
            
            ruleGenerator = new ContainmentRuleGenerator();
            propertyAdapter = new BindablePropertyAdapterGenerator();
            ruleAdapter = new BindableDefinitionSetRuleAdapterGenerator();
            connectionRuleGenerator = new ConnectionRuleGenerator();
            cardinalityRuleGenerator = new CardinalityRuleGenerator();
            edgeCardinalityRuleGenerator = new EdgeCardinalityRuleGenerator();
            definitionAdapterGenerator = new BindableDefinitionAdapterGenerator();
            definitionSetAdapterGenerator = new BindableDefinitionSetAdapterGenerator();
            propertySetAdapterGenerator = new BindablePropertySetAdapterGenerator();
            definitionSetProxyGenerator = new DefinitionSetProxyGenerator();
            shapeSetGenerator = new BindableShapeSetGenerator();
            shapeFactoryGenerator = new BindableShapeFactoryGenerator();
            
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        
        this.containmentRuleGenerator = ruleGenerator;
        this.connectionRuleGenerator = connectionRuleGenerator;
        this.cardinalityRuleGenerator = cardinalityRuleGenerator;
        this.edgeCardinalityRuleGenerator = edgeCardinalityRuleGenerator;
        this.definitionSetAdapterGenerator = definitionSetAdapterGenerator;
        this.definitionAdapterGenerator = definitionAdapterGenerator;
        this.propertySetAdapterGenerator = propertySetAdapterGenerator;
        this.propertyAdapterGenerator = propertyAdapter;
        this.ruleAdapterGenerator = ruleAdapter;
        this.definitionSetProxyGenerator = definitionSetProxyGenerator;
        this.shapeSetGenerator = shapeSetGenerator;
        this.shapeFactoryGenerator = shapeFactoryGenerator;
        
    }

    public static String toValidId( String id ) {
        return StringUtils.uncapitalize( id );
    }

    public static String toClassMemberId( String className ) {
        int i = className.lastIndexOf(".");
        String s = i > -1 ? className.substring( i + 1, className.length() ) : className;
        return toValidId( s );
    }

    @Override
    protected boolean processWithExceptions(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        
        if ( roundEnv.processingOver() ) {

            return processLastRound(set, roundEnv);
            
        }

        //If prior processing threw an error exit
        if ( roundEnv.errorRaised() ) {
            return false;
        }

        final Elements elementUtils = processingEnv.getElementUtils();

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_DEFINITION_SET) ) ) {
            processDefinitionSets(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_DEFINITION) ) ) {
            processDefinitions(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_PROPERTY_SET) ) ) {
            processPropertySets(set, e, roundEnv);
        }

        final Set<? extends Element> propertyElements = new LinkedHashSet<Element>() {{
            addAll( roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_PROPERTY) ) );
            addAll( roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_NAME_PROPERTY ) ) );
        }};
        
        for ( Element e : propertyElements ) {
            processProperties(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_CAN_CONTAIN) ) ) {
            processContainmentRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_ALLOWED_OCCS) ) ) {
            processCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_ALLOWED_EDGE_OCCURRS) ) ) {
            processEdgeCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_ALLOWED_CONNECTION) ) ) {
            processConnectionRules(set, e, roundEnv);
        }
        

        return true;
    }

    protected boolean processDefinitionSets(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isClass) {
            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
            
            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered definition set class [" + classElement.getSimpleName() + "]");
            
            final String packageName = packageElement.getQualifiedName().toString();
            final String className = classElement.getSimpleName().toString();
            processingContext.setDefinitionSet(packageName, className);

            String propertyClassName = packageName + "." + className;
            
            // Description fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DESCRIPTION, processingContext.getDefSetAnnotations().getDescriptionFieldNames());

            // Definitions identifiers.
            DefinitionSet definitionSetAnn = e.getAnnotation(DefinitionSet.class);
            List<? extends TypeMirror> mirrors = null;
            try {
                Class<?>[] defsClasses = definitionSetAnn.definitions();
            } catch( MirroredTypesException mte ) {
                mirrors =  mte.getTypeMirrors();
            }
            if ( null == mirrors ) {
                throw new RuntimeException("No graph class class specifyed for the @DefinitionSet.");
            }
            Set<String> defIds = new LinkedHashSet<>();
            for ( TypeMirror mirror : mirrors ) {
                String fqcn = mirror.toString();
                defIds.add( fqcn );
            }
            processingContext.getDefSetAnnotations().getDefinitionIds().addAll(defIds);

            // Graph.
            TypeMirror mirror = null;
            try {
                Class<?> graphClass = definitionSetAnn.type();
            } catch( MirroredTypeException mte ) {
                mirror =  mte.getTypeMirror();
            }
            if ( null == mirror ) {
                throw new RuntimeException("No graph class class specifyed for the @DefinitionSet.");
            }
            String fqcn = mirror.toString();
            processingContext.getDefSetAnnotations().getGraphTypes().put( propertyClassName, fqcn );

            // Graph factory.
            String factory = definitionSetAnn.factory();
            processingContext.getDefSetAnnotations().getGraphFactories().put( propertyClassName, factory );

            // ShapeSet.
            ShapeSet shapeSetAnn = e.getAnnotation(ShapeSet.class);
            if ( null != shapeSetAnn ) {
         
                TypeMirror tMirror = null;
                try {
                    Class<? extends SafeUriProvider> thumbClass = shapeSetAnn.thumb();
                } catch( MirroredTypeException mte ) {
                    tMirror =  mte.getTypeMirror();
                }
                if ( null == tMirror ) {
                    throw new RuntimeException("No thumbnail SafeUri Provider class class specifyed in this @DefinitionSet.");
                }
                String thumbFQCN = tMirror.toString();
                processingContext.getDefSetAnnotations().getShapeSetThunb().put( propertyClassName, thumbFQCN );
                
            }
            
        }

        return true;
    }

    protected boolean processDefinitions(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        
        final boolean isClass = e.getKind() == ElementKind.CLASS;

        if ( isClass ) {

            TypeElement classElement = (TypeElement) e;

            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
            String propertyClassName = packageElement.getQualifiedName().toString() + "." + classElement.getSimpleName();

            // Category fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DEFINITION_CATEGORY, processingContext.getDefinitionAnnotations().getCategoryFieldNames());

            // Title fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DEFINITION_TITLE, processingContext.getDefinitionAnnotations().getTitleFieldNames());

            // Description fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DESCRIPTION, processingContext.getDefinitionAnnotations().getDescriptionFieldNames());

            // Labels fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DEFINITION_LABELS, processingContext.getDefinitionAnnotations().getLabelsFieldNames());

            // Graph element.
            Definition definitionAnn = e.getAnnotation(Definition.class);
            TypeMirror mirror = null;
            try {
                Class<?> graphClass = definitionAnn.type();
            } catch( MirroredTypeException mte ) {
                mirror =  mte.getTypeMirror();
            }
            if ( null == mirror ) {
                throw new RuntimeException("No graph class class specifyed for the @Definition.");
            }
            String fqcn = mirror.toString();
            processingContext.getDefinitionAnnotations().getGraphElementFieldNames().put( propertyClassName, fqcn );

            // Element factory.
            String factory = definitionAnn.factory();
            processingContext.getDefinitionAnnotations().getElementFactoryFieldNames().put( propertyClassName, factory );

            // PropertySets fields.
            processFieldNames(classElement, propertyClassName, ANNOTATION_DEFINITION_PROPERTYSET, processingContext.getDefinitionAnnotations().getPropertySetFieldNames());

            // Properties fields.
            processFieldNames(classElement, propertyClassName, ANNOTATION_DEFINITION_PROPERTY, processingContext.getDefinitionAnnotations().getPropertyFieldNames());

            // Shape Proxy Factory.
            Shape shapeAnn = e.getAnnotation(Shape.class);
            if ( null != shapeAnn ) {

                TypeMirror sfm = null;
                try {
                    Class<?> graphClass = shapeAnn.factory();
                } catch( MirroredTypeException mte ) {
                    sfm =  mte.getTypeMirror();
                }
                if ( null == sfm ) {
                    throw new RuntimeException( "No ShapeProxy Factory class class specifyed for the Definition ["
                            + propertyClassName + "]" );
                }
                String sfmfqcn = sfm.toString();

                TypeMirror sm = null;
                try {
                    Class<?> graphClass =  shapeAnn.proxy();
                } catch( MirroredTypeException mte ) {
                    sm =  mte.getTypeMirror();
                }
                if ( null == sm ) {
                    throw new RuntimeException("No Shape Proxy class class specifyed for the @Definition.");
                }
                String smfqcn = sm.toString();
                
                if ( !processingContext.getDefinitionAnnotations().getShapeProxies().containsKey( propertyClassName ) ) {
                    
                    processingContext.getDefinitionAnnotations()
                            .getShapeProxies().put( propertyClassName, new String[] { sfmfqcn, smfqcn } );
                    
                }

            }

        }

        return false;

    }

    protected boolean processPropertySets(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {

        final boolean isClass = e.getKind() == ElementKind.CLASS;

        if ( isClass ) {

            TypeElement classElement = (TypeElement) e;

            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
            String propertyClassName = packageElement.getQualifiedName().toString() + "." + classElement.getSimpleName();

            // Name fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_NAME, processingContext.getPropertySetAnnotations().getNameFieldNames());

            // Properties fields.
            processFieldNames(classElement, propertyClassName, ANNOTATION_PROPERTY_SET_PROPERTY, processingContext.getPropertySetAnnotations().getPropertiesFieldNames());

        }

        return false;
        
    }

    protected boolean processProperties(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {

        final boolean isClass = e.getKind() == ElementKind.CLASS;
        
        if ( isClass ) {

            TypeElement classElement = (TypeElement) e;

            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
            String propertyClassName = packageElement.getQualifiedName().toString() + "." + classElement.getSimpleName();

            if ( null != e.getAnnotation( NameProperty.class ) ) {
                processingContext.setNamePropertyClass( propertyClassName + ".class" );
            }
            
            // Value fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_VALUE, processingContext.getPropertyAnnotations().getValueFieldNames());

            // Default Value fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_DEFAULT_VALUE, processingContext.getPropertyAnnotations().getDefaultValueFieldNames());

            // Caption fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_CAPTION, processingContext.getPropertyAnnotations().getCaptionFieldNames());

            // Description fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_DESCRIPTION, processingContext.getPropertyAnnotations().getDescriptionFieldNames());

            // Type fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_TYPE, processingContext.getPropertyAnnotations().getTypeFieldNames());

            // Read only fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_READONLY, processingContext.getPropertyAnnotations().getReadOnlyFieldNames());

            // Optional fields.
            processFieldName(classElement, propertyClassName, ANNOTATION_PROPERTY_OPTIONAL, processingContext.getPropertyAnnotations().getOptionalFieldNames());

        }
        
        return false;

    }
    
    protected void processFieldName(TypeElement classElement, String propertyClassName, String annotation, Map<String, String> ctxMap) {
        Collection<String> fieldNames = getFieldNames(classElement, annotation );
        if ( fieldNames.isEmpty() ) {
            throw new RuntimeException("No annotation of type [" + annotation + "] for Property of class [" + classElement + "]");
        }
        ctxMap.put( propertyClassName, fieldNames.iterator().next() );
    }

    protected void processFieldNames(TypeElement classElement, String propertyClassName, String annotation, Map<String, Set<String>> ctxMap) {
        Collection<String> fieldNames = getFieldNames(classElement, annotation );
        ctxMap.put( propertyClassName, new LinkedHashSet<>(fieldNames) );
    }
    
    protected Collection<String> getFieldNames(TypeElement classElement, String annotation) {
        final Messager messager = processingEnv.getMessager();
        final Elements elementUtils = processingEnv.getElementUtils();

        Set<String> result = new LinkedHashSet<>();
        List<VariableElement> variableElements = ElementFilter.fieldsIn( classElement.getEnclosedElements() );
        for (VariableElement variableElement : variableElements) {
            if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, annotation ) != null ) {
                final TypeMirror fieldReturnType = variableElement.asType();
                final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                final String fieldName = variableElement.getSimpleName().toString();

                result.add( fieldName );
                messager.printMessage(Diagnostic.Kind.WARNING, "Discovered property value for class [" + classElement.getSimpleName() + "] at field [" + fieldName + "] of return type [" + fieldReturnTypeName + "]");

            }
        }
        
        return result;
    }
    
    protected boolean processContainmentRules(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isIface = e.getKind() == ElementKind.INTERFACE;
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {

            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered containment rule for class [" + classElement.getSimpleName() + "]");

            final String packageName = packageElement.getQualifiedName().toString();
            final String classNameActivity = classElement.getSimpleName() + RULE_CONTAINMENT_SUFFIX_CLASSNAME;

            try {
                //Try generating code for each required class
                messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                containmentRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }
        
        return true;
        
    }

    protected boolean processEdgeCardinalityRules(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isIface = e.getKind() == ElementKind.INTERFACE;
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {

            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered edge cardinality rule for class [" + classElement.getSimpleName() + "]");

            final String packageName = packageElement.getQualifiedName().toString();
            final String classNameActivity = classElement.getSimpleName() + RULE_EDGE_CARDINALITY_SUFFIX_CLASSNAME;

            try {
                //Try generating code for each required class
                messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                edgeCardinalityRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }

        return true;

    }
    
    protected boolean processCardinalityRules(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isIface = e.getKind() == ElementKind.INTERFACE;
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {

            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered cardinality rule for class [" + classElement.getSimpleName() + "]");

            final String packageName = packageElement.getQualifiedName().toString();
            final String classNameActivity = classElement.getSimpleName() + RULE_CARDINALITY_SUFFIX_CLASSNAME;

            try {
                //Try generating code for each required class
                messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                cardinalityRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }

        return true;

    }

    protected boolean processConnectionRules(Set<? extends TypeElement> set, Element element, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isIface = element.getKind() == ElementKind.INTERFACE;
        final boolean isClass = element.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {

            TypeElement classElement = (TypeElement) element;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered connection rule for class [" + classElement.getSimpleName() + "]");

            final String packageName = packageElement.getQualifiedName().toString();
            final String classNameActivity = classElement.getSimpleName() + RULE_CONNECTION_SUFFIX_CLASSNAME;

            try {

                //Try generating code for each required class
                messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                connectionRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }

        return true;

    }

    protected boolean processLastRound(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        processLastRoundDefinitionSetProxyAdapter(set, roundEnv);
        processLastRoundDefinitionSetAdapter(set, roundEnv);
        processLastRoundPropertySetAdapter(set, roundEnv);
        processLastRoundDefinitionAdapter(set, roundEnv);
        processLastRoundPropertyAdapter(set, roundEnv);
        processLastRoundRuleAdapter(set, roundEnv);
        processLastRoundShapeSetGenerator(set, roundEnv);
        processLastRoundShapeFactoryGenerator(set, roundEnv);
        return true;
    }

    protected boolean processLastRoundRuleAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            // Ensure visible on both backend and client sides.
            final String packageName = getGeneratedPackageName() + ".api.adapter." + defSetId.toLowerCase() + ".rule";
            final String className = getSetClassPrefix() + RULE_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting RuleAdapter generation for class named " + classFQName);

            final StringBuffer ruleClassCode = ruleAdapterGenerator.generate(packageName, className, 
                    processingContext.getDefinitionSet().getClassName(), processingContext.getRules(), messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundDefinitionSetAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();

            // Ensure only visible on client side.
            final String packageName = getGeneratedPackageName() + ".client.adapter." + defSetId.toLowerCase() + ".definitionset";
            final String className = getSetClassPrefix() + DEFINITIONSET_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = definitionSetAdapterGenerator.generate(packageName, className,
                    processingContext.getDefSetAnnotations(), messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundDefinitionSetProxyAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            // Ensure visible on both backend and client sides.
            final String packageName = getGeneratedPackageName() + ".api.adapter." + defSetId.toLowerCase() + ".definitionset";
            final String className = getSetClassPrefix() + DEFINITIONSET_PROXY_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting DefinitionSetProxyAdapter generation for class named " + classFQName);

            final StringBuffer ruleClassCode = definitionSetProxyGenerator.
                    generate(packageName,
                            className,
                            processingContext.getDefinitionSet(),
                            messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundPropertySetAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();

            // Ensure only visible on client side.
            final String packageName = getGeneratedPackageName() + ".client.adapter." + defSetId.toLowerCase() + ".propertyset";
            final String className = getSetClassPrefix() + PROPERTYSET_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = propertySetAdapterGenerator.generate(packageName, className,
                    processingContext.getPropertySetAnnotations(), messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }
    
    protected boolean processLastRoundDefinitionAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();

            // Ensure only visible on client side.
            final String packageName = getGeneratedPackageName() + ".client.adapter." + defSetId.toLowerCase() + ".definition";
            final String className = getSetClassPrefix() + DEFINITION_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = definitionAdapterGenerator.generate(packageName, className,
                    processingContext.getDefinitionAnnotations(), processingContext.getNamePropertyClass(), messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundPropertyAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            
            // Ensure only visible on client side.
            final String packageName = getGeneratedPackageName() + ".client.adapter." + defSetId.toLowerCase() + ".property";
            final String className = getSetClassPrefix() + PROPERTY_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = propertyAdapterGenerator.generate(packageName, className,
                    processingContext.getPropertyAnnotations(), messager);

            writeCode( packageName,
                    className,
                    ruleClassCode );

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundShapeSetGenerator(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            // Generate the Shape Set if annotation present.
            if ( !processingContext.getDefSetAnnotations().getShapeSetThunb().isEmpty() ) {

                final String defSetId = processingContext.getDefinitionSet().getId();

                // Ensure only visible on client side.
                final String packageName = getGeneratedPackageName() + ".client." + defSetId.toLowerCase() + ".shape";
                final String className = getSetClassPrefix() + BindableAdapterUtils.SHAPE_SET_SUFFIX;
                final String classFQName = packageName + "." + className;
                messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

                final String defSetClassName = processingContext.getDefinitionSet().getClassName();
                
                final StringBuffer ruleClassCode = shapeSetGenerator.generate(
                        packageName, 
                        className,
                        defSetClassName,
                        processingContext.getDefSetAnnotations().getShapeSetThunb().get( defSetClassName ),
                        messager);

                writeCode( packageName,
                        className,
                        ruleClassCode );

            }

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }

    protected boolean processLastRoundShapeFactoryGenerator(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            // Generate the Shape Set if annotation present.
            if ( !processingContext.getDefinitionAnnotations().getShapeProxies().isEmpty() ) {

                final String defSetId = processingContext.getDefinitionSet().getId();

                // Ensure only visible on client side.
                final String packageName = getGeneratedPackageName() + ".client." + defSetId.toLowerCase() + ".shape";
                final String className = getSetClassPrefix() + SHAPE_FACTORY_CLASSNAME;
                final String classFQName = packageName + "." + className;
                messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

                final StringBuffer ruleClassCode =
                        shapeFactoryGenerator.generate(
                                packageName,
                                className,
                                processingContext.getDefinitionAnnotations(),
                                messager );

                writeCode( packageName,
                        className,
                        ruleClassCode );
                
            }

        } catch ( GenerationException ge ) {
            final String msg = ge.getMessage();
            processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
        }
        return true;

    }
    
    private String getGeneratedPackageName() {
        final String s = processingContext.getDefinitionSet().getClassName();
        final String s1 =  s.substring( 0, s.lastIndexOf(".") );
        
        // Handle api package given by nomenclature.
        if ( s1.endsWith( ".api") ) {
            
            return s1.substring( 0, s1.length() - 4 );
            
        }
        
        return s1;
    }
    
    private String getSetClassPrefix() {
        return processingContext.getDefinitionSet().getId();
    }
    
}
