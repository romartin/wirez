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
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.annotation.definition.Property;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.graph.Graph;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;

@Portable
@Graph( type = Node.class )
public class Task extends BPMNDefinition {

    public static final String ID = "Task";
    public static final String COLOR = "#00CC00";
    public static final Integer WIDTH = 100;
    public static final Integer HEIGHT = 100;
    
    @Inject
    private BPMNGeneral bpmnBaseSet;

    @Inject
    private BackgroundSet backgroundSet;

    @Inject
    private FontSet fontSet;

    @Inject
    private Width width;

    @Inject
    private Height height;


    public Task() {
        super("Activities", "Task", "A task is a unit of work - the job to be performed",
                new HashSet<String>(){{
                    add( "all" );
                    add( "sequence_start" );
                    add( "sequence_end" );
                    // TODO
                }});
    }

    @PostConstruct
    public void init() {
        getGeneralSet().getName().setValue("My task");
        getBackgroundSet().getBgColor().setValue(COLOR);
        getBackgroundSet().getBorderSize().setValue(3);
        getWidth().setValue(WIDTH);
        getHeight().setValue(HEIGHT);
        
    }
    @Override
    public String getId() {
        return ID;
    }

    @PropertySet
    public BPMNGeneral getGeneralSet() {
        return bpmnBaseSet;
    }

    @PropertySet
    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    @PropertySet
    public FontSet getFontSet() {
        return fontSet;
    }

    @Property
    public Width getWidth() {
        return width;
    }

    @Property
    public Height getHeight() {
        return height;
    }
}
