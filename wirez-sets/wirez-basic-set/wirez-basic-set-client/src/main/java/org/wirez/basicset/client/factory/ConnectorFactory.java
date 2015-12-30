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

package org.wirez.basicset.client.factory;

import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.SimpleArrow;
import com.ait.lienzo.client.core.types.Point2DArray;
import org.wirez.basicset.api.Connector;
import org.wirez.basicset.client.ConnectorShape;
import org.wirez.basicset.client.glyph.ConnectorGlyph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.factory.BaseShapeFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ConnectorFactory extends BaseShapeFactory<Connector, ConnectorShape> {

    @Inject
    public ConnectorFactory() {
        super();
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof Connector;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return ConnectorGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A connector";
    }

    @Override
    public ConnectorShape build(final Connector definition, final CanvasHandler canvasHandler) {
        final BaseCanvas baseWirezCanvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();

        OrthogonalPolyLine line = createLine(0,0,100,100);
        ConnectorShape connector = new ConnectorShape(line, null, new SimpleArrow(20, 0.75), baseWirezCanvas.getWiresManager());

        return connector;
    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

   

}
