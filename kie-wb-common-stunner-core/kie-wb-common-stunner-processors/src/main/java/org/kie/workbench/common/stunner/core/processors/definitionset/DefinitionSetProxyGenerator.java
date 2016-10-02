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

package org.kie.workbench.common.stunner.core.processors.definitionset;

import org.kie.workbench.common.stunner.core.definition.DefinitionSetProxy;
import org.kie.workbench.common.stunner.core.processors.AbstractAdapterGenerator;
import org.kie.workbench.common.stunner.core.processors.ProcessingEntity;
import org.uberfire.annotations.processors.exceptions.GenerationException;

import javax.annotation.processing.Messager;
import java.util.HashMap;
import java.util.Map;

public class DefinitionSetProxyGenerator extends AbstractAdapterGenerator {

    @Override
    protected String getTemplatePath() {
        return "DefinitionSetProxy.ftl";
    }

    public StringBuffer generate(String packageName, String className,
                                 ProcessingEntity definitionSetProcessedEntity,
                                 Map<String, String> buildersMap,
                                 Messager messager) throws GenerationException {

        String defSetClassName = definitionSetProcessedEntity.getClassName();

        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("generatedByClassName",
                DefinitionSetProxyGenerator.class.getName());
        root.put("parentFQCName",
                DefinitionSetProxy.class.getName());
        root.put("defSetFQCName",
                defSetClassName );

        String builder = "new " + defSetClassName + "()";
        
        if ( null != buildersMap && !buildersMap.isEmpty() ) {

            String builderClass = buildersMap.get( defSetClassName );
            
            if ( null != builderClass && builderClass.trim().length() > 0 ) {
                builder = "new " + builderClass + "().build()";
            }
            
        }

        // Builder.
        root.put("builder",
                builder );
        
        //Generate code
        return writeTemplate(packageName, className, root, messager);
    }
    
}
