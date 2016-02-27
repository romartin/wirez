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

package org.wirez.bpmn.client.factory;

import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.client.BPMNViewFactory;
import org.wirez.bpmn.client.TaskShape;
import org.wirez.bpmn.client.factory.control.BPMNToolboxControlFactory;
import org.wirez.client.shapes.ShapeViewFactory;
import org.wirez.client.shapes.WiresRectangleView;
import org.wirez.client.shapes.glyph.WiresRectangleGlyph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.factory.ShapeGlyphFactory;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;
import org.wirez.core.client.view.ShapeGlyph;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TaskShapeFactory extends BaseBPMNShapeFactory<Task, TaskShape> implements ShapeGlyphFactory {

    BPMNViewFactory bpmnViewFactory;
    
    public TaskShapeFactory() {
    }
    
    @Inject
    public TaskShapeFactory(final ShapeViewFactory shapeViewFactory,
                            final BPMNViewFactory bpmnViewFactory,
                            final DefaultShapeControlFactories defaultShapeControlFactories,
                            final ShapeGlyphDragHandler shapeGlyphDragHandler,
                            final BPMNToolboxControlFactory bpmnToolboxControlFactory) {
        super(shapeViewFactory, defaultShapeControlFactories, shapeGlyphDragHandler, bpmnToolboxControlFactory);
        this.bpmnViewFactory = bpmnViewFactory;
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof Task;
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
    protected ShapeControlFactory<?, ?> getResizeControlFactory() {
        return defaultShapeControlFactories.lienzoResizeControlFactory();
    }

    @Override
    public TaskShape build(final Task definition, final CanvasHandler canvasHandler) {

        final WiresCanvas wiresCanvas = (WiresCanvas) canvasHandler.getCanvas(); 
        final WiresRectangleView view = bpmnViewFactory.task( Task.WIDTH, Task.HEIGHT, wiresCanvas.getWiresManager());
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
