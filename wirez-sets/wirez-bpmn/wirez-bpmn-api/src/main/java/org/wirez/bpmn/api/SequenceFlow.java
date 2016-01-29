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
import org.wirez.core.api.definition.BaseDefinition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Edge.class )
public class SequenceFlow extends BaseDefinition implements BPMNDefinition {

    public static final String ID = "SequenceFlow";
    public static final String COLOR = "#000000";
    
    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    public SequenceFlow() {
        super("Connecting Objects", "Sequence Flow", "A Sequence Flow",
                new HashSet<String>(){{
                    add( "all" );
                    add( "ConnectingObjectsMorph" );
                }});
    }
    
    public SequenceFlow(@MapsTo("general") BPMNGeneral general,
                        @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                        @MapsTo("fontSet") FontSet fontSet) {
        this();
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        init();
    }

    private void init() {
        getGeneral().getName().setValue("My sequence flow");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getBackgroundSet().getBorderSize().setValue(3);
        
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

}
