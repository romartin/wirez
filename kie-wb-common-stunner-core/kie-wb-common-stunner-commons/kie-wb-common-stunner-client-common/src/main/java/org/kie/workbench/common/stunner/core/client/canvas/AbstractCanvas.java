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

package org.kie.workbench.common.stunner.core.client.canvas;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasClearEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasDrawnEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasFocusedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasShapeAddedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasShapeRemovedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.listener.CanvasShapeListener;
import org.kie.workbench.common.stunner.core.client.canvas.listener.HasCanvasListeners;
import org.kie.workbench.common.stunner.core.client.canvas.util.CanvasLoadingObserver;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.core.client.shape.view.event.MouseClickEvent;
import org.kie.workbench.common.stunner.core.client.shape.view.event.MouseClickHandler;
import org.kie.workbench.common.stunner.core.util.UUID;

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
public abstract class AbstractCanvas<V extends AbstractCanvas.View>
        implements Canvas<Shape>, HasCanvasListeners<CanvasShapeListener> {

    private static Logger LOGGER = Logger.getLogger( AbstractCanvas.class.getName() );

    public interface View<P> extends IsWidget {

        View show( P panel, Layer layer );

        View add( IsWidget widget );

        View remove( IsWidget widget );

        View addShape( ShapeView<?> shapeView );

        View removeShape( ShapeView<?> shapeView );

        View addChildShape( ShapeView<?> parent, ShapeView<?> child );

        View removeChildShape( ShapeView<?> parent, ShapeView<?> child );

        View dock( ShapeView<?> parent, ShapeView<?> child );

        View undock( ShapeView<?> parent, ShapeView<?> child );

        double getAbsoluteX();

        double getAbsoluteY();

        int getWidth();

        int getHeight();

        View setGrid( CanvasGrid grid );

        Layer getLayer();

        View clear();

        void destroy();

    }

    protected Layer layer;
    protected V view;
    protected Event<CanvasClearEvent> canvasClearEvent;
    protected Event<CanvasShapeAddedEvent> canvasShapeAddedEvent;
    protected Event<CanvasShapeRemovedEvent> canvasShapeRemovedEvent;
    protected Event<CanvasDrawnEvent> canvasDrawnEvent;
    protected Event<CanvasFocusedEvent> canvasFocusedEvent;

    protected final List<Shape> shapes = new ArrayList<Shape>();
    protected final List<CanvasShapeListener> listeners = new LinkedList<>();
    private final CanvasLoadingObserver loadingObserver = new CanvasLoadingObserver();
    private final String uuid;

    @Inject
    public AbstractCanvas( final Event<CanvasClearEvent> canvasClearEvent,
                           final Event<CanvasShapeAddedEvent> canvasShapeAddedEvent,
                           final Event<CanvasShapeRemovedEvent> canvasShapeRemovedEvent,
                           final Event<CanvasDrawnEvent> canvasDrawnEvent,
                           final Event<CanvasFocusedEvent> canvasFocusedEvent,
                           final Layer layer,
                           final V view ) {
        this.canvasClearEvent = canvasClearEvent;
        this.canvasShapeAddedEvent = canvasShapeAddedEvent;
        this.canvasShapeRemovedEvent = canvasShapeRemovedEvent;
        this.canvasDrawnEvent = canvasDrawnEvent;
        this.canvasFocusedEvent = canvasFocusedEvent;
        this.layer = layer;
        this.view = view;
        this.uuid = UUID.uuid();
    }

    @SuppressWarnings( "unchecked" )
    public <P> void show( P panel, Layer layer ) {
        view.show( panel, layer );

        // Click event.
        final MouseClickHandler clickHandler = new MouseClickHandler() {

            @Override
            public void handle( final MouseClickEvent event ) {

                canvasFocusedEvent.fire( new CanvasFocusedEvent( AbstractCanvas.this ) );

            }

        };

        // TODO: layer.addHandler( ViewEventType.MOUSE_CLICK, clickHandler );
        //       If adding this handler, the SelectionControl for this layer never fires,
        //       so it seems it's not registering fine more than one click event handler.

    }

    public abstract void addControl( IsWidget controlView );

    public abstract void deleteControl( IsWidget controlView );

    @Override
    public List<Shape> getShapes() {
        return shapes;
    }

    public Shape getShape( final String uuid ) {
        if ( null != shapes ) {
            for ( final Shape shape : shapes ) {
                if ( shape.getUUID().equals( uuid ) ) return shape;
            }
        }
        return null;
    }

    public Canvas addChildShape( final Shape parent, final Shape child ) {
        getView().addChildShape( parent.getShapeView(), child.getShapeView() );
        log( Level.FINE, "Adding child [" + child.getUUID() + "] into parent [" + parent.getUUID() + "]" );
        return this;
    }

    public Canvas deleteChildShape( final Shape parent, final Shape child ) {
        getView().removeChildShape( parent.getShapeView(), child.getShapeView() );
        log( Level.FINE, "Deleting child [" + child.getUUID() + "] from parent [" + parent.getUUID() + "]" );
        return this;
    }

    public Canvas dock( final Shape parent, final Shape child ) {
        getView().dock( parent.getShapeView(), child.getShapeView() );
        log( Level.FINE, "Docking child [" + child.getUUID() + "] into parent [" + parent.getUUID() + "]" );
        return this;
    }

    public Canvas undock( final Shape parent, final Shape child ) {
        getView().undock( parent.getShapeView(), child.getShapeView() );
        log( Level.FINE, "Undocking child [" + child.getUUID() + "] from parent [" + parent.getUUID() + "]" );
        return this;
    }

    @Override
    public Canvas addShape( final Shape shape ) {

        addTransientShape( shape );

        shapes.add( shape );

        fireCanvasShapeAdded( shape );

        canvasShapeAddedEvent.fire( new CanvasShapeAddedEvent( this, shape ) );

        return this;
    }

    public Canvas addTransientShape( final Shape shape ) {

        if ( shape.getUUID() == null ) {
            shape.setUUID( UUID.uuid() );
        }

        shape.getShapeView().setUUID( shape.getUUID() );
        view.addShape( shape.getShapeView() );

        return this;
    }

    public double getAbsoluteX() {
        return view.getAbsoluteX();
    }

    public double getAbsoluteY() {
        return view.getAbsoluteY();
    }

    @Override
    public Canvas setGrid( final CanvasGrid grid ) {
        view.setGrid( grid );
        return this;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public Canvas deleteShape( final Shape shape ) {

        deleteTransientShape( shape );

        fireCanvasShapeRemoved( shape );

        shapes.remove( shape );

        canvasShapeRemovedEvent.fire( new CanvasShapeRemovedEvent( this, shape ) );

        return this;
    }

    public Canvas deleteTransientShape( final Shape shape ) {

        view.removeShape( shape.getShapeView() );

        shape.destroy();

        return this;
    }

    @Override
    public AbstractCanvas draw() {
        applyShapesDraw();
        view.getLayer().draw();
        return this;
    }


    @Override
    public void destroy() {

        if ( !shapes.isEmpty() ) {

            for ( final Shape shape : shapes ) {

                shape.destroy();

            }

            shapes.clear();
        }

        listeners.clear();

        view.destroy();
        layer.destroy();

        layer = null;
        view = null;
    }

    @Override
    public HasCanvasListeners<CanvasShapeListener> addRegistrationListener( final CanvasShapeListener instance ) {
        listeners.add( instance );
        return this;
    }

    @Override
    public HasCanvasListeners<CanvasShapeListener> removeRegistrationListener( final CanvasShapeListener instance ) {
        listeners.remove( instance );
        return this;
    }

    @Override
    public HasCanvasListeners<CanvasShapeListener> clearRegistrationListeners() {
        listeners.clear();
        return this;
    }

    protected void fireCanvasShapeAdded( final Shape shape ) {

        for ( final CanvasShapeListener instance : listeners ) {

            instance.register( shape );

        }

    }

    protected void fireCanvasShapeRemoved( final Shape shape ) {

        for ( final CanvasShapeListener instance : listeners ) {

            instance.deregister( shape );

        }

    }

    protected void fireCanvasClear() {

        for ( final CanvasShapeListener instance : listeners ) {

            instance.clear();

        }

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

    protected void afterDrawCanvas() {

        canvasDrawnEvent.fire( new CanvasDrawnEvent( this ) );
    }

    public AbstractCanvas clear() {

        if ( !shapes.isEmpty() ) {
            final List<Shape> shapesToRemove = new LinkedList<>( shapes );
            // Clear shapes.
            for ( Shape shape : shapesToRemove ) {
                deleteShape( shape );
            }

            // Clear state.
            shapes.clear();

        }

        fireCanvasClear();

        canvasClearEvent.fire( new CanvasClearEvent( this ) );

        return this;
    }

    @Override
    public int getWidth() {
        return view.getWidth();
    }

    @Override
    public int getHeight() {
        return view.getHeight();
    }

    public V getView() {
        return view;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractCanvas ) ) {
            return false;
        }

        AbstractCanvas that = ( AbstractCanvas ) o;

        return uuid.equals( that.uuid );

    }

    public void setLoadingObserverCallback( final CanvasLoadingObserver.Callback loadingObserverCallback ) {
        this.loadingObserver.setLoadingObserverCallback( loadingObserverCallback );
    }

    public void loadingStarted() {
        this.loadingObserver.loadingStarted();
    }

    public void loadingCompleted() {
        this.loadingObserver.loadingCompleted();
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
