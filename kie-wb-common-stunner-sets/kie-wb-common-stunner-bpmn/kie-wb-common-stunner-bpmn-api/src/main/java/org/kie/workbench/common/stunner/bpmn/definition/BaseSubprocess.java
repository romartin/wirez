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
package org.kie.workbench.common.stunner.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.BPMNGeneralSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.shape.def.SubprocessShapeDef;
import org.kie.workbench.common.stunner.core.definition.annotation.Description;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Shape;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphBase;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;
import org.kie.workbench.common.stunner.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Shape( factory = BasicShapesFactory.class, def = SubprocessShapeDef.class )
@MorphBase( defaultType = ReusableSubprocess.class, targets = { BaseTask.class } )
public abstract class BaseSubprocess implements BPMNDefinition {
    @Category
    public static final transient String category = Categories.ACTIVITIES;

    @Description
    public static final transient String description = "A subprocess is a decomposable activity.";

    @PropertySet
    @FieldDef( label = "General Settings", position = 0)
    @Valid
    protected BPMNGeneralSet general;

    @PropertySet
    @FieldDef( label = "Background Settings", position = 3)
    @Valid
    protected BackgroundSet backgroundSet;

    @PropertySet
    //@FieldDef( label = "Font Settings", position = 4)
    protected FontSet fontSet;

    @PropertySet
    //@FieldDef( label = "Process Simulation", position = 5)
    protected SimulationSet simulationSet;

    @PropertySet
    @FieldDef( label = "Shape Dimensions", position = 6)
    protected RectangleDimensionsSet dimensionsSet;

    @Labels
    protected final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "sequence_start" );
        add( "sequence_end" );
        add( "messageflow_start" );
        add( "messageflow_end" );
        add( "to_task_event" );
        add( "from_task_event" );
        add( "fromtoall" );
        add( "ActivitiesMorph" );
    }};

    @NonPortable
    static abstract class BaseSubprocessBuilder<T extends BaseSubprocess> implements Builder<T> {

        public static final String COLOR = "#fafad2";
        public static final Double WIDTH = 136d;
        public static final Double HEIGHT = 48d;
        public static final Double BORDER_SIZE = 1d;
        public static final String BORDER_COLOR = "#000000";

    }

    protected BaseSubprocess( ) {
    }

    public BaseSubprocess(@MapsTo("general") BPMNGeneralSet general,
                    @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                    @MapsTo("fontSet") FontSet fontSet,
                    @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                    @MapsTo("simulationSet") SimulationSet simulationSet ) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.dimensionsSet = dimensionsSet;
        this.simulationSet = simulationSet;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public BPMNGeneralSet getGeneral() {
        return general;
    }

    public void setGeneral(BPMNGeneralSet general) {
        this.general = general;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setBackgroundSet(BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }

    public SimulationSet getSimulationSet() {
        return simulationSet;
    }

    public void setSimulationSet(SimulationSet simulationSet) {
        this.simulationSet = simulationSet;
    }

    public RectangleDimensionsSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet(RectangleDimensionsSet dimensionsSet) {
        this.dimensionsSet = dimensionsSet;
    }

    public Set<String> getLabels() {
        return labels;
    }
}
