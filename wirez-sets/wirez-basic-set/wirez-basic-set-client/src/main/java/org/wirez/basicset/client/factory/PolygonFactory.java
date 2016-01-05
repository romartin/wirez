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

package org.wirez.basicset.client.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.shared.core.types.EventPropagationMode;
import org.wirez.basicset.api.Polygon;
import org.wirez.basicset.client.PolygonShape;
import org.wirez.basicset.client.factory.control.RadiusBasedResizeControlFactory;
import org.wirez.basicset.client.glyph.PolygonGlyph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.factory.BaseShapeFactory;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PolygonFactory extends BaseShapeFactory<Polygon, PolygonShape> {

    RadiusBasedResizeControlFactory radiusBasedResizeControlFactory;

    @Inject
    public PolygonFactory(final DefaultShapeControlFactories defaultShapeControlFactories,
                          final RadiusBasedResizeControlFactory radiusBasedResizeControlFactory,
                          final ShapeGlyphDragHandler shapeGlyphDragHandler) {
        super(defaultShapeControlFactories, shapeGlyphDragHandler);
        this.radiusBasedResizeControlFactory = radiusBasedResizeControlFactory;
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof Polygon;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return PolygonGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A polygon";
    }

    @Override
    protected ShapeControlFactory<?, ?> getResizeControlFactory() {
        return radiusBasedResizeControlFactory;
    }

    @Override
    public PolygonShape build(final Polygon definition, final CanvasHandler canvasHandler) {

        final double radius = Polygon.RADIUS * 2;
        MultiPath path = new MultiPath().rect(0, 0, radius, radius).setStrokeAlpha(0);

        path.setDraggable(false);

        Group group = new Group();

        group.add(path);

        group.setDraggable(true);

        group.setEventPropagationMode(EventPropagationMode.FIRST_ANCESTOR);

        final BaseCanvas baseCanvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();
        
        return new PolygonShape(path, group, baseCanvas.getWiresManager());

    }

}
