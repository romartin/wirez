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

package org.wirez.bpmn.client.shape;

import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.api.property.background.BackgroundSet;
import org.wirez.client.shapes.view.ConnectorView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.ViewConnector;

public class SequenceFlowShape extends BPMNBasicConnector<SequenceFlow, ConnectorView> {

    public SequenceFlowShape(final ConnectorView view) {
        super(view);
    }
    
    @Override
    protected BackgroundSet getBackgroundSet(final Edge<ViewConnector<SequenceFlow>, Node> element) {
        return element.getContent().getDefinition().getBackgroundSet();
    }
    
    @Override
    public void destroy() {

    }

    @Override
    public String toString() {
        return "SequenceFlowShape{}";
    }

}
