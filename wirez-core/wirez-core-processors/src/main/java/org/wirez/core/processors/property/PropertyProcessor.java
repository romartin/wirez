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

package org.wirez.core.processors.property;

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.processors.GeneratorUtils;
import org.wirez.core.processors.ProcessingContext;
import org.wirez.core.processors.ProcessingElement;

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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes({PropertyProcessor.ANNOTATION_PROPERTY})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PropertyProcessor extends AbstractErrorAbsorbingProcessor {

    public static final String CLASS_NAME = "ErraiBindablePropertyAdapter";
    public static final String ANNOTATION_PROPERTY = "org.wirez.core.api.annotation.property.Property";
    public static final String ANNOTATION_DEFAULT_VALUE = "org.wirez.core.api.annotation.property.DefaultValue";
    public static final String ANNOTATION_VALUE = "org.wirez.core.api.annotation.property.Value";

    private final ProcessingContext processingContext = ProcessingContext.getInstance();
    private ErraiBindablePropertyAdapterWritter adapterGenerator;
    
    public PropertyProcessor() {
        ErraiBindablePropertyAdapterWritter pg = null;
        try {
            pg = new ErraiBindablePropertyAdapterWritter();
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        adapterGenerator = pg;
    }

    @Override
    protected boolean processWithExceptions(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {
        final Messager messager = processingEnv.getMessager();
        
        if ( roundEnv.processingOver() ) {

            try {
                
                final String packageName = getCommonPackageName(processingContext.getValueFieldNames().keySet());
                final String className = packageName + "." + CLASS_NAME;
                messager.printMessage(Diagnostic.Kind.WARNING, "Starting ErraiBinderAdapter generation named " + className);

                final StringBuffer ruleClassCode = adapterGenerator.generate(packageName, CLASS_NAME,
                        processingContext.getValueFieldNames(), processingContext.getDefaultValueFieldNames(), messager);

                writeCode( packageName,
                        CLASS_NAME,
                        ruleClassCode );
                
            } catch ( GenerationException ge ) {
                final String msg = ge.getMessage();
                processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg);
            }
            
            return true;
        }

        //If prior processing threw an error exit
        if ( roundEnv.errorRaised() ) {
            return false;
        }

        final Elements elementUtils = processingEnv.getElementUtils();

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_PROPERTY) ) ) {
            final boolean isIface = e.getKind() == ElementKind.INTERFACE;
            final boolean isClass = e.getKind() == ElementKind.CLASS;
            if (isIface || isClass) {

                TypeElement classElement = (TypeElement) e;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                String propertyClassName = packageElement.getQualifiedName().toString() + "." + classElement.getSimpleName();
                
                List<VariableElement> variableElements = ElementFilter.fieldsIn( classElement.getEnclosedElements() );
                for (VariableElement variableElement : variableElements) {

                    // Value.
                    if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, PropertyProcessor.ANNOTATION_VALUE ) != null ) {
                        final TypeMirror fieldReturnType = variableElement.asType();
                        final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                        final String fieldName = variableElement.getSimpleName().toString();

                        processingContext.addValueFieldName(propertyClassName, fieldName);
                        messager.printMessage(Diagnostic.Kind.WARNING, "Discovered property value for class [" + propertyClassName + "] at field [" + fieldName + "] of return type [" + fieldReturnTypeName + "]");
                        
                    }

                    // Default Value.
                    if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, PropertyProcessor.ANNOTATION_DEFAULT_VALUE ) != null ) {
                        final TypeMirror fieldReturnType = variableElement.asType();
                        final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                        final String fieldName = variableElement.getSimpleName().toString();

                        processingContext.addDefaultValueFieldName(propertyClassName, fieldName);
                        messager.printMessage(Diagnostic.Kind.WARNING, "Discovered property default value for class [" + propertyClassName + "] at field [" + fieldName + "] of return type [" + fieldReturnTypeName + "]");

                    }

                }
                

            }
        }

        return true;
    }

    private static String getCommonPackageName(Set<String> packageNames) {
        StringBuilder result = new StringBuilder();
        boolean end = false;
        int pos = 0;
        while (!end) {
            Character c = null;
            for (String packageName : packageNames) {
                if (packageName.length() >= pos) {
                    char _c = packageName.charAt(pos);
                    if ( null == c ) {
                        c = _c;
                    } else if ( c != _c ) {
                        end = true;
                        break;
                    }
                }
            }
            
            if ( !end ) {
                result.append(c);
                pos++;
            }
        }
        
        if (result.length() > 0) {
            String pName = result.toString();
            while (pName.endsWith(".")) {
                pName = pName.substring(0, pName.length() - 1);
            }
            
            return pName;
        }
        
        throw new RuntimeException("Cannot find common package name.");
    }

}
