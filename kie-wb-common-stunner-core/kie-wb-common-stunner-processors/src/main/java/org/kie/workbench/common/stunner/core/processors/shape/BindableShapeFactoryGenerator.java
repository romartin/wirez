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

package org.kie.workbench.common.stunner.core.processors.shape;

import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactoryWrapper;
import org.kie.workbench.common.stunner.core.processors.AbstractBindableAdapterGenerator;
import org.kie.workbench.common.stunner.core.processors.MainProcessor;
import org.kie.workbench.common.stunner.core.processors.ProcessingDefinitionAnnotations;
import org.kie.workbench.common.stunner.core.processors.ProcessingEntity;

import javax.annotation.processing.Messager;
import java.util.*;

public class BindableShapeFactoryGenerator extends AbstractBindableAdapterGenerator {

    @Override
    protected String getTemplatePath() {
        return "BindableShapeFactory.ftl";
    }

    public StringBuffer generate(String packageName, String className,
                                 ProcessingDefinitionAnnotations processingDefinitionAnnotations,
                                 Messager messager) throws GenerationException {

        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("generatedByClassName",
                BindableShapeFactoryGenerator.class.getName() );
        root.put("parentClassName",
                ShapeFactoryWrapper.class.getName() );

        Map<String, String[]> shapeProxies = processingDefinitionAnnotations.getShapeProxies();
        Set<String> definitionClasses = shapeProxies.keySet();
        Collection<String[]> values = shapeProxies.values();
        Collection<String> factoryClasses = getCollection( values, 0 );
        Collection<String> proxyClasses = getCollection( values, 1 );

        Collection<ProcessingEntity> shapeProxyFactoryEntities = new LinkedList<>();
        for ( String s : factoryClasses ) {
            shapeProxyFactoryEntities.add( new ProcessingEntity( s, MainProcessor.toClassMemberId( s ) ) );
        }
        
        root.put("shapeProxyFactoryEntities",
                shapeProxyFactoryEntities );
        
        root.put("definitionClasses",
                definitionClasses);
        root.put("proxyClasses",
                proxyClasses );
        

        Collection<String> addProxySentences = new LinkedList<>();
        for ( Map.Entry<String, String[]> entry : shapeProxies.entrySet() ) {

            String classname = entry.getKey();
            String factoryClass = entry.getValue()[0];
            String factoryId = MainProcessor.toClassMemberId( factoryClass );
            String proxyClass = entry.getValue()[1];
            
            addProxySentences.add( factoryId + ".addProxy( " + classname + ".class, new " + proxyClass + "() );" );
            
        }

        root.put("addProxySentences",
                addProxySentences );
        
        //Generate code
        return writeTemplate(packageName, className, root, messager);
    }
    
    private Collection<String> getCollection( Collection<String[]> c, 
                                              int index ) {
        
        Collection<String> result = new LinkedHashSet<>();
        for ( String[] s : c ) {
            result.add( s[index] );
        }
        
        return result;
        
    }
    
}
