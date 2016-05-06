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
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.Category;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definition.Labels;
import org.wirez.core.api.definition.annotation.definition.Property;
import org.wirez.core.api.definition.annotation.definition.PropertySet;
import org.wirez.core.api.definition.annotation.definition.Title;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.rule.annotation.CanContain;

@Portable
@Bindable
@Definition( type = Node.class )
@CanContain( roles = { "all" } )
public class Lane implements BPMNDefinition {

    @Category
    public static final transient String category = "Swimlanes";

    @Title
    public static final transient String title = "Lane";

    @Description
    public static final transient String description = "Pools and Lanes represent responsibilities for activities in a process. " +
            "A pool or a lane can be an organization, a role, or a system. " +
            "Lanes sub-divide pools or other lanes hierarchically.";

    public static final transient String COLOR = "#ffffff";
    public static final Double WIDTH = 450d;
    public static final Double HEIGHT = 250d;
    public static final Double BORDER_SIZE = 1d;

    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    @FieldDef(label = "Width", property = "value")
    private Width width;

    @Property
    @FieldDef(label = "Height", property = "value")
    private Height height;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "PoolChild" );
        add( "fromtoall" );
        add( "canContainArtifacts" );
    }};

    public Lane() {

    }

    public Lane(@MapsTo("general") BPMNGeneral general,
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

    public void setHeight( Height height ) {
        this.height = height;
    }

    public void setWidth( Width width ) {
        this.width = width;
    }

    public void setFontSet( FontSet fontSet ) {
        this.fontSet = fontSet;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }
}
