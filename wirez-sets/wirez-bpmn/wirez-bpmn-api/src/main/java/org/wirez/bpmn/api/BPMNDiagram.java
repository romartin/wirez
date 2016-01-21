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
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.graph.Graph;
import org.wirez.core.api.annotation.rule.CanContain;
import org.wirez.core.api.graph.impl.DefaultGraph;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;

@Portable
@Graph( type = DefaultGraph.class )
@CanContain( roles = {"all", "diagram"} )
public class BPMNDiagram extends BPMNDefinition {

    public static final String ID = "bpmnDiagram";

    @Inject
    private BPMNGeneral bpmnGeneral;
    
    @Inject
    private DiagramSet diagramSet;
    
    public BPMNDiagram() {
        super("Diagram", "BPMN Diagram", "BPMN Diagram",
                new HashSet<String>(){{
                    add( "all" );
                    add( "diagram" );
                }});
    }
    
    @PostConstruct
    public void init() {
        getGeneral().getName().setValue("My diagram");
    }

    @Override
    public String getId() {
        return ID;
    }
    
    @PropertySet
    public DiagramSet getDiagramSet() {
        return diagramSet;
    }

    @PropertySet
    public BPMNGeneral getGeneral() {
        return bpmnGeneral;
    }
}
