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
import org.wirez.core.processors.property.ErraiBindablePropertyAdapterGenerator;
import org.wirez.core.processors.rule.ConnectionRuleGenerator;
import org.wirez.core.processors.rule.ContainmentRuleGenerator;
import org.wirez.core.processors.rule.DefinitionSetRuleAdapterGenerator;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({MainProcessor.ANNOTATION_DEFINITION_SET,
        MainProcessor.ANNOTATION_DEFINITION,
        MainProcessor.ANNOTATION_PROPERTY_SET,
        MainProcessor.ANNOTATION_PROPERTY, 
        MainProcessor.ANNOTATION_RULE_CAN_CONTAIN, 
        MainProcessor.ANNOTATION_RULE_CAN_CONNECT, 
        MainProcessor.ANNOTATION_RULE_OCCURRENCES,
        MainProcessor.ANNOTATION_RULE_EDGE_OCCURRENCES})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MainProcessor extends AbstractErrorAbsorbingProcessor {

    public static final String ANNOTATION_DEFINITION = "org.wirez.core.api.annotation.definition.Definition";
    public static final String ANNOTATION_DEFINITION_SET = "org.wirez.core.api.annotation.definitionset.DefinitionSet";
    public static final String ANNOTATION_PROPERTY_SET = "org.wirez.core.api.annotation.propertyset.PropertySet";
    public static final String ANNOTATION_PROPERTY = "org.wirez.core.api.annotation.property.Property";
    public static final String ANNOTATION_PROPERTY_DEFAULT_VALUE = "org.wirez.core.api.annotation.property.DefaultValue";
    public static final String ANNOTATION_PROPERTY_VALUE = "org.wirez.core.api.annotation.property.Value";
    public static final String ANNOTATION_RULE_CAN_CONTAIN = "org.wirez.core.api.annotation.rule.CanContain";
    public static final String ANNOTATION_RULE_OCCURRENCES = "org.wirez.core.api.annotation.rule.Occurrences";
    public static final String ANNOTATION_RULE_CAN_CONNECT = "org.wirez.core.api.annotation.rule.CanConnect";
    public static final String ANNOTATION_RULE_EDGE_OCCURRENCES = "org.wirez.core.api.annotation.rule.EdgeOccurrences";

    public static final String RULE_CONTAINMENT_SUFFIX_CLASSNAME = "ContainmentRule";
    public static final String RULE_CONNECTION_SUFFIX_CLASSNAME = "ConnectionRule";
    public static final String RULE_CARDINALITY_SUFFIX_CLASSNAME = "CardinalityRule";
    public static final String PROPERTY_ADAPTER_CLASSNAME = "PropertyAdapter";
    public static final String RULE_ADAPTER_CLASSNAME = "RuleAdapter";

    private final ProcessingContext processingContext = ProcessingContext.getInstance();
    private final ContainmentRuleGenerator containmentRuleGenerator;
    private final ConnectionRuleGenerator connectionRuleGenerator;
    private ErraiBindablePropertyAdapterGenerator propertyAdapterGenerator;
    private DefinitionSetRuleAdapterGenerator ruleAdapterGenerator;
    
    public MainProcessor() {
        ContainmentRuleGenerator ruleGenerator = null;
        ConnectionRuleGenerator connectionRuleGenerator = null;
        ErraiBindablePropertyAdapterGenerator propertyAdapter = null;
        DefinitionSetRuleAdapterGenerator ruleAdapter = null;
        
        try {
            ruleGenerator = new ContainmentRuleGenerator();
            propertyAdapter = new ErraiBindablePropertyAdapterGenerator();
            ruleAdapter = new DefinitionSetRuleAdapterGenerator();
            connectionRuleGenerator = new ConnectionRuleGenerator();
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        this.containmentRuleGenerator = ruleGenerator;
        this.connectionRuleGenerator = connectionRuleGenerator;
        this.propertyAdapterGenerator = propertyAdapter;
        this.ruleAdapterGenerator = ruleAdapter;
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

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_OCCURRENCES) ) ) {
            // processCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_EDGE_OCCURRENCES) ) ) {
            // processEdgeCardinalityRules(set, e, roundEnv);
        }

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_RULE_CAN_CONNECT) ) ) {
            // processConnectionRules(set, e, roundEnv);
        }
        

        return true;
    }

    protected boolean processDefinitionSets(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final boolean isIface = e.getKind() == ElementKind.INTERFACE;
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {
            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered definition set class [" + classElement.getSimpleName() + "]");
            final String packageName = packageElement.getQualifiedName().toString();
            final String className = classElement.getSimpleName().toString();
            processingContext.setDefinitionSet(packageName, className);
        }

        return true;
    }

    protected boolean processDefinitions(Set<? extends TypeElement> set, Element element, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        return false;

    }

    protected boolean processPropertySets(Set<? extends TypeElement> set, Element element, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        return false;

    }

    protected boolean processProperties(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        final Elements elementUtils = processingEnv.getElementUtils();

        final boolean isIface = e.getKind() == ElementKind.INTERFACE;
        final boolean isClass = e.getKind() == ElementKind.CLASS;
        if (isIface || isClass) {

            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            String propertyClassName = packageElement.getQualifiedName().toString() + "." + classElement.getSimpleName();

            List<VariableElement> variableElements = ElementFilter.fieldsIn( classElement.getEnclosedElements() );
            for (VariableElement variableElement : variableElements) {

                // Value.
                if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, ANNOTATION_PROPERTY_VALUE ) != null ) {
                    final TypeMirror fieldReturnType = variableElement.asType();
                    final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                    final String fieldName = variableElement.getSimpleName().toString();

                    processingContext.addValueFieldName(propertyClassName, fieldName);
                    messager.printMessage(Diagnostic.Kind.WARNING, "Discovered property value for class [" + propertyClassName + "] at field [" + fieldName + "] of return type [" + fieldReturnTypeName + "]");

                }

                // Default Value.
                if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, ANNOTATION_PROPERTY_DEFAULT_VALUE ) != null ) {
                    final TypeMirror fieldReturnType = variableElement.asType();
                    final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                    final String fieldName = variableElement.getSimpleName().toString();

                    processingContext.addDefaultValueFieldName(propertyClassName, fieldName);
                    messager.printMessage(Diagnostic.Kind.WARNING, "Discovered property default value for class [" + propertyClassName + "] at field [" + fieldName + "] of return type [" + fieldReturnTypeName + "]");

                }

            }


        }
        return false;

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
                final StringBuffer ruleClassCode = containmentRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

                processingContext.addRule(toValidId(classNameActivity), ProcessingRule.TYPE.CONTAINMENT, ruleClassCode);
                
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
        /*if (isIface || isClass) {

            TypeElement classElement = (TypeElement) e;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            messager.printMessage(Diagnostic.Kind.NOTE, "Discovered cardinality rule for class [" + classElement.getSimpleName() + "]");

            final String packageName = packageElement.getQualifiedName().toString();
            final String classNameActivity = classElement.getSimpleName() + RULE_CARDINALITY_SUFFIX_CLASSNAME;

            try {
                //Try generating code for each required class
                messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                final StringBuffer ruleClassCode = cardinalityRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

                processingContext.addRule(toValidId(classNameActivity), ProcessingRule.TYPE.CARDINALITY, ruleClassCode);

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }*/

        return true;

    }

    protected boolean processEdgeCardinalityRules(Set<? extends TypeElement> set, Element e, RoundEnvironment roundEnv) throws Exception {
        // TODO
        return false;
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
                final StringBuffer ruleClassCode = connectionRuleGenerator.generate( packageName,
                        packageElement,
                        classNameActivity,
                        classElement,
                        processingEnv );

                processingContext.addRule(toValidId(classNameActivity), ProcessingRule.TYPE.CONNECTION, ruleClassCode);

            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
            }

        }

        return true;

    }

    protected boolean processLastRound(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        processLastRoundPropertyAdapter(set, roundEnv);
        processLastRoundRuleAdapter(set, roundEnv);
        return true;
    }

    protected boolean processLastRoundRuleAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            // Ensure visible on both backend and client sides.
            final String packageName = "org.wirez.core.api.adapter.rule." + defSetId.toLowerCase();
            final String className = defSetId + RULE_ADAPTER_CLASSNAME;
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

    protected boolean processLastRoundPropertyAdapter(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        try {

            final String defSetId = processingContext.getDefinitionSet().getId();
            // Ensure only visible on client side.
            final String packageName = "org.wirez.core.client.adapter.property." + defSetId.toLowerCase();
            final String className = defSetId + PROPERTY_ADAPTER_CLASSNAME;
            final String classFQName = packageName + "." + className;
            messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + classFQName);

            final StringBuffer ruleClassCode = propertyAdapterGenerator.generate(packageName, className,
                    processingContext.getValueFieldNames(), processingContext.getDefaultValueFieldNames(), messager);

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
