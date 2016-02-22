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

package org.wirez.core.client.canvas.wires;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.animation.ShapeSelectionAnimation;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.event.ShapeStateModifiedEvent;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.HasCanvasStateMutation;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.MouseClickEvent;
import org.wirez.core.client.view.event.MouseClickHandler;
import org.wirez.core.client.view.event.ViewEventType;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canvas impl based on Lienzo Wires.
 */
public abstract class WiresCanvas implements Canvas, SelectionManager<Shape> {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.canvas.wires.WiresCanvas");

    public interface View extends IsWidget {

        View init(Layer layer);
        
        View show(int width, int height, int padding);

        View add(IsWidget widget);

        View addShape(ShapeView<?> shapeView);
        
        View removeShape(ShapeView<?> shapeView);
        
        View setConnectionAcceptor(IConnectionAcceptor connectionAcceptor);
        
        View setContainmentAcceptor(IContainmentAcceptor containmentAcceptor);
        
        Layer getLayer();
        
        WiresManager getWiresManager();
        
        View clear();

    }
    
    public static final long ANIMATION_SELECTION_DURATION = 250;

    protected Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent;
    protected Layer layer;
    protected View view;
    protected List<Shape> shapes = new ArrayList<Shape>();
    protected List<Shape> selectedShapes = new ArrayList<Shape>();
    

    @Inject
    public WiresCanvas(final Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent,
                       final Layer layer,
                       final View view) {
        this.canvasShapeStateModifiedEvent = canvasShapeStateModifiedEvent;
        this.layer = layer;
        this.view = view;
    }

    public void init() {
        view.init(layer);
        layer.addHandler(ViewEventType.MOUSE_CLICK, new MouseClickHandler() {
            @Override
            public void handle(final MouseClickEvent event) {
                clearSelection();
                fireCanvasSelected();
            }
        });
    }

    @Override
    public List<Shape> getShapes() {
        return shapes;
    }

    public Shape getShape(final String id) {
        if ( null != shapes) {
            for (final Shape shape : shapes) {
                if (shape.getId().equals(id)) return shape;
            }
        }
        return null;
    }

    @Override
    public Canvas addShape(final Shape shape) {

        if (shape.getId() == null) {
            shape.setId(org.wirez.core.api.util.UUID.uuid());
        }

        log(Level.FINE, "BaseCanvas#register - " + shape.toString() + " [id=" + shape.getId() + "]");

        view.addShape( shape.getShapeView() );

        shapes.add( shape );

        return this;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public Canvas deleteShape(final Shape shape) {
        
        log(Level.FINE, "BaseCanvas#deregister - " + shape.toString() + " [id=" + shape.getId() + "]");
        
        view.removeShape( shape.getShapeView() );

        shapes.remove(shape);
        
        return this;
    }
    
    @Override
    public WiresCanvas draw() {
        view.getLayer().draw();
        return this;
    }

    public WiresCanvas clear() {

        if ( !shapes.isEmpty() ) {
            final List<Shape> shapesToRemove = new LinkedList<>(shapes);
            // Clear shapes.
            for (Shape shape : shapesToRemove) {
                deleteShape(shape);
            }

            // Clear state.
            shapes.clear();
            
        }

        if ( !selectedShapes.isEmpty() ) {
            selectedShapes.clear();
        }
        
        return this;
    }
    
    /*
        ******************************************
        *       Selection management
        ******************************************
     */

    @Override
    public SelectionManager<Shape> select(final Shape shape) {
        selectedShapes.add(shape);
        updateViewShapesState().draw();
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(this, shape, ShapeState.SELECTED));
        return this;
    }

    @Override
    public SelectionManager<Shape> deselect(final Shape shape) {
        selectedShapes.remove(shape);
        updateViewShapesState().draw();
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(this, shape, ShapeState.DESELECTED));
        return this;
    }

    @Override
    public boolean isSelected(final Shape shape) {
        return shape != null && selectedShapes.contains(shape);
    }

    @Override
    public Collection<Shape> getSelectedItems() {
        return Collections.unmodifiableCollection(selectedShapes);
    }

    @Override
    public SelectionManager<Shape> clearSelection() {
        for (final Shape shape : selectedShapes) {
            deselectShape(shape);
        }
        selectedShapes.clear();
        draw();
        return this;
    }

    protected void selectShape(final Shape shape) {
        
        if (shape instanceof HasCanvasStateMutation) {
            final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;
            canvasStateMutation.applyState(ShapeState.SELECTED);
        } else if (shape instanceof HasDecorators) {
            new ShapeSelectionAnimation(shape)
                    .setCanvas(WiresCanvas.this)
                    .setDuration(ANIMATION_SELECTION_DURATION)
                    .run();
        }
    }

    protected void deselectShape(final Shape shape) {
        final boolean isConnector = shape instanceof BaseConnector;

        if (shape instanceof HasCanvasStateMutation) {
            final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;
            canvasStateMutation.applyState(ShapeState.DESELECTED);
        } else if (shape instanceof HasDecorators) {
            new ShapeDeSelectionAnimation(shape, isConnector ? 1 : 0, isConnector ? 1 : 0, ColorName.BLACK)
                    .setCanvas(WiresCanvas.this)
                    .setDuration(ANIMATION_SELECTION_DURATION)
                    .run();
        }
    }

    protected WiresCanvas updateViewShapesState() {
        final List<Shape> shapes = getShapes();
        for (final Shape shape : shapes) {
            final boolean isSelected = !selectedShapes.isEmpty() && selectedShapes.contains(shape);
            if (isSelected) {
                selectShape(shape);
            } else {
                deselectShape(shape);
            }
        }
        return this;
    }
    
    protected void fireCanvasSelected() {
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(this, null, ShapeState.SELECTED));
    }

    /*
        ******************************************
        *       Other helper methods.
        ******************************************
     */

    public WiresManager getWiresManager() {
        return view.getWiresManager();
    }

    public View getView() {
        return view;
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
