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

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.client.shapes.WiresRectangleView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.Context;
import org.wirez.core.client.mutation.GraphContext;
import org.wirez.core.client.view.HasTitle;

public class BPMNDiagramShape extends BPMNBasicShape<BPMNDiagram> {

    public BPMNDiagramShape(final WiresRectangleView view) {
        super(view);
        getView().setPosition(HasTitle.Position.TOP);
    }

    protected WiresRectangleView getView() {
        return (WiresRectangleView) view;
    }
    @Override
    public void applyElementProperties(Node<View<BPMNDiagram>, Edge> element, CanvasHandler wirezCanvas, GraphContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);

        // Size.
        _applySize(element, mutationContext);

    }

    @Override
    public void afterMutations(final Canvas canvas) {
        super.afterMutations(canvas);
        getView().setFillAlpha(0.8);
    }

    protected BPMNDiagramShape _applySize(final Node<View<BPMNDiagram>, Edge> element, GraphContext mutationContext) {
        final Width widthProperty  = element.getContent().getDefinition().getWidth();
        final Height heightProperty  = element.getContent().getDefinition().getHeight();
        final Double width = widthProperty.getValue();
        final Double height = heightProperty.getValue();
        applySize(width, height, mutationContext);
        GraphUtils.updateBounds(width, height, element.getContent());
        return this;
    }

    protected void applySize(final double width, final double height, final Context mutationContext) {
        getView().setSize(width, height);
    }

    @Override
    protected BackgroundSet getBackgroundSet(final Node<View<BPMNDiagram>, Edge> element) {
        return element.getContent().getDefinition().getBackgroundSet();
    }

    @Override
    protected FontSet getFontSet(final Node<View<BPMNDiagram>, Edge> element) {
        return element.getContent().getDefinition().getFontSet();
    }

    @Override
    public String toString() {
        return "BPMNDiagramShape{}";
    }

}
