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

package org.wirez.core.client.canvas;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.client.canvas.event.CanvasClearEvent;
import org.wirez.core.client.canvas.event.CanvasDrawnEvent;
import org.wirez.core.client.canvas.event.CanvasShapeAddedEvent;
import org.wirez.core.client.canvas.event.CanvasShapeRemovedEvent;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * For Lienzo's based Canvas.
 */
public abstract class AbstractCanvas<V extends AbstractCanvas.View> implements Canvas<Shape> {

    private static Logger LOGGER = Logger.getLogger(AbstractCanvas.class.getName());

    public interface View extends IsWidget {

        View init(Layer layer);
        
        View show(int width, int height, int padding);

        View add(IsWidget widget);

        View remove(IsWidget widget);

        View addShape(ShapeView<?> shapeView);
        
        View removeShape(ShapeView<?> shapeView);
        
        View addChildShape(ShapeView<?> parent, ShapeView<?> child);

        View removeChildShape(ShapeView<?> parent, ShapeView<?> child);
        
        double getAbsoluteX();

        double getAbsoluteY();
        
        Layer getLayer();
        
        View clear();

    }
    

    protected Layer layer;
    protected V view;
    protected Event<CanvasClearEvent> canvasClearEvent;
    protected Event<CanvasShapeAddedEvent> canvasShapeAddedEvent;
    protected Event<CanvasShapeRemovedEvent> canvasShapeRemovedEvent;
    protected Event<CanvasDrawnEvent> canvasDrawnEvent;
    
    protected List<Shape> shapes = new ArrayList<Shape>();
    private final String uuid;
    
    @Inject
    public AbstractCanvas(final Event<CanvasClearEvent> canvasClearEvent,
                          final Event<CanvasShapeAddedEvent> canvasShapeAddedEvent,
                          final Event<CanvasShapeRemovedEvent> canvasShapeRemovedEvent,
                          final Event<CanvasDrawnEvent> canvasDrawnEvent,
                          final Layer layer,
                          final V view) {
        this.canvasClearEvent = canvasClearEvent;
        this.canvasShapeAddedEvent = canvasShapeAddedEvent;
        this.canvasShapeRemovedEvent = canvasShapeRemovedEvent;
        this.canvasDrawnEvent = canvasDrawnEvent;
        this.layer = layer;
        this.view = view;
        this.uuid = org.wirez.core.api.util.UUID.uuid();
    }

    public void init() {
        view.init(layer);
        layer.onBeforeDraw(() -> AbstractCanvas.this.beforeDrawCanvas());
        layer.onAfterDraw(() -> AbstractCanvas.this.afterDrawCanvas());
    }

    public abstract void addControl( IsWidget controlView );

    public abstract void deleteControl( IsWidget controlView );
    
    @Override
    public List<Shape> getShapes() {
        return shapes;
    }

    public Shape getShape(final String uuid) {
        if ( null != shapes) {
            for (final Shape shape : shapes) {
                if (shape.getUUID().equals(uuid)) return shape;
            }
        }
        return null;
    }

    public Canvas addChildShape(final Shape parent, final Shape child) {
        getView().addChildShape(parent.getShapeView(), child.getShapeView());
        log(Level.FINE, "Adding child [" + child.getUUID() + "] into parent [" + parent.getUUID() + "]");
        return this;
    }

    public Canvas deleteChildShape(final Shape parent, final Shape child) {
        getView().removeChildShape(parent.getShapeView(), child.getShapeView());
        log(Level.FINE, "Deleting child [" + child.getUUID() + "] from parent [" + parent.getUUID()  + "]");
        return this;
    }
    
    @Override
    public Canvas addShape(final Shape shape) {

        addTransientShape( shape );

        shapes.add(shape);
        
        canvasShapeAddedEvent.fire(new CanvasShapeAddedEvent(this, shape));

        return this;
    }
    
    public Canvas addTransientShape( final Shape shape ) {
        
        if (shape.getUUID() == null) {
            shape.setUUID(org.wirez.core.api.util.UUID.uuid());
        }

        shape.getShapeView().setUUID(shape.getUUID());
        view.addShape(shape.getShapeView());
        
        return this;
    }

    public double getAbsoluteX() {
        return view.getAbsoluteX();
    }

    public double getAbsoluteY() {
        return view.getAbsoluteY();
    }
    
    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public Canvas deleteShape(final Shape shape) {
        
        deleteTransientShape( shape );

        shapes.remove(shape);
        
        canvasShapeRemovedEvent.fire(new CanvasShapeRemovedEvent( this, shape) );
        
        return this;
    }

    public Canvas deleteTransientShape(final Shape shape) {

        view.removeShape( shape.getShapeView() );

        return this;
    }
    
    @Override
    public AbstractCanvas draw() {
        applyShapesDraw();
        view.getLayer().draw();
        return this;
    }

    protected void applyShapesDraw() {
        
        for ( final Shape shape : shapes ) {
            
            // Zindex.
            applyShapeZIndex( shape );
            
        }
        
    }

    protected void applyShapeZIndex( final Shape shape ) {
        final int order = shape.getShapeView().getZIndex();
        shape.getShapeView().moveToBottom();
        for ( int x = 0; x < order; x++ ) {
            shape.getShapeView().moveUp();
        }
    }
    
    protected void beforeDrawCanvas() {
        
    }

    protected void afterDrawCanvas() {

        canvasDrawnEvent.fire( new CanvasDrawnEvent( this ) );
    }

    public AbstractCanvas clear() {

        if ( !shapes.isEmpty() ) {
            final List<Shape> shapesToRemove = new LinkedList<>(shapes);
            // Clear shapes.
            for (Shape shape : shapesToRemove) {
                deleteShape(shape);
            }

            // Clear state.
            shapes.clear();
            
        }
        
        canvasClearEvent.fire( new CanvasClearEvent( this ) );
        
        return this;
    }
    
    public V getView() {
        return view;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractCanvas) ) {
            return false;
        }

        AbstractCanvas that = (AbstractCanvas) o;

        return uuid.equals(that.uuid);

    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
