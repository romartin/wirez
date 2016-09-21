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
import org.wirez.bpmn.shape.proxy.ReusableSubprocessShapeProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.annotation.morph.MorphBase;
import org.wirez.core.definition.builder.Builder;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = ReusableSubprocess.ReusableSubprocessBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = ReusableSubprocessShapeProxy.class )
@MorphBase( defaultType = ReusableSubprocess.class, targets = { BaseTask.class } )
public class ReusableSubprocess implements BPMNDefinition {

    @Category
    public static final transient String category = Categories.ACTIVITIES;

    @Title
    public static final transient String title = "Reusable Subprocess";
    
    @Description
    public static final transient String description = "A reusable subprocess. It can be used to invoke another process.";

    @PropertySet
    @FieldDef( label = "General Settings", position = 0)
    @Valid
    protected BPMNGeneral general;

    @PropertySet
    @FieldDef( label = "Background Settings", position = 2)
    @Valid
    protected BackgroundSet backgroundSet;

    @PropertySet
    @FieldDef( label = "Font Settings")
    protected FontSet fontSet;

    @PropertySet
    @FieldDef( label = "Shape Dimensions", position = 5)
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
    public static class ReusableSubprocessBuilder implements Builder<ReusableSubprocess> {

        public static final String COLOR = "#fafad2";
        public static final Double WIDTH = 136d;
        public static final Double HEIGHT = 48d;
        public static final Double BORDER_SIZE = 1d;
        public static final String BORDER_COLOR = "#000000";

        @Override
        public ReusableSubprocess build() {
            return new ReusableSubprocess( new BPMNGeneral( "Subprocess" ),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new RectangleDimensionsSet(WIDTH, HEIGHT) );
        }
    }
    
    public ReusableSubprocess() {
    }
    
    public ReusableSubprocess(@MapsTo("general") BPMNGeneral general,
                              @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                              @MapsTo("fontSet") FontSet fontSet,
                              @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.dimensionsSet = dimensionsSet;
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

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public void setFontSet( FontSet fontSet ) {
        this.fontSet = fontSet;
    }

    public RectangleDimensionsSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet(RectangleDimensionsSet dimensionsSet) {
        this.dimensionsSet = dimensionsSet;
    }
}
