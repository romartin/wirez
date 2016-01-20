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

package org.wirez.core.processors.rule;

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.processors.property.PropertyGenerator;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({RuleProcessor.ANNOTATION_CAN_CONTAIN, RuleProcessor.ANNOTATION_CAN_CARDINALITY, RuleProcessor.ANNOTATION_CAN_CONNECTION})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RuleProcessor extends AbstractErrorAbsorbingProcessor {

    public static final String ANNOTATION_CAN_CONTAIN = "org.wirez.core.api.annotation.rule.CanContain";
    public static final String ANNOTATION_CAN_CARDINALITY = "org.wirez.core.api.annotation.rule.Cardinality";
    public static final String ANNOTATION_CAN_CONNECTION = "org.wirez.core.api.annotation.rule.Connection";
    public static final String ANNOTATION_CAN_CONNECTOR = "org.wirez.core.api.annotation.rule.Connector";
    public static final String ANNOTATION_CAN_PERMITTED_CONNECTION = "org.wirez.core.api.annotation.rule.PermittedConnection";

    static final String SUFFIX_CONTAINMENT_RULE = "ContainmentRule";
    
    private final ContainmentRuleGenerator containmentRuleGenerator;

    public RuleProcessor() {
        ContainmentRuleGenerator pg = null;
        try {
            pg = new ContainmentRuleGenerator();
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        containmentRuleGenerator = pg;
    }

    @Override
    protected boolean processWithExceptions(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        
        //We don't have any post-processing
        if ( roundEnv.processingOver() ) {
            return false;
        }

        //If prior processing threw an error exit
        if ( roundEnv.errorRaised() ) {
            return false;
        }

        final Messager messager = processingEnv.getMessager();
        final Elements elementUtils = processingEnv.getElementUtils();

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_CAN_CONTAIN) ) ) {
            final boolean isIface = e.getKind() == ElementKind.INTERFACE;
            final boolean isClass = e.getKind() == ElementKind.CLASS;
            if (isIface || isClass) {

                TypeElement classElement = (TypeElement) e;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                messager.printMessage(Diagnostic.Kind.NOTE, "Discovered containment rule class [" + classElement.getSimpleName() + "]");

                final String packageName = packageElement.getQualifiedName().toString();
                final String classNameActivity = classElement.getSimpleName() + SUFFIX_CONTAINMENT_RULE;

                try {
                    //Try generating code for each required class
                    messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );
                    final StringBuffer ruleClassCode = containmentRuleGenerator.generate( packageName,
                            packageElement,
                            classNameActivity,
                            classElement,
                            processingEnv );

                    writeCode( packageName,
                            classNameActivity,
                            ruleClassCode );

                } catch ( GenerationException ge ) {
                    final String msg = ge.getMessage();
                    processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
                }

            }
        }
        
        // TODO: Iterate for ANNOTATION_CAN_CARDINALITY

        // TODO: Iterate for ANNOTATION_CAN_CONNECTION

        return true;
    }
    
    
}
