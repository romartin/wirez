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

import org.wirez.bpmn.api.EndNoneEvent;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.client.shapes.WiresCircleView;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.HasTitle;

public class EndNoneEventShape extends BPMNBasicShape<EndNoneEvent, WiresCircleView> {

    public EndNoneEventShape(final WiresCircleView view) {
        super(view);
        getShapeView().setPosition(HasTitle.Position.BOTTOM);
    }

    @Override
    public void applyProperties(final Node<View<EndNoneEvent>, Edge> element, final MutationContext mutationContext) {
        super.applyProperties(element, mutationContext);

        // Radius.
        _applyRadius(element, mutationContext);
    }

    @Override
    protected Name getNameProperty(final Node<View<EndNoneEvent>, Edge> element) {
        return element.getContent().getDefinition().getGeneral().getName();
    }

    protected EndNoneEventShape _applyRadius(final Node<View<EndNoneEvent>, Edge> element, final MutationContext mutationContext) {
        final Radius radiusProperty  = element.getContent().getDefinition().getRadius();
        final Double radius = radiusProperty.getValue();
        if ( null != radius ) {
            applyRadius(getShapeView(), radius, mutationContext);
            GraphUtils.updateBounds(radius, element.getContent());
        }
        return this;
    }

    @Override
    protected BackgroundSet getBackgroundSet(final Node<View<EndNoneEvent>, Edge> element) {
        return element.getContent().getDefinition().getBackgroundSet();
    }

    @Override
    protected FontSet getFontSet(final Node<View<EndNoneEvent>, Edge> element) {
        return element.getContent().getDefinition().getFontSet();
    }
    
    @Override
    public String toString() {
        return "EndNoneEventShape{}";
    }

    
}
