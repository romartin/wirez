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
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.bpmn.api.property.simulation.CatchEventAttributes;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.*;
import org.wirez.core.api.graph.Node;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
public class StartNoneEvent implements BPMNDefinition {

    @Category
    public static final transient String category = "Start Events";

    @Title
    public static final transient String title = "Start Event";

    @Description
    public static final transient String description = "Untyped start event";
    
    
    public static final transient String COLOR = "#3eb870";
    public static final Double RADIUS = 15d;
    
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

}
