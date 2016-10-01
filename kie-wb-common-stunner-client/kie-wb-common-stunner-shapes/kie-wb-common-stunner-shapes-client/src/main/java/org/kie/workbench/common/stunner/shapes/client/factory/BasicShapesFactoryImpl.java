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

package org.kie.workbench.common.stunner.shapes.client.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.shared.core.types.ColorName;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.AbstractCompositeShape;
import org.kie.workbench.common.stunner.core.client.shape.AbstractShape;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.client.shape.MutableShape;
import org.kie.workbench.common.stunner.core.client.shape.factory.AbstractProxyShapeFactory;
import org.kie.workbench.common.stunner.core.client.shape.view.AbstractBindableShapeGlyphBuilder;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyphBuilder;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;
import org.kie.workbench.common.stunner.shapes.client.proxy.*;
import org.kie.workbench.common.stunner.shapes.client.view.*;
import org.kie.workbench.common.stunner.shapes.client.view.icon.dynamics.DynamicIconShapeView;
import org.kie.workbench.common.stunner.shapes.proxy.*;
import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.IconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.Icons;
import org.kie.workbench.common.stunner.shapes.client.proxy.DynamicIconShape;
import org.kie.workbench.common.stunner.shapes.client.proxy.RectangleShape;
import org.kie.workbench.common.stunner.shapes.client.proxy.StaticIconShape;
import org.kie.workbench.common.stunner.shapes.client.view.ConnectorView;
import org.kie.workbench.common.stunner.shapes.client.view.PolygonView;
import org.kie.workbench.common.stunner.shapes.client.view.ShapeViewFactory;
import org.kie.workbench.common.stunner.shapes.client.view.glyph.ConnectorGlyph;
import org.kie.workbench.common.stunner.shapes.client.view.icon.statics.StaticIconShapeView;
import org.kie.workbench.common.stunner.shapes.factory.BasicShapesFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class BasicShapesFactoryImpl 
        extends AbstractProxyShapeFactory<Object, ShapeView, MutableShape<Object, ShapeView>, BasicShapeProxy<Object>>
        implements BasicShapesFactory<Object, AbstractCanvasHandler> {

    protected static final double DEFAULT_SIZE = 50;

    protected ShapeViewFactory shapeViewFactory;
    protected DefinitionManager definitionManager;
    protected ShapeGlyphBuilder<Group> glyphBuilder;
    
    protected BasicShapesFactoryImpl() {
    }
    
    @Inject
    public BasicShapesFactoryImpl(final FactoryManager factoryManager,
                                  final ShapeViewFactory shapeViewFactory,
                                  final DefinitionManager definitionManager,
                                  final ShapeGlyphBuilder<Group> glyphBuilder) {
        super( factoryManager );
        this.shapeViewFactory = shapeViewFactory;
        this.definitionManager = definitionManager;
        this.glyphBuilder = glyphBuilder;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    public MutableShape<Object, ShapeView> build( final Object definition, 
                     final AbstractCanvasHandler context) {

        final String id = definitionManager.adapters().forDefinition().getId( definition );
        final BasicShapeProxy<Object> proxy = getProxy( id );

        return build( definition, proxy, context );
    }

    @SuppressWarnings("unchecked")
    protected MutableShape<Object, ShapeView> build( final Object definition,
                                                final ShapeProxy<Object> proxy,
                                                final AbstractCanvasHandler context) {

        MutableShape<Object, ShapeView> shape = null;
        
        if ( isCircle( proxy ) ) {

            final CircleProxy<Object> circleProxy = (CircleProxy<Object>) proxy;

            final double radius = circleProxy.getRadius( definition );

            final CircleView view =
                    shapeViewFactory.circle( radius );

            shape = new CircleShape( view, circleProxy );

        }

        if ( isRing( proxy ) ) {

            final RingProxy<Object> ringProxy = (RingProxy<Object>) proxy;

            final double oRadius = ringProxy.getOuterRadius( definition );

            final RingView view =
                    shapeViewFactory.ring( oRadius );

            shape = new RingShape( view, ringProxy );

        }

        if ( isRectangle( proxy ) ) {

            final RectangleProxy<Object> rectangleProxy = (RectangleProxy<Object>) proxy;

            final double width = rectangleProxy.getWidth( definition );
            final double height = rectangleProxy.getHeight( definition );

            final RectangleView view =
                    shapeViewFactory.rectangle( width, height );

            shape = new RectangleShape( view, rectangleProxy );

        }

        if ( isPolygon( proxy ) ) {

            final PolygonProxy<Object> polygonProxy = (PolygonProxy<Object>) proxy;

            final double radius = polygonProxy.getRadius( definition );
            final String fillColor = polygonProxy.getBackgroundColor( definition );

            final PolygonView view =
                    shapeViewFactory.polygon( radius,
                            fillColor );

            shape = new PolygonShape( view, polygonProxy );

        }

        if ( isConnector( proxy ) ) {

            final ConnectorProxy<Object> polygonProxy = (ConnectorProxy<Object>) proxy;

            final ConnectorView view = shapeViewFactory.connector( 0, 0, 100, 100 );

            shape = new ConnectorShape( view, polygonProxy );

        }

        if ( isStaticIcon( proxy ) ) {

            final org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy<Object> iconProxy =
                    ( org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy<Object> ) proxy;

            final org.kie.workbench.common.stunner.shapes.proxy.icon.statics.Icons icon = iconProxy.getIcon( definition );

            final StaticIconShapeView view =
                    shapeViewFactory.staticIcon( icon );

            shape = new StaticIconShape( view, iconProxy );

        }

        if ( isDynamicIcon( proxy ) ) {

            final IconProxy<Object> iconProxy =
                    (IconProxy<Object> ) proxy;

            final Icons icon = DynamicIconShape.getIcon( definition, iconProxy );
            final double width = iconProxy.getWidth( definition );
            final double height = iconProxy.getHeight( definition );

            final DynamicIconShapeView view =
                    shapeViewFactory.dynamicIcon( icon, width, height );

            shape = new DynamicIconShape( view, iconProxy );

        }
        
        // Add children, if any.
        if ( null != shape && proxy instanceof HasChildProxies) {

            final HasChildProxies<Object> hasChildren = (HasChildProxies<Object>) proxy;
            final Map<ShapeProxy<Object>, HasChildren.Layout> childProxies = hasChildren.getChildProxies();
            if ( null != childProxies && !childProxies.isEmpty() ) {
                for ( final Map.Entry<ShapeProxy<Object>, HasChildren.Layout> entry : childProxies.entrySet() ) {

                    final ShapeProxy<Object> child = entry.getKey();
                    final HasChildren.Layout layout = entry.getValue();

                    final MutableShape<Object, ShapeView> childShape = this.build( definition, child, context);

                    if ( childShape instanceof AbstractCompositeShape ) {

                        ( (AbstractCompositeShape) shape).addChild( (AbstractShape) childShape, layout );

                    }
                    
                }
            }

        }

        if ( null != shape ) {
        
            return shape;
            
        }
        
        final String id = definitionManager.adapters().forDefinition().getId( definition );
        throw new RuntimeException( "This factory supports [" + id + "] but cannot built a shape for it." );

    }
    
    private boolean isCircle( final ShapeProxy<Object> proxy ) {
        return proxy instanceof CircleProxy;
    }

    private boolean isRing( final ShapeProxy<Object> proxy ) {
        return proxy instanceof RingProxy;
    }

    private boolean isRectangle( final ShapeProxy<Object> proxy ) {
        return proxy instanceof RectangleProxy;
    }

    private boolean isPolygon( final ShapeProxy<Object> proxy ) {
        return proxy instanceof PolygonProxy;
    }

    private boolean isConnector( final ShapeProxy<Object> proxy ) {
        return proxy instanceof ConnectorProxy;
    }

    private boolean isDynamicIcon(final ShapeProxy<Object> proxy ) {
        return proxy instanceof IconProxy;
    }

    private boolean isStaticIcon(final ShapeProxy<Object> proxy ) {
        return proxy instanceof org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected ShapeGlyph glyph( final Class<?> clazz, 
                                final double width, 
                                final double height) {

        final String id = getDefinitionId( clazz );
        final BasicShapeProxy<Object> proxy = getProxy( id );

        // Custom shape glyphs.

        if ( null != proxy && isConnector( proxy ) ) {

            return new ConnectorGlyph( width, height, ColorName.BLACK.getColorString() );

        }

        // Use of Shape Glyph Builder - it builds the glyph by building the shape for the given Definition
        // and scaling it to the given size.

        if ( null != proxy ) {

            if ( glyphBuilder instanceof AbstractBindableShapeGlyphBuilder ) {

                final AbstractBindableShapeGlyphBuilder bindableShapeGlyphBuilder =
                        (AbstractBindableShapeGlyphBuilder) glyphBuilder;

                bindableShapeGlyphBuilder.glyphProxy( proxy, clazz );

            } else {

                glyphBuilder.glyphProxy( proxy, id );

            }

        } else {

            glyphBuilder.definition( id );

        }


        return glyphBuilder
                .factory( this )
                .height( height )
                .width( width )
                .build();
    }
    
}
