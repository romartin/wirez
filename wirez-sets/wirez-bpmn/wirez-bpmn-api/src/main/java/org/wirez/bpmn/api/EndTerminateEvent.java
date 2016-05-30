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

import java.util.HashSet;
import java.util.Set;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.livespark.formmodeler.metaModel.Slider;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.Category;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definition.Labels;
import org.wirez.core.api.definition.annotation.definition.Property;
import org.wirez.core.api.definition.annotation.definition.PropertySet;
import org.wirez.core.api.definition.annotation.definition.Title;
import org.wirez.core.api.graph.Node;

@Portable
@Bindable
@Definition( type = Node.class )
public class EndTerminateEvent implements BPMNDefinition {

    @Category
    public static final transient String category = "End Events";

    @Title
    public static final transient String title = "End Terminate Event";

    @Description
    public static final transient String description = "Triggering the immediate termination of a process instance. " +
            "All steps still in execution in parallel branches are terminated";

    public static final transient String COLOR = "#000000";
    public static final transient String BORDER_COLOR = "#000000";
    public static final transient String RING_COLOR = "#FF0000";
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
    @FieldDef(label = "Radius", property = "value")
    @Slider( min = 25.0, max = 50.0, step = 1.0, precision = 0.0 )
    private Radius radius;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "sequence_end" );
        add( "to_task_event" );
        add( "from_task_event" );
        add( "fromtoall" );
        add( "choreography_sequence_end" );
        add( "EndEventsMorph" );
    }};

    public EndTerminateEvent() {

    }

    public EndTerminateEvent(@MapsTo("general") BPMNGeneral general,
                             @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                             @MapsTo("fontSet") FontSet fontSet,
                             @MapsTo("throwEventAttributes") ThrowEventAttributes throwEventAttributes,
                             @MapsTo("radius") Radius radius) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.throwEventAttributes = throwEventAttributes;
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

    public ThrowEventAttributes getThrowEventAttributes() {
        return throwEventAttributes;
    }

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public void setThrowEventAttributes( ThrowEventAttributes throwEventAttributes ) {
        this.throwEventAttributes = throwEventAttributes;
    }

    public void setFontSet( FontSet fontSet ) {
        this.fontSet = fontSet;
    }

    public void setRadius( Radius radius ) {
        this.radius = radius;
    }
}
