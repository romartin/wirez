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

import org.wirez.bpmn.api.property.background.BackgroundSet;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.client.shapes.AbstractConnector;
import org.wirez.core.client.shape.view.ShapeView;

public abstract class BPMNBasicConnector<W, V extends ShapeView> 
        extends AbstractConnector<W, Edge<ViewConnector<W>, Node>, V> {


    public BPMNBasicConnector(final V view) {
        super(view);
    }

    @Override
    public void applyProperties(final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Fill color.
        _applyFillColor(element, mutationContext);

        // Apply border styles.
        _applyBorders(element, mutationContext);

    }
    
    protected abstract BackgroundSet getBackgroundSet(Edge<ViewConnector<W>, Node> element);
    
    protected BPMNBasicConnector<W, V> _applyFillColor(final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext) {
        final String color = getBackgroundSet( element ).getBgColor().getValue();
        super._applyFillColor(color, mutationContext);
        return this;
    }

    protected BPMNBasicConnector<W, V> _applyBorders(final Edge<ViewConnector<W>, Node> element, final MutationContext mutationContext) {
        final String color = getBackgroundSet( element ).getBorderColor().getValue();
        final Double width = getBackgroundSet( element ).getBorderSize().getValue();
        super._applyBorders(color, width, mutationContext);
        return this;
    }

}
