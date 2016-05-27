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
import org.wirez.bpmn.definition.property.Radius;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.CatchEventAttributes;
import org.wirez.bpmn.shape.proxy.StartNoneEventShapeProxy;
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
@Definition( type = Node.class, builder = StartNoneEvent.StartNoneEventBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = StartNoneEventShapeProxy.class )
public class StartNoneEvent implements BPMNDefinition {

    @Category
    public static final transient String category = "Start Events";

    @Title
    public static final transient String title = "Start Event";

    @Description
    public static final transient String description = "Untyped start event";

    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @PropertySet
    private CatchEventAttributes catchEventAttributes;

    @Property
    private Radius radius;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "Startevents_all" );
        add( "sequence_start" );
        add( "choreography_sequence_start" );
        add( "to_task_event" );
        add( "from_task_event" );
        add( "fromtoall" );
        add( "StartEventsMorph" );
    }};

    @NonPortable
    public static class StartNoneEventBuilder implements Builder<StartNoneEvent> {

        public static final transient String COLOR = "#cae294";
        public static final Double BORDER_SIZE = 1d;
        public static final String BORDER_COLOR = "#000000";
        public static final Double RADIUS = 15d;

        @Override
        public StartNoneEvent build() {
            return new StartNoneEvent(  new BPMNGeneral( "Start" ),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new CatchEventAttributes(),
                    new Radius( RADIUS ) );
        }

    }

    public StartNoneEvent() {

    }

    public StartNoneEvent(@MapsTo("general") BPMNGeneral general,
                   @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                   @MapsTo("fontSet") FontSet fontSet,
                   @MapsTo("catchEventAttributes") CatchEventAttributes catchEventAttributes,
                   @MapsTo("radius") Radius radius) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.catchEventAttributes = catchEventAttributes;
        this.radius = radius;
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

    public Radius getRadius() {
        return radius;
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

    public CatchEventAttributes getCatchEventAttributes() {
        return catchEventAttributes;
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

    public void setCatchEventAttributes( CatchEventAttributes catchEventAttributes ) {
        this.catchEventAttributes = catchEventAttributes;
    }

    public void setRadius( Radius radius ) {
        this.radius = radius;
    }
}
