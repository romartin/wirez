/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.shared.core.types.EventPropagationMode;
import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.client.ParallelGatewayShape;
import org.wirez.bpmn.client.glyph.ParallelGatewayGlyph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.factory.BaseShapeFactory;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ParallelGatewayShapeFactory extends BaseShapeFactory<ParallelGateway, ParallelGatewayShape> {

    @Inject
    public ParallelGatewayShapeFactory(final DefaultShapeControlFactories defaultShapeControlFactories,
                          final ShapeGlyphDragHandler shapeGlyphDragHandler) {
        super(defaultShapeControlFactories, shapeGlyphDragHandler);
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof ParallelGateway;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return ParallelGatewayGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A parallel gateway";
    }

    @Override
    public ParallelGatewayShape build(final ParallelGateway definition, final CanvasHandler canvasHandler) {

        Group group = new Group();

        group.setDraggable(true);

        group.setEventPropagationMode(EventPropagationMode.FIRST_ANCESTOR);

        final BaseCanvas baseCanvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();
        
        return new ParallelGatewayShape(group, baseCanvas.getWiresManager());

    }

}
