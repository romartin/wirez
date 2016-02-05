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

import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.SimpleArrow;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.client.SequenceFlowShape;
import org.wirez.bpmn.client.glyph.SequenceFlowGlyph;
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
public class SequenceFlowShapeFactory extends BaseShapeFactory<SequenceFlow, SequenceFlowShape> {

    public SequenceFlowShapeFactory() {
    }
    
    @Inject
    public SequenceFlowShapeFactory(final DefaultShapeControlFactories defaultShapeControlFactories,
                            final ShapeGlyphDragHandler shapeGlyphDragHandler) {
        super(defaultShapeControlFactories, shapeGlyphDragHandler);
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof SequenceFlow;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return SequenceFlowGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A sequence flow";
    }

    @Override
    public SequenceFlowShape build(final SequenceFlow definition, final CanvasHandler canvasHandler) {
        final BaseCanvas baseWirezCanvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();

        OrthogonalPolyLine line = createLine(0,0,100,100);
        SequenceFlowShape connector = new SequenceFlowShape(line, null, new SimpleArrow(20, 0.75), baseWirezCanvas.getWiresManager());

        return connector;
    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

   

}
