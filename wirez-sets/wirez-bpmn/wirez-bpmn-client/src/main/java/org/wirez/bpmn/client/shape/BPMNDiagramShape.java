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

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.client.shapes.WiresRectangleView;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.shape.view.HasTitle;

public class BPMNDiagramShape extends BPMNBasicShape<BPMNDiagram, WiresRectangleView> {

    public BPMNDiagramShape(final WiresRectangleView view) {
        super(view);
        getShapeView().setPosition(HasTitle.Position.TOP);
    }

    @Override
    protected Name getNameProperty(final Node<View<BPMNDiagram>, Edge> element) {
        return element.getContent().getDefinition().getGeneral().getName();
    }

    @Override
    public void applyProperties(final Node<View<BPMNDiagram>, Edge> element) {
        super.applyProperties(element);

        // Size.
        _applySize(element);

    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        getShapeView().setFillAlpha(0.8);
    }

    protected BPMNDiagramShape _applySize(final Node<View<BPMNDiagram>, Edge> element) {
        final Width widthProperty  = element.getContent().getDefinition().getWidth();
        final Height heightProperty  = element.getContent().getDefinition().getHeight();
        final Double width = widthProperty.getValue();
        final Double height = heightProperty.getValue();
        applySize(width, height);
        GraphUtils.updateBounds(width, height, element.getContent());
        return this;
    }

    protected void applySize(final double width, final double height) {
        getShapeView().setSize(width, height);
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
