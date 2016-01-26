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

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.mutation.HasSizeMutation;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class TaskShape extends BPMNBasicShape<Task> implements HasSizeMutation {

    private static final Shadow SHADOW = new Shadow(Color.fromColorString(Task.COLOR).setA(0.80), 10, -10, -10);

    private Rectangle decorator;
    
    public TaskShape(Group group, WiresManager manager) {
        super(new MultiPath().rect(0, 0, Task.WIDTH, Task.HEIGHT), group, manager);
        
        init();
    }

    private void init() {
        decorator = new Rectangle(Task.WIDTH, Task.HEIGHT).setX(0).setY(0).setFillAlpha(0).setStrokeAlpha(0);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( decorator );
        }};
    }

    @Override
    public void applyElementProperties(Node<ViewContent<Task>, Edge> element, CanvasHandler wirezCanvas, MutationContext mutationContext) {
        super.applyElementProperties(element, wirezCanvas, mutationContext);
        
        // Size.
        _applySize(element, mutationContext);
        
    }

    protected TaskShape _applySize(final Node<ViewContent<Task>, Edge> element, MutationContext mutationContext) {
        final Width widthProperty  = (Width) ElementUtils.getProperty(element, Width.ID);
        final Height heightProperty  = (Height) ElementUtils.getProperty(element, Height.ID);
        final Integer width = widthProperty.getValue();
        final Integer height = heightProperty.getValue();
        applySize(width, height, mutationContext);
        return this;
    }

    @Override
    public String toString() {
        return "TaskShape{}";
    }

    @Override
    public void applySize(final double width, final double height, final MutationContext mutationContext) {
        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
        decorator.setWidth(width);
        decorator.setHeight(height);
    }
    
}
