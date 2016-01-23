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

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.graph.Graph;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;

@Portable
@Bindable
@Definition
@Graph( type = Node.class )
public class StartNoneEvent extends BPMNDefinition {

    public static final String ID = "StartNoneEvent";
    public static final String COLOR = "@0000CC";
    public static final Integer RADIUS = 25;
    
    @Inject
    @PropertySet
    private BPMNGeneral general;

    @Inject
    @PropertySet
    private BackgroundSet backgroundSet;

    @Inject
    @PropertySet
    private FontSet fontSet;
    
    @Inject
    @Property
    private Radius radius;
    
    public StartNoneEvent() {
        super("Start Events", "Start Event", "Untyped start event",
                new HashSet<String>(){{
                    add( "all" );
                    add( "diagram" );
                }});
    }

    @PostConstruct
    public void init() {
        getGeneral().getName().setValue("My start event");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getRadius().setValue(RADIUS);
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
}