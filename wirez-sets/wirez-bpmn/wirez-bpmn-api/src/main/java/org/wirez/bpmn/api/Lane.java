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
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.rule.CanContain;
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.Node;

import java.util.HashSet;

@Portable
@Bindable
@Definition( type = Node.class )
@CanContain( roles = { "all" } )
public class Lane extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "Lane";
    public static final String COLOR = "#ffffff";
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
    private Width width;

    @Property
    private Height height;
    
    public Lane() {
        super("Swimlanes", "Lane", "Pools and Lanes represent responsibilities for activities in a process. " +
                "A pool or a lane can be an organization, a role, or a system. " +
                "Lanes sub-divide pools or other lanes hierarchically.",
                new HashSet<String>(){{
                    add( "all" );
                    add( "PoolChild" );
                    add( "fromtoall" );
                    add( "canContainArtifacts" );
                }});
    }

    public Lane(@MapsTo("general") BPMNGeneral general,
                @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                @MapsTo("fontSet") FontSet fontSet,
                @MapsTo("width") Width width,
                @MapsTo("height") Height height) {
        this();
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.width = width;
        this.height = height;
    }

    public Lane buildDefaults() {
        getGeneral().getName().setValue("My lane");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getBackgroundSet().getBorderSize().setValue(BORDER_SIZE);
        getWidth().setValue(WIDTH);
        getHeight().setValue(HEIGHT);
        return this;
    }
    
    @Override
    public String getId() {
        return ID;
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

    public void setGeneral(BPMNGeneral general) {
        this.general = general;
    }

    public void setBackgroundSet(BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }

    public void setWidth(Width width) {
        this.width = width;
    }

    public void setHeight(Height height) {
        this.height = height;
    }
}
