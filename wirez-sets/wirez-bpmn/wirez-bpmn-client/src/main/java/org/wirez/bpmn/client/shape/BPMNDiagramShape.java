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
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.animation.AnimationProperties;

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
    public void applyProperties(final Node<View<BPMNDiagram>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Size.
        _applySize(element, mutationContext);

    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        getShapeView().setFillAlpha(0.8);
    }

    protected BPMNDiagramShape _applySize(final Node<View<BPMNDiagram>, Edge> element, final MutationContext mutationContext) {
        final Width widthProperty  = element.getContent().getDefinition().getWidth();
        final Height heightProperty  = element.getContent().getDefinition().getHeight();
        final Double width = widthProperty.getValue();
        final Double height = heightProperty.getValue();
        applySize(getShapeView(), width, height, mutationContext);
        GraphUtils.updateBounds(width, height, element.getContent());
        return this;
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
