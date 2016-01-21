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
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.core.api.annotation.definition.PropertySet;
import org.wirez.core.api.annotation.graph.Graph;
import org.wirez.core.api.graph.Node;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;

@Portable
@Graph( type = Node.class )
public class StartNoneEvent extends BPMNDefinition {

    public static final String ID = "StartNoneEvent";
    
    @Inject
    private BPMNGeneral bpmnBaseSet;
    
    public StartNoneEvent() {
        super("Start Events", "Start Event", "Untyped start event",
                new HashSet<String>(){{
                    add( "all" );
                    add( "diagram" );
                }});
    }

    @PostConstruct
    public void init() {
        getBpmnBaseSet().getName().setValue("My start event");
    }
    @Override
    public String getId() {
        return ID;
    }
    
    @PropertySet
    public BPMNGeneral getBpmnBaseSet() {
        return bpmnBaseSet;
    }
    
}
