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

package org.wirez.client.shapes.factory;

import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.shapes.proxy.*;
import org.wirez.client.shapes.view.ShapeViewFactory;
import org.wirez.client.shapes.view.WiresCircleView;
import org.wirez.client.shapes.view.WiresPolygonView;
import org.wirez.client.shapes.view.WiresRectangleView;
import org.wirez.client.shapes.view.glyph.WiresCircleGlyph;
import org.wirez.client.shapes.view.glyph.WiresPolygonGlyph;
import org.wirez.client.shapes.view.glyph.WiresRectangleGlyph;
import org.wirez.core.api.definition.util.DefinitionUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.AbstractProxyShapeFactory;
import org.wirez.core.client.shape.factory.ShapeGlyphFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BasicShapesFactoryImpl<W> 
        extends AbstractProxyShapeFactory<W, ShapeView, MutableShape<W, ShapeView>, BasicShapeProxy<W>>
        implements BasicShapesFactory<W, AbstractCanvasHandler> {

    protected static final double DEFAULT_SIZE = 50;

    protected ShapeViewFactory shapeViewFactory;
    protected DefinitionUtils definitionUtils;
    
    protected BasicShapesFactoryImpl() {
    }
    
    @Inject
    public BasicShapesFactoryImpl(final ShapeViewFactory shapeViewFactory,
                                  final DefinitionUtils definitionUtils ) {

        this.shapeViewFactory = shapeViewFactory;
        this.definitionUtils = definitionUtils;
        
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public MutableShape<W, ShapeView> build( final W definition, 
                     final AbstractCanvasHandler context) {

        final WiresCanvas wiresCanvas = (WiresCanvas) context.getCanvas();
        final String id = definitionUtils.getDefinitionId( definition );
        final BasicShapeProxy<W> proxy = getProxy( id );

        if ( isCircle(proxy) ) {

            final CircleProxy<W> circleProxy = (CircleProxy<W>) proxy;
            
            final double radius = circleProxy.getRadius( definition );
            
            final WiresCircleView view =
                    shapeViewFactory.circle( radius, wiresCanvas.getWiresManager() );

            return new CircleShape( view, circleProxy );
            
        }

        if ( isRectangle(proxy) ) {

            final RectangleProxy<W> rectangleProxy = (RectangleProxy<W>) proxy;

            final double width = rectangleProxy.getWidth( definition );
            final double height = rectangleProxy.getHeight( definition );

            final WiresRectangleView view =
                    shapeViewFactory.rectangle( width, height, wiresCanvas.getWiresManager() );

            return new RectangleShape( view, rectangleProxy );

        }

        if ( isPolygon(proxy) ) {

            final PolygonProxy<W> polygonProxy = (PolygonProxy<W>) proxy;

            final double radius = polygonProxy.getRadius( definition );
            final String fillColor = polygonProxy.getBackgroundColor( definition );

            final WiresPolygonView view =
                    shapeViewFactory.polygon( radius, 
                            fillColor,
                            wiresCanvas.getWiresManager() );

            return new PolygonShape( view, polygonProxy );

        }
        
        throw new RuntimeException( "This factory supports [" + id + "] but cannot built the shape for it." );
    }

    @Override
    protected ShapeGlyph build(Class<?> clazz, double width, double height) {

        final BasicShapeProxy<W> proxy = getProxy( clazz );

        if ( isCircle(proxy) ) {

            return new WiresCircleGlyph( DEFAULT_SIZE / 2, proxy.getGlyphBackgroundColor() );

        }

        if ( isRectangle(proxy) ) {

            return new WiresRectangleGlyph( DEFAULT_SIZE, DEFAULT_SIZE, proxy.getGlyphBackgroundColor() );

        }

        if ( isPolygon(proxy) ) {

            return new WiresPolygonGlyph( DEFAULT_SIZE / 2, proxy.getGlyphBackgroundColor() );

        }

        throw new RuntimeException( "This factory supports [" + clazz.getName() + "] but cannot built the shape glyph for it." );
        
    }

    @Override
    public ShapeGlyph build( final String definitionId ) {

        return build( definitionId, DEFAULT_SIZE, DEFAULT_SIZE );

    }

    @Override
    protected ShapeGlyph build( final Class<?> clazz ) {
        return build( clazz, DEFAULT_SIZE, DEFAULT_SIZE );
    }

    @Override
    public ShapeGlyphFactory getGlyphFactory( final String definitionId ) {
        return this;
    }

    private boolean isCircle( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof CircleProxy;
    }

    private boolean isRectangle( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof RectangleProxy;
    }

    private boolean isPolygon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof PolygonProxy;
    }

}
