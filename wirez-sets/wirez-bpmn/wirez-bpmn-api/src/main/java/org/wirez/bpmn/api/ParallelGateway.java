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
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.annotation.definition.Definition;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
public class ParallelGateway extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "ParallelGateway";
    public static final String COLOR = "#f0e68c";
    public static final Integer RADIUS = 50;
    public static final Integer BORDER_SIZE = 3;
    
    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    private Radius radius;

    public ParallelGateway() {
        super("Gateways", "Parallel Gateway", "When used to split the sequence flow, " +
                        "all outgoing branches are activated simultaneously. " +
                        "When merging parallel branches it waits for all incoming branches to complete before triggering the outgoing flow",
                new HashSet<String>(){{
                    add( "all" );
                    add( "sequence_start" );
                    add( "sequence_end" );
                    // TODO
                }});
    }

    public ParallelGateway(@MapsTo("general") BPMNGeneral general,
                           @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                           @MapsTo("fontSet") FontSet fontSet,
                           @MapsTo("radius") Radius radius) {
        this();
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.radius = radius;
    }

    public ParallelGateway buildDefaults() {
        getGeneral().getName().setValue("My gateway");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getBackgroundSet().getBorderSize().setValue(3);
        getRadius().setValue(RADIUS);
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

    public Radius getRadius() {
        return radius;
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
