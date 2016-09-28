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
import org.wirez.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.SimulationSet;
import org.wirez.bpmn.definition.property.task.ReusableSubprocessTaskExecutionSet;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.PropertySet;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.rule.annotation.CanDock;

import javax.validation.Valid;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = ReusableSubprocess.ReusableSubprocessBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
public class ReusableSubprocess extends BaseSubprocess {

    @Title
    public static final transient String title = "Reusable Subprocess";

    @Description
    public static final transient String description = "A reusable subprocess. It can be used to invoke another process.";


    @PropertySet
    @FieldDef( label = "Implementation/Execution", position = 1)
    @Valid
    protected ReusableSubprocessTaskExecutionSet executionSet;

    @NonPortable
    public static class ReusableSubprocessBuilder  extends BaseSubprocessBuilder<ReusableSubprocess> {

        @Override
        public ReusableSubprocess build() {
            return new ReusableSubprocess(
                    new BPMNGeneral( "Subprocess" ),
                    new ReusableSubprocessTaskExecutionSet(),
                    new BackgroundSet(COLOR, BORDER_COLOR, BORDER_SIZE),
                    new FontSet(),
                    new RectangleDimensionsSet(WIDTH, HEIGHT),
                    new SimulationSet());
        }
    }
    
    public ReusableSubprocess() {
        super();
    }

    public ReusableSubprocess(@MapsTo("general") BPMNGeneral general,
                              @MapsTo("executionSet") ReusableSubprocessTaskExecutionSet executionSet,
                              @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                              @MapsTo("fontSet") FontSet fontSet,
                              @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                              @MapsTo("simulationSet") SimulationSet simulationSet) {

        super(general, backgroundSet, fontSet, dimensionsSet, simulationSet);
        this.executionSet = executionSet;
    }

    public String getTitle() {
        return title;
    }

    public ReusableSubprocessTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(ReusableSubprocessTaskExecutionSet executionSet) {
        this.executionSet = executionSet;
    }
}
