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
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.rule.CanContain;
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
@CanContain( roles = { "all" } )
public class BPMNDiagram extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "BPMNDiagram";
    public static final String COLOR = "#FFFFFF";
    public static final String BORDER_COLOR = "#000000";
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
    
    public BPMNDiagram() {
        super("Diagram", "BPMN Diagram", "BPMN Diagram",
                new HashSet<String>(){{
                    add( "canContainArtifacts" );
                    add( "diagram" );
                }});
    }

    public BPMNDiagram(@MapsTo("general") BPMNGeneral general,
                       @MapsTo("diagramSet") DiagramSet diagramSet,
                       @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                       @MapsTo("fontSet") FontSet fontSet,
                       @MapsTo("width") Width width,
                       @MapsTo("height") Height height) {
        this();
        this.general = general;
        this.diagramSet = diagramSet;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.width = width;
        this.height = height;
    }

    public BPMNDiagram buildDefaults() {
        getGeneral().getName().setValue("My BPMN diagram");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getBackgroundSet().getBorderSize().setValue(BORDER_SIZE);
        getBackgroundSet().getBorderColor().setValue(BORDER_COLOR);
        getWidth().setValue(WIDTH);
        getHeight().setValue(HEIGHT);
        return this;
    }

    @Override
    public String getId() {
        return ID;
    }
    
    public DiagramSet getDiagramSet() {
        return diagramSet;
    }

    public BPMNGeneral getGeneral() {
        return general;
    }

    public void setGeneral(final BPMNGeneral general) {
        this.general = general;
    }

    public void setDiagramSet(final DiagramSet diagramSet) {
        this.diagramSet = diagramSet;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public Width getWidth() {
        return width;
    }

    public void setWidth(Width width) {
        this.width = width;
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
}
