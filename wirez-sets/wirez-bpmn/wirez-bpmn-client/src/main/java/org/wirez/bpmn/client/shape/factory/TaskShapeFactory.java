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

package org.wirez.bpmn.client.shape.factory;

import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.client.shape.BPMNViewFactory;
import org.wirez.bpmn.client.shape.TaskShape;
import org.wirez.client.shapes.ShapeViewFactory;
import org.wirez.client.shapes.WiresRectangleView;
import org.wirez.client.shapes.glyph.WiresRectangleGlyph;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.shape.factory.ShapeGlyphFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class TaskShapeFactory extends BaseBPMNShapeFactory<Task, TaskShape> implements ShapeGlyphFactory {

    BPMNViewFactory bpmnViewFactory;

    private static final Set<Class<?>> SUPPORTED_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(Task.class);
    }};

    public TaskShapeFactory() {
    }
    
    @Inject
    public TaskShapeFactory(final ShapeViewFactory shapeViewFactory,
                            final BPMNViewFactory bpmnViewFactory) {
        super(shapeViewFactory);
        this.bpmnViewFactory = bpmnViewFactory;
    }

    @Override
    protected Set<Class<?>> getSupportedModelClasses() {
        return SUPPORTED_CLASSES;
    }

    @Override
    public ShapeGlyphFactory getGlyphFactory() {
        return this;
    }

    @Override
    public String getDescription() {
        return "A task";
    }

    @Override
    public TaskShape build(final Task definition, final AbstractCanvasHandler canvasHandler) {

        final WiresCanvas wiresCanvas = (WiresCanvas) canvasHandler.getCanvas(); 
        final WiresRectangleView view = bpmnViewFactory.task( definition.getWidth().getValue(), 
                definition.getHeight().getValue(), wiresCanvas.getWiresManager());
        return new TaskShape(view);

    }

    @Override
    public ShapeGlyph build() {
        return build(50, 50);
    }

    @Override
    public ShapeGlyph build(double width, double height) {
        return new WiresRectangleGlyph(width, height, Task.COLOR);
    }
    
}
