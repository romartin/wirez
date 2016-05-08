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

import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.api.definition.adapter.binding.BindablePropertyAdapter;
import org.wirez.core.processors.AbstractBindableAdapterGenerator;
import org.wirez.core.processors.ProcessingPropertyAnnotations;

import javax.annotation.processing.Messager;
import java.util.HashMap;
import java.util.Map;

public class BindablePropertyAdapterGenerator extends AbstractBindableAdapterGenerator {

    @Override
    protected String getTemplatePath() {
        return "BindablePropertyAdapter.ftl";
    }

    public StringBuffer generate(String packageName, String className,
                                 ProcessingPropertyAnnotations processingPropertyAnnotations,
                                 Messager messager) throws GenerationException {

        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("parentAdapterClassName",
                BindablePropertyAdapter.class.getName());
        root.put("generatedByClassName",
                BindablePropertyAdapterGenerator.class.getName());
        
        addFields("valuePropNames", root, processingPropertyAnnotations.getValueFieldNames());
        addFields("defaultValuePropNames", root, processingPropertyAnnotations.getDefaultValueFieldNames());
        addFields("captionPropNames", root, processingPropertyAnnotations.getCaptionFieldNames());
        addFields("descriptionPropNames", root, processingPropertyAnnotations.getDescriptionFieldNames());
        addFields("propTypePropNames", root, processingPropertyAnnotations.getTypeFieldNames());
        addFields("readOnlyPropNames", root, processingPropertyAnnotations.getReadOnlyFieldNames());
        addFields("optionalPropNames", root, processingPropertyAnnotations.getOptionalFieldNames());

        //Generate code
        return writeTemplate(packageName, className, root, messager);
    }
    
}
