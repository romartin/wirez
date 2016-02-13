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

package org.wirez.core.client.canvas.impl;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.animation.ShapeSelectionAnimation;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.event.ShapeStateModifiedEvent;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.HasCanvasStateMutation;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;

public abstract class BaseCanvas implements Canvas, SelectionManager<Shape> {

    public static final long ANIMATION_SELECTION_DURATION = 250;
    
    Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent;
    protected WiresManager wiresManager;
    protected List<Shape> shapes = new ArrayList<Shape>();
    protected List<Shape> selectedShapes = new ArrayList<Shape>();
    protected Layer layer;

    @Inject
    public BaseCanvas(final Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent) {
        this.canvasShapeStateModifiedEvent = canvasShapeStateModifiedEvent;
    }

    /*
        ******************************************
        *       Canvas impl methods
        ******************************************
     */
    
    @Override
    public BaseCanvas initialize(final Layer lienzoLayer) {
        this.layer = lienzoLayer;
        wiresManager = WiresManager.get(lienzoLayer);
        lienzoLayer.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                clearSelection();
                fireCanvasSelected();
            }
        });
        return this;
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
        if (shape instanceof BaseShape) {
            registerShape((BaseShape) shape);
        } else {
            registerConnector((BaseConnector) shape);
        }
        return this;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public Canvas deleteShape(final Shape shape) {
        if (shape instanceof BaseShape) {
            deregisterShape((BaseShape) shape);
        } else {
            deregisterConnector((BaseConnector) shape);
        }
        return this;
    }
    
    @Override
    public BaseCanvas draw() {
        wiresManager.getLayer().getLayer().batch();
        return this;
    }

    public BaseCanvas clear() {

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
        *       Shapes register/de-register
        ******************************************
     */
    
    protected void registerShape(final BaseShape shape) {
        assert wiresManager != null;
        if (shape.getId() == null) {
            shape.setId(org.wirez.core.api.util.UUID.uuid());
        }
        GWT.log("BaseCanvas#registerShape - " + shape.toString() + " [id=" + shape.getId() + "]");
        wiresManager.createMagnets(shape);
        wiresManager.registerShape(shape);
        shapes.add(shape);
    }

    protected void deregisterShape(final BaseShape shape) {
        assert wiresManager != null;
        GWT.log("BaseCanvas#DE-registerShape - " + shape.toString() + " [id=" + shape.getId() + "]");
        wiresManager.deregisterShape(shape);
        shapes.remove(shape);
    }

    protected void registerConnector(final BaseConnector connector) {
        assert wiresManager != null;
        if (connector.getId() == null) {
            connector.setId(UUID.uuid());
        }
        GWT.log("BaseCanvas#registerConnector - " + connector.toString() + " [id=" + connector.getId() + "]");
        wiresManager.registerConnector(connector);
        shapes.add(connector);
    }

    protected void deregisterConnector(final BaseConnector connector) {
        assert wiresManager != null;
        GWT.log("BaseCanvas#DE-registerConnector - " + connector.toString() + " [id=" + connector.getId() + "]");
        wiresManager.deregisterConnector(connector);
        shapes.remove(connector);
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
                    .setCanvas(BaseCanvas.this)
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
                    .setCanvas(BaseCanvas.this)
                    .setDuration(ANIMATION_SELECTION_DURATION)
                    .run();
        }
    }

    protected BaseCanvas updateViewShapesState() {
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
        return wiresManager;
    }

}
