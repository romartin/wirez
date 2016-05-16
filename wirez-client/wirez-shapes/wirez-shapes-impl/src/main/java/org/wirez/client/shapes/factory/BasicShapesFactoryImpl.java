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
import org.wirez.client.shapes.AbstractShape;
import org.wirez.client.shapes.HasChildren;
import org.wirez.client.shapes.proxy.*;
import org.wirez.client.shapes.proxy.icon.*;
import org.wirez.client.shapes.view.*;
import org.wirez.client.shapes.view.glyph.*;
import org.wirez.core.api.definition.util.DefinitionUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.AbstractProxyShapeFactory;
import org.wirez.core.client.shape.factory.ShapeGlyphFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Map;

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

        final String id = definitionUtils.getDefinitionId( definition );
        final BasicShapeProxy<W> proxy = getProxy( id );

        return build( definition, proxy, context );
    }

    @SuppressWarnings("unchecked")
    protected MutableShape<W, ShapeView> build( final W definition,
                                                final BasicShapeProxy<W> proxy,
                                                final AbstractCanvasHandler context) {

        final WiresCanvas wiresCanvas = (WiresCanvas) context.getCanvas();

        MutableShape<W, ShapeView> shape = null;
        
        if ( isCircle(proxy) ) {

            final CircleProxy<W> circleProxy = (CircleProxy<W>) proxy;

            final double radius = circleProxy.getRadius( definition );

            final CircleView view =
                    shapeViewFactory.circle( radius, wiresCanvas.getWiresManager() );

            shape = new CircleShape( view, circleProxy );

        }

        if ( isRectangle(proxy) ) {

            final RectangleProxy<W> rectangleProxy = (RectangleProxy<W>) proxy;

            final double width = rectangleProxy.getWidth( definition );
            final double height = rectangleProxy.getHeight( definition );

            final RectangleView view =
                    shapeViewFactory.rectangle( width, height, wiresCanvas.getWiresManager() );

            shape = new RectangleShape( view, rectangleProxy );

        }

        if ( isPolygon(proxy) ) {

            final PolygonProxy<W> polygonProxy = (PolygonProxy<W>) proxy;

            final double radius = polygonProxy.getRadius( definition );
            final String fillColor = polygonProxy.getBackgroundColor( definition );

            final PolygonView view =
                    shapeViewFactory.polygon( radius,
                            fillColor,
                            wiresCanvas.getWiresManager() );

            shape = new PolygonShape( view, polygonProxy );

        }

        if ( isIcon(proxy) ) {

            final IconProxy<W> iconProxy = (IconProxy<W>) proxy;

            final ICONS icon = IconShape.getIcon( definition, iconProxy );
            final double width = iconProxy.getWidth( definition );
            final double height = iconProxy.getHeight( definition );

            final IconShapeView view =
                    shapeViewFactory.icon( icon, width, height, wiresCanvas.getWiresManager() );

            shape = new IconShape( view, iconProxy );

        }
        
        // Add children, if any.
        if ( null != shape && proxy instanceof HasChildProxies ) {

            final HasChildProxies<W> hasChildren = (HasChildProxies<W>) proxy;
            final Map<BasicShapeProxy<W>, HasChildren.Layout> childProxies = hasChildren.getChildProxies();
            if ( null != childProxies && !childProxies.isEmpty() ) {
                for ( final Map.Entry<BasicShapeProxy<W>, HasChildren.Layout> entry : childProxies.entrySet() ) {

                    final BasicShapeProxy<W> child = entry.getKey();
                    final HasChildren.Layout layout = entry.getValue();

                    final MutableShape<W, ShapeView> childShape = this.build( definition, child, context);

                    if ( childShape instanceof AbstractShape ) {

                        ( (AbstractShape) shape).addChild( (AbstractShape) childShape, layout );

                    }
                    
                }
            }

        }

        if ( null != shape ) {
        
            return shape;
            
        }
        
        final String id = definitionUtils.getDefinitionId( definition );
        throw new RuntimeException( "This factory supports [" + id + "] but cannot built a shape for it." );

    }
    
    @Override
    protected ShapeGlyph build( Class<?> clazz, double width, double height) {

        final BasicShapeProxy<W> proxy = getProxy( clazz );

        ShapeGlyph glyph = null;
        
        if ( isCircle(proxy) ) {

            glyph = new CircleGlyph( DEFAULT_SIZE / 2, proxy.getGlyphBackgroundColor() );
            
            

        }

        if ( isRectangle(proxy) ) {

            glyph = new RectangleGlyph( DEFAULT_SIZE, DEFAULT_SIZE, proxy.getGlyphBackgroundColor() );

        }

        if ( isPolygon(proxy) ) {

            glyph = new PolygonGlyph( DEFAULT_SIZE / 2, proxy.getGlyphBackgroundColor() );
            
        }

        if ( isMinusIcon(proxy) ) {
            
            return new IconGlyph ( ICONS.MINUS, DEFAULT_SIZE, DEFAULT_SIZE, proxy.getGlyphBackgroundColor() );

        }

        if ( isPlusIcon(proxy) ) {

            return new IconGlyph ( ICONS.PLUS, DEFAULT_SIZE, DEFAULT_SIZE, proxy.getGlyphBackgroundColor() );

        }

        if ( isXORIcon(proxy) ) {

            return new IconGlyph ( ICONS.XOR, DEFAULT_SIZE, DEFAULT_SIZE, proxy.getGlyphBackgroundColor() );

        }
        
        if ( null != glyph && hasIcon(proxy) ) {

            return new ShapeWithIconGlyph( DEFAULT_SIZE, DEFAULT_SIZE, glyph, ICONS.PLUS );
            
        } else if ( null != glyph ) {
            
            return glyph;
            
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
    
    private boolean hasIcon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof HasChildProxies;
    }

    private boolean isIcon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof IconProxy;
    }

    private boolean isMinusIcon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof MinusIconProxy;
    }

    private boolean isPlusIcon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof PlusIconProxy;
    }

    private boolean isXORIcon( final BasicShapeProxy<W> proxy ) {
        return proxy instanceof XORIconProxy;
    }
    

}
