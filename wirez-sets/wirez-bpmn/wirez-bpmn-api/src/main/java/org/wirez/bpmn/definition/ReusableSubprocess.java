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
import org.wirez.bpmn.definition.property.Height;
import org.wirez.bpmn.definition.property.Width;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.shape.proxy.ReusableSubprocessShapeProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.factory.Builder;
import org.wirez.core.graph.Node;
import org.wirez.shapes.factory.BasicShapesFactory;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class, builder = ReusableSubprocess.ReusableSubprocessBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = ReusableSubprocessShapeProxy.class )
public class ReusableSubprocess implements BPMNDefinition {

    @Category
    public static final transient String category = Categories.ACTIVITIES;

    @Title
    public static final transient String title = "Reusable Subprocess";
    
    @Description
    public static final transient String description = "A reusable subprocess. It can be used to invoke another process.";

    @PropertySet
    protected BPMNGeneral general;

    @PropertySet
    protected BackgroundSet backgroundSet;

    @PropertySet
    protected FontSet fontSet;

    @Property
    protected Width width;

    @Property
    protected Height height;

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
                    new Width( WIDTH ),
                    new Height( HEIGHT ) );
        }
    }
    
    public ReusableSubprocess() {
    }
    
    public ReusableSubprocess(@MapsTo("general") BPMNGeneral general,
                              @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                              @MapsTo("fontSet") FontSet fontSet,
                              @MapsTo("width") Width width,
                              @MapsTo("height") Height height) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.width = width;
        this.height = height;
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

    public Width getWidth() {
        return width;
    }

    public Height getHeight() {
        return height;
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

    public void setWidth( Width width ) {
        this.width = width;
    }

    public void setHeight( Height height ) {
        this.height = height;
    }

}
