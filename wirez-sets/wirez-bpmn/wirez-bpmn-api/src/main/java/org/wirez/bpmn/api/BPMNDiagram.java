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

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.rule.CanContain;
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.impl.DefaultGraph;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = DefaultGraph.class )
@CanContain( roles = { "all" } )
public class BPMNDiagram extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "BPMNDiagram";

    @PropertySet
    private BPMNGeneral general;
    
    @PropertySet
    private DiagramSet diagramSet;


    public BPMNDiagram() {
        super("Diagram", "BPMN Diagram", "BPMN Diagram",
                new HashSet<String>(){{
                    add( "canContainArtifacts" );
                }});
    }

    public BPMNDiagram(@MapsTo("general") BPMNGeneral general,
                       @MapsTo("diagramSet") DiagramSet diagramSet) {
        this();
        this.general = general;
        this.diagramSet = diagramSet;
    }

    public BPMNDiagram buildDefaults() {
        getGeneral().getName().setValue("My diagram");
        return this;
    }

    @Override
    public String getId() {
        return ID;
    }
    
    public DiagramSet getDiagramSet() {
        return diagramSet;
    }

    public BPMNGeneral getGeneral() {
        return general;
    }

    public void setGeneral(final BPMNGeneral general) {
        this.general = general;
    }

    public void setDiagramSet(final DiagramSet diagramSet) {
        this.diagramSet = diagramSet;
    }
}
