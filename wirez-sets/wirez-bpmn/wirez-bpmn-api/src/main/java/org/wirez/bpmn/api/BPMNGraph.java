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

import java.util.HashSet;

@Portable
@Bindable
@Definition( type = DefaultGraph.class )
@CanContain( roles = { "diagram" } )
public class BPMNGraph extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "BPMNGraph";

    @PropertySet
    private BPMNGeneral general;
    
    public BPMNGraph() {
        super("Graph", "BPMN Graph", "BPMN Graph",
                new HashSet<String>(){{
                    add( "graph" );
                }});
    }

    public BPMNGraph(@MapsTo("general") BPMNGeneral general) {
        this();
        this.general = general;
    }

    public BPMNGraph buildDefaults() {
        getGeneral().getName().setValue("My diagram");
        return this;
    }

    @Override
    public String getId() {
        return ID;
    }
    
    public BPMNGeneral getGeneral() {
        return general;
    }

    public void setGeneral(final BPMNGeneral general) {
        this.general = general;
    }

}
