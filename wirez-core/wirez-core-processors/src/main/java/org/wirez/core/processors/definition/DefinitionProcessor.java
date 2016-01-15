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

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({DefinitionProcessor.ANNOTATION_DEFINITION})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DefinitionProcessor extends AbstractErrorAbsorbingProcessor {

    public static final String ANNOTATION_DEFINITION = "org.wirez.core.api.annotation.definition.Definition";
    public static final String ANNOTATION_DEFINITION_PROPERTY = "org.wirez.core.api.annotation.definition.Property";

    @Override
    protected boolean processWithExceptions(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) throws Exception {
        return false;
    }
    
    
}
