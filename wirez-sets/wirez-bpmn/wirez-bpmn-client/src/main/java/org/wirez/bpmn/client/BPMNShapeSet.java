/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.bpmn.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.bpmn.client.factory.ParallelGatewayShapeFactory;
import org.wirez.bpmn.client.factory.SequenceFlowShapeFactory;
import org.wirez.bpmn.client.factory.StartNoneEventShapeFactory;
import org.wirez.bpmn.client.factory.TaskShapeFactory;
import org.wirez.bpmn.client.resources.BPMNImageResources;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class BPMNShapeSet implements ShapeSet {

    public static final String ID = "basic";

    BPMNDefinitionSet bpmnDefinitionSet;
    StartNoneEventShapeFactory startNoneEventShapeFactory;
    TaskShapeFactory taskShapeFactory;
    SequenceFlowShapeFactory sequenceFlowShapeFactory;
    ParallelGatewayShapeFactory parallelGatewayShapeFactory;
    
    private List<ShapeFactory<? extends Definition, ? extends Shape>> factories;

    @Inject
    public BPMNShapeSet( final BPMNDefinitionSet bpmnDefinitionSet,
                         final StartNoneEventShapeFactory startNoneEventShapeFactory,
                         final TaskShapeFactory taskShapeFactory,
                         final SequenceFlowShapeFactory sequenceFlowShapeFactory,
                         final ParallelGatewayShapeFactory parallelGatewayShapeFactory) {
        this.bpmnDefinitionSet = bpmnDefinitionSet;
        this.startNoneEventShapeFactory = startNoneEventShapeFactory;
        this.taskShapeFactory = taskShapeFactory;
        this.sequenceFlowShapeFactory = sequenceFlowShapeFactory;
        this.parallelGatewayShapeFactory = parallelGatewayShapeFactory;
    }

    @PostConstruct
    public void init() {
        factories = new LinkedList<ShapeFactory<? extends Definition, ? extends Shape>>() {{
            add( startNoneEventShapeFactory );
            add( taskShapeFactory );
            add( sequenceFlowShapeFactory );
            add( parallelGatewayShapeFactory );
        }};
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "BPMN Shapes";
    }

    @Override
    public String getDescription() {
        return "The BPMN shapes set";
    }

    @Override
    public SafeUri getThumbnailUri() {
        return BPMNImageResources.INSTANCE.bpmnSetThumb().getSafeUri();
    }

    @Override
    public String getDefinitionSetId() {
        return bpmnDefinitionSet.getId();
    }

    @Override
    public Collection<ShapeFactory<? extends Definition, ? extends Shape>> getFactories() {
        return factories;
    }
}
