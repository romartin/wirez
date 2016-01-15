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

import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.processors.GeneratorUtils;
import org.wirez.core.processors.ProcessingElement;

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
import java.util.List;
import java.util.Map;

public class PropertyGenerator extends AbstractGenerator  {

    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        messager.printMessage( Diagnostic.Kind.NOTE, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final boolean isInterface = classElement.getKind().isInterface();
        final String annotationName = PropertyProcessor.ANNOTATION_PROPERTY;

        String identifier = null;
        String type = null;
        String caption = null;
        String description = null;
        boolean isReadOnly = false;
        boolean isOptional = false;
        boolean isPublic = false;

        for ( final AnnotationMirror am : classElement.getAnnotationMirrors() ) {
            if ( annotationName.equals( am.getAnnotationType().toString() ) ) {
                for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() ) {
                    AnnotationValue aval = entry.getValue();
                    if ( "identifier".equals( entry.getKey().getSimpleName().toString() ) ) {
                        identifier = aval.getValue().toString();
                    } else if ( "caption".equals( entry.getKey().getSimpleName().toString() ) ) {
                        caption = aval.getValue().toString();
                    }
                    else if ( "description".equals( entry.getKey().getSimpleName().toString() ) ) {
                        description = aval.getValue().toString();
                    }
                    else if ( "isReadOnly".equals( entry.getKey().getSimpleName().toString() ) ) {
                        isReadOnly = (boolean) aval.getValue();
                    }
                    else if ( "isOptional".equals( entry.getKey().getSimpleName().toString() ) ) {
                        isOptional = (boolean) aval.getValue();
                    }
                    else if ( "isPublic".equals( entry.getKey().getSimpleName().toString() ) ) {
                        isPublic = (boolean) aval.getValue();
                    }
                    else if ( "type".equals( entry.getKey().getSimpleName().toString() ) ) {
                        type = aval.getValue().toString();
                    }
                }
                break;
            }
        }

        messager.printMessage( Diagnostic.Kind.WARNING, "isReadOnly" + isReadOnly+ "]" );


        // Field types.
        ProcessingElement defaultValueElement = null;
        List<VariableElement> variableElements = ElementFilter.fieldsIn( classElement.getEnclosedElements() );
        for (VariableElement variableElement : variableElements) {
            
            // Default Value.
            if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, PropertyProcessor.ANNOTATION_DEFAULT_VALUE ) != null ) {
                final TypeMirror fieldReturnType = variableElement.asType();
                final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                final String fieldName = variableElement.getSimpleName().toString();
                defaultValueElement = new ProcessingElement(fieldReturnTypeName, fieldName);
            }
            
        }
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "packageName",
                packageName );
        root.put( "className",
                className );
        root.put( "classHierarchyModifier",
                isInterface ? "implements" : "extends" );
        root.put( "realClassName",
                classElement.getSimpleName().toString() );
        root.put( "identifier",
                identifier );
        root.put( "typeName",
                type );
        root.put( "caption",
                caption );
        root.put( "description",
                description );
        root.put( "isReadOnly",
                isReadOnly + "" );
        root.put( "isOptional",
                isOptional + "" );
        root.put( "isPublic",
                isPublic + "" );
        root.put( "defaultValue",
                defaultValueElement );
        
        
        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter( sw );
        try {
            final Template template = config.getTemplate( "Property.ftl" );
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
        messager.printMessage( Diagnostic.Kind.NOTE, "Successfully generated code for [" + className + "]" );

        return sw.getBuffer();

    }

}
