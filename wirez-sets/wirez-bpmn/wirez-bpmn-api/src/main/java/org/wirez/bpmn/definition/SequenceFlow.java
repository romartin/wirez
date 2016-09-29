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

package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.connectors.SequenceFlowExecutionSet;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.SimulationSet;
import org.wirez.bpmn.definition.property.task.BusinessRuleTaskExecutionSet;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.shape.proxy.SequenceFlowConnectorProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.builder.Builder;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.rule.annotation.CanConnect;
import org.wirez.core.rule.annotation.EdgeOccurrences;
import org.wirez.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( graphFactory = EdgeFactory.class, builder = SequenceFlow.SequenceFlowBuilder.class )
// Connection rules.
@CanConnect( startRole = "sequence_start", endRole = "sequence_end" )
@CanConnect( startRole = "choreography_sequence_start", endRole = "choreography_sequence_end" )
@CanConnect( startRole = "Exclusive_Eventbased_Gateway", endRole = "FromEventbasedGateway" )
@CanConnect( startRole = "EventbasedGateway", endRole = "FromEventbasedGateway" )
// Edge cardinality rules.
@EdgeOccurrences(role="Startevents_all", type = EdgeOccurrences.EdgeType.INCOMING, max = 0)
@EdgeOccurrences(role="Endevents_all", type = EdgeOccurrences.EdgeType.OUTGOING, max = 0)
public class SequenceFlow extends BaseConnector {

    @Title
    public static final transient String title = "Sequence Flow";

    @PropertySet
    @FieldDef( label = "Implementation/Execution", position = 1)
    @Valid
    protected SequenceFlowExecutionSet executionSet;

    @NonPortable
    public static class SequenceFlowBuilder extends BaseConnectorBuilder<SequenceFlow> {
        @Override
        public SequenceFlow build() {
            return new SequenceFlow(  new BPMNGeneral( "Sequence" ),
                    new SequenceFlowExecutionSet(),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet());
        }
    }

    public SequenceFlow() {

    }

    public SequenceFlow(@MapsTo("general") BPMNGeneral general,
                        @MapsTo("executionSet") SequenceFlowExecutionSet executionSet,
                        @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                        @MapsTo("fontSet") FontSet fontSet) {

        super(general, backgroundSet, fontSet);
        this.executionSet = executionSet;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public BPMNGeneral getGeneral() {
        return general;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public SequenceFlowExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(SequenceFlowExecutionSet executionSet) {
        this.executionSet = executionSet;
    }
}
