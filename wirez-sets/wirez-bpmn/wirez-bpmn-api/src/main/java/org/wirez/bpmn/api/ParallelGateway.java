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
import org.wirez.bpmn.api.property.background.BackgroundSet;
import org.wirez.bpmn.api.property.font.FontSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.client.proxy.ParallelGatewayShapeProxy;
import org.wirez.client.shapes.factory.BasicShapesFactory;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.*;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.annotation.Shape;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
@Shape( factory = BasicShapesFactory.class,
        proxy = ParallelGatewayShapeProxy.class )
public class ParallelGateway implements BPMNDefinition {

    @Category
    public static final transient String category = "Gateways";

    @Title
    public static final transient String title = "BPMN Diagram";

    @Description
    public static final transient String description = "Parallel Gateway";
    
    public static final transient String COLOR = "#f0e68c";
    public static final Double RADIUS = 20d;

    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    private Radius radius;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "sequence_start" );
        add( "sequence_end" );
        add( "choreography_sequence_start" );
        add( "choreography_sequence_end" );
        add( "fromtoall" );
        add( "GatewaysMorph" );
    }};

    public ParallelGateway() {

    }

    public ParallelGateway(@MapsTo("general") BPMNGeneral general,
                           @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                           @MapsTo("fontSet") FontSet fontSet,
                           @MapsTo("radius") Radius radius) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
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

    public BPMNGeneral getGeneral() {
        return general;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public Radius getRadius() {
        return radius;
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

    public void setRadius( Radius radius ) {
        this.radius = radius;
    }
}
