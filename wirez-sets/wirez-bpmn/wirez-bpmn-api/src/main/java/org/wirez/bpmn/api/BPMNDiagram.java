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
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.*;
import org.wirez.core.api.rule.annotation.CanContain;
import org.wirez.core.api.graph.Node;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
@CanContain( roles = { "all" } )
public class BPMNDiagram implements BPMNDefinition {

    @Category
    public static final transient String category = "Diagram";
    
    @Title
    public static final transient String title = "BPMN Diagram";
    
    @Description
    public static final transient String description = "BPMN Diagam";
    
    public static final transient String COLOR = "#FFFFFF";
    public static final transient String BORDER_COLOR = "#000000";
    public static final Double BORDER_SIZE = 1d;
    public static final Double WIDTH = 950d;
    public static final Double HEIGHT = 950d;

    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private DiagramSet diagramSet;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    private Width width;

    @Property
    private Height height;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "canContainArtifacts" );
        add( "diagram" );
    }};

    public BPMNDiagram() {

    }

    public BPMNDiagram(@MapsTo("general") BPMNGeneral general,
                @MapsTo("diagramSet") DiagramSet diagramSet,
                @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                @MapsTo("fontSet") FontSet fontSet,
                @MapsTo("width") Width width,
                @MapsTo("height") Height height) {
        this.general = general;
        this.diagramSet = diagramSet;
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

    public DiagramSet getDiagramSet() {
        return diagramSet;
    }

    public BPMNGeneral getGeneral() {
        return general;
    }

    public Height getHeight() {
        return height;
    }

    public Width getWidth() {
        return width;
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

    public void setDiagramSet( DiagramSet diagramSet ) {
        this.diagramSet = diagramSet;
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
