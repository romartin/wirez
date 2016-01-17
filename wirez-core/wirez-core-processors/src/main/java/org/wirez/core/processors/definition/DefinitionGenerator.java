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

package org.wirez.core.processors.definition;

import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;
import org.wirez.core.processors.GeneratorUtils;
import org.wirez.core.processors.ProcessingElement;
import org.wirez.core.processors.property.PropertyProcessor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefinitionGenerator extends AbstractGenerator {
    
    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        print( messager, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final boolean isInterface = classElement.getKind().isInterface();
        final String annotationName = DefinitionProcessor.ANNOTATION_DEFINITION;





        String identifier = null;
        String category = null;
        String title = null;
        String description = null;
        String[] labels = null;
        String factory = null;

        for ( final AnnotationMirror am : classElement.getAnnotationMirrors() ) {
            if ( annotationName.equals( am.getAnnotationType().toString() ) ) {
                for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() ) {
                    AnnotationValue aval = entry.getValue();
                    if ( "identifier".equals( entry.getKey().getSimpleName().toString() ) ) {
                        identifier = aval.getValue().toString();
                    } else if ( "category".equals( entry.getKey().getSimpleName().toString() ) ) {
                        category = aval.getValue().toString();
                    }
                    else if ( "description".equals( entry.getKey().getSimpleName().toString() ) ) {
                        description = aval.getValue().toString();
                    }
                    else if ( "title".equals( entry.getKey().getSimpleName().toString() ) ) {
                        title = aval.getValue().toString();
                    }
                    else if ( "labels".equals( entry.getKey().getSimpleName().toString() ) ) {
                        labels = (String[]) aval.getValue();
                    }
                    else if ( "factory".equals( entry.getKey().getSimpleName().toString() ) ) {
                        factory = aval.getValue().toString();
                    }
                }
                break;
            }
        }

        final TypeMirror propertyTypeMirror = elementUtils.getTypeElement( PropertyProcessor.ANNOTATION_PROPERTY ).asType();
        final List<ExecutableElement> propertyElements = GeneratorUtils.getAnnotatedMethods(classElement, processingEnvironment,
                DefinitionProcessor.ANNOTATION_DEFINITION_PROPERTY, propertyTypeMirror, new String[] {});

        final List<ProcessingElement> properties = new LinkedList<>();
        if ( null != propertyElements && !propertyElements.isEmpty() ) {
            for (ExecutableElement executableElement : propertyElements) {
                final String methodName = executableElement.getSimpleName().toString();
                final TypeMirror returnTypeMirror = executableElement.getReturnType();
                String returnClassName = GeneratorUtils.getTypeMirrorDeclaredName(returnTypeMirror);
                properties.add(new ProcessingElement(returnClassName, methodName));
                print( messager, "[" + className + "] - Found property [class=" + returnClassName + "] at method [" + methodName + "].");
            }
        } else {
            print( messager, "[INFO] NO properties for definition " + className);
        }

        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "packageName",
                packageName );
        root.put( "className",
                className );
        root.put( "realClassName",
                classElement.getSimpleName().toString() );
        root.put( "identifier",
                identifier );
        root.put( "defName",
                name );
        root.put( "properties",
                properties );
        
        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter( sw );
        try {
            final Template template = config.getTemplate( "Definition.ftl" );
            template.process( root,
                    bw );
        } catch ( IOException ioe ) {
            throw new GenerationException( ioe );
        } catch ( TemplateException te ) {
            throw new GenerationException( te );
        } finally {
            try {
                bw.close();
                sw.close();
            } catch ( IOException ioe ) {
                throw new GenerationException( ioe );
            }
        }
        print( messager, "Successfully generated code for [" + className + "]" );

        return sw.getBuffer();

    }

    private String getAnnotationStringField(TypeElement classElement, String annotationName, String fieldName) {
        for (final AnnotationMirror am : classElement.getAnnotationMirrors()) {
            if (annotationName.equals(am.getAnnotationType().toString())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet()) {
                    AnnotationValue aval = entry.getValue();
                    if (fieldName.equals(entry.getKey().getSimpleName().toString())) {
                        return aval.getValue().toString();
                    }
                }
            }
        }
        return null;
    }        
        
    private void print(final Messager messager , String message ) {
        messager.printMessage( Diagnostic.Kind.NOTE, message );

    }
}
