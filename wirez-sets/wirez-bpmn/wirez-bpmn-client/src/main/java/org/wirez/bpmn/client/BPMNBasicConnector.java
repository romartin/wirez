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

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.GraphContext;
import org.wirez.core.client.view.ShapeView;

public abstract class BPMNBasicConnector<W extends BPMNDefinition> 
        extends BaseConnector<W> {


    public BPMNBasicConnector(final ShapeView view) {
        super(view);
    }

    @Override
    public void applyElementProperties(Edge<View<W>, Node> element, CanvasHandler canvasHandler, GraphContext mutationContext) {
        super.applyElementProperties(element, canvasHandler, mutationContext);

        // Fill color.
        _applyFillColor(element);

        // Apply border styles.
        _applyBorders(element);

    }
    
    protected abstract BackgroundSet getBackgroundSet(Edge<View<W>, Node> element);
    
    protected BPMNBasicConnector<W> _applyFillColor(final Edge<View<W>, Node> element) {
        final String color = getBackgroundSet( element ).getBgColor().getValue();
        super._applyFillColor(color);
        return this;
    }

    protected BPMNBasicConnector<W> _applyBorders(final Edge<View<W>, Node> element) {
        final String color = getBackgroundSet( element ).getBorderColor().getValue();
        final Double width = getBackgroundSet( element ).getBorderSize().getValue();
        super._applyBorders(color, width);
        return this;
    }

}
