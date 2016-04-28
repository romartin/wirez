package org.wirez.core.client.canvas.controls.select;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.animation.ShapeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.controls.AbstractCanvasRegistrationControl;
import org.wirez.core.client.canvas.controls.event.ClearSelectionEvent;
import org.wirez.core.client.canvas.controls.event.DeselectShapeEvent;
import org.wirez.core.client.canvas.controls.event.SelectShapeEvent;
import org.wirez.core.client.canvas.controls.event.SelectSingleShapeEvent;
import org.wirez.core.client.canvas.event.CanvasShapeRemovedEvent;
import org.wirez.core.client.canvas.event.ShapeStateModifiedEvent;
import org.wirez.core.client.shape.HasDecorators;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.impl.AbstractConnector;
import org.wirez.core.client.shape.view.HasCanvasState;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.MouseClickHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public final class SelectionControlImpl extends AbstractCanvasRegistrationControl
    implements SelectionControl<AbstractCanvas, Shape> {

    private static Logger LOGGER = Logger.getLogger(SelectionControlImpl.class.getName());
    public static final long ANIMATION_SELECTION_DURATION = 250;

    Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent;
    private final List<Shape> selectedShapes = new ArrayList<Shape>();

    @Inject
    public SelectionControlImpl(final Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent) {
        this.canvasShapeStateModifiedEvent = canvasShapeStateModifiedEvent;
    }

    /*
        **************************************************************
        *               CANVAS CONTROL METHODS
        ***************************************************************
     */

    @Override
    public void enable(AbstractCanvas canvas) {
        super.enable(canvas);
        
        // TODO: fireNoShapeSelected on click on canvas.
    }

    @Override
    public void register(final Shape shape) {

        // Selection handling.
        final ShapeView shapeView = shape.getShapeView();
        if ( shapeView instanceof HasEventHandlers ) {

            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shapeView;

            hasEventHandlers.addHandler(ViewEventType.MOUSE_CLICK, new MouseClickHandler() {
                @Override
                public void handle(final MouseClickEvent event) {
                    final boolean isSelected = isSelected(shape);

                    if (!event.isShiftKeyDown()) {
                        clearSelection();
                    }

                    if (isSelected) {
                        log(Level.FINE, "Deselect [shape=" + shape.getUUID() + "]");
                        deselect(shape);
                    } else {
                        log(Level.FINE, "Select [shape=" + shape.getUUID() + "]");
                        select(shape);
                    }
                }
            });

        }
        
    }

    @Override
    public void deregister(final Shape shape) {
        // TODO: Remove registered handlers.
    }

    @Override
    public void disable() {

    }

    protected void updateViewShapesState() {
        final List<Shape> shapes = canvas.getShapes();
        for (final Shape shape : shapes) {
            final boolean isSelected = !selectedShapes.isEmpty() && selectedShapes.contains(shape);
            if (isSelected) {
                selectShape(shape);
            } else {
                deselectShape(shape);
            }
        }
        
        // Batch a draw operation.
        canvas.draw();
    }

    protected void selectShape(final Shape shape) {

        if (shape.getShapeView() instanceof HasCanvasState) {
            final HasCanvasState canvasStateMutation = (HasCanvasState) shape.getShapeView();
            canvasStateMutation.applyState(ShapeState.SELECTED);
        } else if (shape.getShapeView() instanceof HasDecorators) {
            new ShapeSelectionAnimation(shape)
                    .setCanvas(canvas)
                    .setDuration(ANIMATION_SELECTION_DURATION)
                    .run();
        }
    }

    protected void deselectShape(final Shape shape) {
        
        final boolean isConnector = shape instanceof AbstractConnector;

        if (shape.getShapeView() instanceof HasCanvasState) {
            final HasCanvasState canvasStateMutation = (HasCanvasState) shape.getShapeView();
            canvasStateMutation.applyState(ShapeState.DESELECTED);
        } else if (shape.getShapeView() instanceof HasDecorators) {
            new ShapeDeSelectionAnimation(shape, isConnector ? 1 : 0, isConnector ? 1 : 0, ColorName.BLACK)
                    .setCanvas(canvas)
                    .setDuration(ANIMATION_SELECTION_DURATION)
                    .run();
        }
    }
    
    /*
        **************************************************************
        *               SELECTION CONTROL METHODS
        ***************************************************************
     */

    
    @Override
    public SelectionControl<AbstractCanvas, Shape> select(final Shape shape) {
        selectedShapes.add(shape);
        updateViewShapesState(); 
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(canvas, shape, ShapeState.SELECTED));
        return null;
    }

    @Override
    public SelectionControl<AbstractCanvas, Shape> deselect(final Shape shape) {
        selectedShapes.remove(shape);
        updateViewShapesState();
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(canvas, shape, ShapeState.DESELECTED));
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
    public SelectionControl<AbstractCanvas, Shape> clearSelection() {
        
        // De-select all currently selected shapes.
        for (final Shape shape : selectedShapes) {
            deselectShape(shape);
        }
        selectedShapes.clear();
        
        // Force batch re-draw.
        canvas.draw();
        
        // Fire the event.
        fireNoShapeSelected();
        
        return this;
    }
    
    void onShapeRemovedEvent(@Observes CanvasShapeRemovedEvent shapeRemovedEvent) {
        checkNotNull("shapeRemovedEvent", shapeRemovedEvent);

        if ( canvas.equals( shapeRemovedEvent.getCanvas() ) ) {
            final Shape<?> shape = shapeRemovedEvent.getShape();
            
            if ( selectedShapes.contains( shape ) ) {
                this.deselect( shape );
            }
            
        }
        
    }
    
    void onSingleShapeSelectedEvent(@Observes SelectSingleShapeEvent selectShapeEvent) {
        checkNotNull("selectShapeEvent", selectShapeEvent);
        
        if ( canvas.equals( selectShapeEvent.getCanvas() )) {
            this.clearSelection();
            this.select( selectShapeEvent.getShape() );
        }
        
    }

    void onShapeSelectedEvent(@Observes SelectShapeEvent selectShapeEvent) {
        checkNotNull("selectShapeEvent", selectShapeEvent);

        if ( canvas.equals( selectShapeEvent.getCanvas() )) {
            this.select( selectShapeEvent.getShape() );
        }

    }

    void onShapeDeselectedEvent(@Observes DeselectShapeEvent deselectShapeEvent) {
        checkNotNull("deselectShapeEvent", deselectShapeEvent);

        if ( canvas.equals( deselectShapeEvent.getCanvas() )) {
            this.deselect( deselectShapeEvent.getShape() );
        }

    }

    void onClearSelectionEvent(@Observes ClearSelectionEvent clearSelectionEvent) {
        checkNotNull("clearSelectionEvent", clearSelectionEvent);

        if ( canvas.equals( clearSelectionEvent.getCanvas() )) {
            this.clearSelection();
        }

    }

    protected void fireNoShapeSelected() {
        canvasShapeStateModifiedEvent.fire(new ShapeStateModifiedEvent(canvas, null, ShapeState.SELECTED));
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
