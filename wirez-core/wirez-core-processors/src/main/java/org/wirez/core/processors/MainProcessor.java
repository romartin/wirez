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
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.processors.definition.BindableDefinitionAdapterGenerator;
import org.wirez.core.processors.definitionset.BindableDefinitionSetAdapterGenerator;
import org.wirez.core.processors.definitionset.DefinitionSetProxyGenerator;
import org.wirez.core.processors.property.BindablePropertyAdapterGenerator;
import org.wirez.core.processors.propertyset.BindablePropertySetAdapterGenerator;
import org.wirez.core.processors.rule.BindableDefinitionSetRuleAdapterGenerator;
import org.wirez.core.processors.rule.CardinalityRuleGenerator;
import org.wirez.core.processors.rule.ConnectionRuleGenerator;
import org.wirez.core.processors.rule.ContainmentRuleGenerator;

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
        MainProcessor.ANNOTATION_RULE_CAN_CONTAIN, 
        MainProcessor.ANNOTATION_RULE_CAN_CONNECT, 
        MainProcessor.ANNOTATION_RULE_OCCURRENCES,
        MainProcessor.ANNOTATION_RULE_EDGE_OCCURRENCES})
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
    public static final String ANNOTATION_PROPERTY_DEFAULT_VALUE = "org.wirez.core.api.definition.annotation.property.DefaultValue";
    public static final String ANNOTATION_PROPERTY_VALUE = "org.wirez.core.api.definition.annotation.property.Value";
    public static final String ANNOTATION_PROPERTY_CAPTION = "org.wirez.core.api.definition.annotation.property.Caption";
    public static final String ANNOTATION_PROPERTY_TYPE = "org.wirez.core.api.definition.annotation.property.Type";
    public static final String ANNOTATION_PROPERTY_READONLY= "org.wirez.core.api.definition.annotation.property.ReadOnly";
    public static final String ANNOTATION_PROPERTY_OPTIONAL = "org.wirez.core.api.definition.annotation.property.Optional";
    
    
    public static final String ANNOTATION_RULE_CAN_CONTAIN = "org.wirez.core.api.definition.annotation.rule.CanContain";
    public static final String ANNOTATION_RULE_CAN_CONNECT = "org.wirez.core.api.definition.annotation.rule.CanConnect";
    public static final String ANNOTATION_RULE_PERMITTED_CONNECTION = "org.wirez.core.api.definition.annotation.rule.PermittedConnection";
    public static final String ANNOTATION_RULE_CARDINALITY = "org.wirez.core.api.definition.annotation.rule.Cardinality";
    public static final String ANNOTATION_RULE_OCCURRENCES = "org.wirez.core.api.definition.annotation.rule.Occurrences";
    public static final String ANNOTATION_RULE_EDGE_OCCURRENCES = "org.wirez.core.api.definition.annotation.rule.EdgeOccurrences";

    public static final String RULE_CONTAINMENT_SUFFIX_CLASSNAME = "ContainmentRule";
    public static final String RULE_CONNECTION_SUFFIX_CLASSNAME = "ConnectionRule";
    public static final String RULE_CARDINALITY_SUFFIX_CLASSNAME = "CardinalityRule";

    public static final String DEFINITIONSET_ADAPTER_CLASSNAME = "BindableDefinitionSetAdapterImpl";
    public static final String DEFINITIONSET_PROXY_CLASSNAME = "DefinitionSetProxyImpl";
    public static final String DEFINITION_ADAPTER_CLASSNAME = "BindableDefinitionAdapterImpl";
    public static final String PROPERTYSET_ADAPTER_CLASSNAME = "BindablePropertySetAdapterImpl";
    public static final String PROPERTY_ADAPTER_CLASSNAME = "BindablePropertyAdapterImpl";
    public static final String RULE_ADAPTER_CLASSNAME = "RuleAdapterImpl";

    private final ProcessingContext processingContext = ProcessingContext.getInstance();
    private final ContainmentRuleGenerator containmentRuleGenerator;
    private final ConnectionRuleGenerator connectionRuleGenerator;
    private final CardinalityRuleGenerator cardinalityRuleGenerator; 
    private BindableDefinitionSetAdapterGenerator definitionSetAdapterGenerator;
    private BindableDefinitionAdapterGenerator definitionAdapterGenerator;
    private BindablePropertySetAdapterGenerator propertySetAdapterGenerator;
    private BindablePropertyAdapterGenerator propertyAdapterGenerator;
    private BindableDefinitionSetRuleAdapterGenerator ruleAdapterGenerator;
    private DefinitionSetProxyGenerator definitionSetProxyGenerator;
    
    public MainProcessor() {
        ContainmentRuleGenerator ruleGenerator = null;
        ConnectionRuleGenerator connectionRuleGenerator = null;
        CardinalityRuleGenerator cardinalityRuleGenerator = null;
        BindableDefinitionSetAdapterGenerator definitionSetAdapterGenerator = null;
        BindableDefinitionAdapterGenerator definitionAdapterGenerator = null;
        BindablePropertySetAdapterGenerator propertySetAdapterGenerator = null;
        BindablePropertyAdapterGenerator propertyAdapter = null;
        BindableDefinitionSetRuleAdapterGenerator ruleAdapter = null;
        DefinitionSetProxyGenerator definitionSetProxyGenerator = null;
        
        try {
            ruleGenerator = new ContainmentRuleGenerator();
            propertyAdapter = new BindablePropertyAdapterGenerator();
            ruleAdapter = new BindableDefinitionSetRuleAdapterGenerator();
            connectionRuleGenerator = new ConnectionRuleGenerator();
            cardinalityRuleGenerator = new CardinalityRuleGenerator();
            definitionAdapterGenerator = new BindableDefinitionAdapterGenerator();
            definitionSetAdapterGenerator = new BindableDefinitionSetAdapterGenerator();
            propertySetAdapterGenerator = new BindablePropertySetAdapterGenerator();
            definitionSetProxyGenerator = new DefinitionSetProxyGenerator();
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        this.containmentRuleGenerator = ruleGenerator;
        this.connectionRuleGenerator = connectionRuleGenerator;
        this.cardinalityRuleGenerator = cardinalityRuleGenerator;
        this.definitionSetAdapterGenerator = definitionSetAdapterGenerator;
        this.definitionAdapterGenerator = definitionAdapterGenerator;
        this.propertySetAdapterGenerator = propertySetAdapterGenerator;
        this.propertyAdapterGenerator = propertyAdapter;
        this.ruleAdapterGenerator = ruleAdapter;
        this.definitionSetProxyGenerator = definitionSetProxyGenerator;
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

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_PROPERTY) ) ) {
            processProperties(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_CAN_CONTAIN) ) ) {
            processContainmentRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_CARDINALITY) ) ) {
            processCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_EDGE_OCCURRENCES) ) ) {
            // TODO: processEdgeCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_CAN_CONNECT) ) ) {
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
        return true;
    }

    protected boolean processLastRoundRuleAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            // Ensure visible on both backend and client sides.
            final String packageName = "org.wirez.core.api.adapter." + defSetId.toLowerCase() + ".rule";
            final String className = RULE_ADAPTER_CLASSNAME;
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
            final String packageName = "org.wirez.core.client.adapter." + defSetId.toLowerCase() + ".definitionset";
            final String className = DEFINITIONSET_ADAPTER_CLASSNAME;
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
            final String packageName = "org.wirez.core.api.adapter." + defSetId.toLowerCase() + ".definitionset";
            final String className = DEFINITIONSET_PROXY_CLASSNAME;
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
            final String packageName = "org.wirez.core.client.adapter." + defSetId.toLowerCase() + ".propertyset";
            final String className = PROPERTYSET_ADAPTER_CLASSNAME;
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
            final String packageName = "org.wirez.core.client.adapter." + defSetId.toLowerCase() + ".definition";
            final String className = DEFINITION_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = definitionAdapterGenerator.generate(packageName, className,
                    processingContext.getDefinitionAnnotations(), messager);

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
            final String packageName = "org.wirez.core.client.adapter." + defSetId.toLowerCase() + ".property";
            final String className = PROPERTY_ADAPTER_CLASSNAME;
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
    
    public static String toValidId(String id) {
        return StringUtils.uncapitalize(id);
    }

}
