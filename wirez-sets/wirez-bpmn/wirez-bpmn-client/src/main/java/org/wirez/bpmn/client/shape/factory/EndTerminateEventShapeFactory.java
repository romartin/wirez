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

import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.client.shape.EndTerminateEventShape;
import org.wirez.bpmn.client.shape.glyph.EndTerminateEventGlyph;
import org.wirez.client.shapes.ShapeViewFactory;
import org.wirez.client.shapes.WiresCircleView;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.shape.factory.ShapeGlyphFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EndTerminateEventShapeFactory extends BaseBPMNShapeFactory<EndTerminateEvent, EndTerminateEventShape> implements ShapeGlyphFactory {
    
    public EndTerminateEventShapeFactory() {
    }

    @Inject
    public EndTerminateEventShapeFactory(final ShapeViewFactory shapeViewFactory) {
        super(shapeViewFactory);
    }

    @Override
    public boolean accepts(final String definitionId) {
        return EndTerminateEvent.class.getSimpleName().equals( definitionId );
    }

    @Override
    public ShapeGlyphFactory getGlyphFactory() {
        return this;
    }

    @Override
    public String getDescription() {
        return "An end terminate event";
    }

    @Override
    public EndTerminateEventShape build(final EndTerminateEvent definition, final AbstractCanvasHandler canvasHandler) {
        final WiresCanvas wiresCanvas = (WiresCanvas) canvasHandler.getCanvas();
        final WiresCircleView view = shapeViewFactory.circle( definition.getRadius().getValue(), 
                wiresCanvas.getWiresManager());
        return new EndTerminateEventShape(view);
    }

    @Override
    public ShapeGlyph build() {
        return build(50, 50);
    }

    @Override
    public ShapeGlyph build(double width, double height) {
        return new EndTerminateEventGlyph(width/2, EndTerminateEvent.COLOR);
    }

}
