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

package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.bpmn.api.content.BPMNContent;
import org.wirez.bpmn.api.property.Package;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.graph.Graph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultGraphImpl;

import java.util.HashSet;

@Portable
@org.wirez.core.api.annotation.definition.Definition
@Graph( type = DefaultGraph.class )
public class BPMNDiagram implements Definition<BPMNContent> {

    public static final String ID = "bpmnDiagram";
    
    private BPMNContent content;
    private Package thePackage;
    
    public BPMNDiagram() {
        content = new BPMNContent("Diagram", "BPMN Diagram", "A BPMN Diagram", 
                new HashSet<String>(){{
                    add( "all" );
                    add( "diagram" );
                }});
        thePackage = new Package();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public BPMNContent getDefinitionContent() {
        return content;
    }
    
    @Property
    public Package getPackage() {
        return thePackage;
    }
}
