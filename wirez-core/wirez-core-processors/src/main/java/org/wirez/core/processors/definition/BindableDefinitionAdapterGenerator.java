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

import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.api.definition.adapter.binding.BindableDefinitionAdapter;
import org.wirez.core.processors.AbstractBindableAdapterGenerator;
import org.wirez.core.processors.ProcessingDefinitionAnnotations;

import javax.annotation.processing.Messager;
import java.util.HashMap;
import java.util.Map;

public class BindableDefinitionAdapterGenerator extends AbstractBindableAdapterGenerator {

    @Override
    protected String getTemplatePath() {
        return "BindableDefinitionAdapter.ftl";
    }

    public StringBuffer generate(String packageName, String className,
                                 ProcessingDefinitionAnnotations processingDefinitionAnnotations,
                                 Messager messager) throws GenerationException {

        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("parentAdapterClassName",
                BindableDefinitionAdapter.class.getName());
        root.put("generatedByClassName",
                BindableDefinitionAdapterGenerator.class.getName());
        
        addFields("categoryFieldNames", root, processingDefinitionAnnotations.getCategoryFieldNames());
        addFields("titleFieldNames", root, processingDefinitionAnnotations.getTitleFieldNames());
        addFields("descriptionFieldNames", root, processingDefinitionAnnotations.getDescriptionFieldNames());
        addFields("labelsFieldNames", root, processingDefinitionAnnotations.getLabelsFieldNames());
        addFields("graphElementFieldNames", root, processingDefinitionAnnotations.getGraphElementFieldNames());
        addFields("elementFactoryFieldNames", root, processingDefinitionAnnotations.getElementFactoryFieldNames());
        addMultipleFields("propertySetsFieldNames", root, processingDefinitionAnnotations.getPropertySetFieldNames());
        addMultipleFields("propertiesFieldNames", root, processingDefinitionAnnotations.getPropertyFieldNames());

        //Generate code
        return writeTemplate(packageName, className, root, messager);
    }
    
}
