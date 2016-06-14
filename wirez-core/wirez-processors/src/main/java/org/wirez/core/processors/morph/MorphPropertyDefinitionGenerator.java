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

package org.wirez.core.processors.morph;

import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.wirez.core.definition.morph.BindableMorphProperty;
import org.wirez.core.definition.morph.BindablePropertyMorphDefinition;
import org.wirez.core.processors.AbstractAdapterGenerator;
import org.wirez.core.processors.AbstractBindableAdapterGenerator;
import org.wirez.core.processors.ProcessingMorphProperty;

import javax.annotation.processing.Messager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MorphPropertyDefinitionGenerator extends AbstractBindableAdapterGenerator {

    @Override
    protected String getTemplatePath() {
        return "MorphPropertyDefinition.ftl";
    }

    public StringBuffer generate(String packageName,
                                 String className,
                                 String baseMorphType,
                                 Collection<ProcessingMorphProperty> morphProperties,
                                 String defaultMorphType,
                                 Messager messager) throws GenerationException {

        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("parentClassName",
                BindablePropertyMorphDefinition.class.getName());
        root.put("bindableMorphPropertyParentClassName",
                BindableMorphProperty.class.getName());
        root.put("generatedByClassName",
                MorphPropertyDefinitionGenerator.class.getName());
        root.put("morphBaseClassName",
                baseMorphType);
        root.put("defaultTypeClassName",
                defaultMorphType);
        root.put("morphPropertiesSize",
                morphProperties.size() );
        root.put("morphProperties",
                morphProperties );


        //Generate code
        return writeTemplate(packageName, className, root, messager);
    }
    
}
