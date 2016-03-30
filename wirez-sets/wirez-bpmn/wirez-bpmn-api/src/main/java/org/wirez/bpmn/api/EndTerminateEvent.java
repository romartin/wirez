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
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.Node;

import java.util.HashSet;

@Portable
@Bindable
@Definition( type = Node.class )
public class EndTerminateEvent extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "EndTerminateEvent";
    public static final String COLOR = "#000000";
    public static final String BORDER_COLOR = "#000000";
    public static final String RING_COLOR = "#FF0000";
    public static final Double RADIUS = 14d;
    
    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private ThrowEventAttributes throwEventAttributes;

    @PropertySet
    private FontSet fontSet;
    
    @Property
    private Radius radius;
    
    public EndTerminateEvent() {
        super("End Events", "End Terminate Event", "Triggering the immediate termination of a process instance. " +
                "All steps still in execution in parallel branches are terminated",
                new HashSet<String>(){{
                    add( "all" );
                    add( "sequence_end" );
                    add( "to_task_event" );
                    add( "from_task_event" );
                    add( "fromtoall" );
                    add( "choreography_sequence_end" );
                    add( "EndEventsMorph" );
                }});
    }

    public EndTerminateEvent(@MapsTo("general") BPMNGeneral general,
                             @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                             @MapsTo("fontSet") FontSet fontSet,
                             @MapsTo("throwEventAttributes") ThrowEventAttributes throwEventAttributes,
                             @MapsTo("radius") Radius radius) {
        this();
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.throwEventAttributes = throwEventAttributes;
        this.radius = radius;
    }

    @Override
    public String getId() {
        return ID;
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

    public void setGeneral(BPMNGeneral general) {
        this.general = general;
    }

    public void setBackgroundSet(BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }

    public void setRadius(Radius radius) {
        this.radius = radius;
    }

    public ThrowEventAttributes getThrowEventAttributes() {
        return throwEventAttributes;
    }

    public void setThrowEventAttributes(ThrowEventAttributes throwEventAttributes) {
        this.throwEventAttributes = throwEventAttributes;
    }
}
