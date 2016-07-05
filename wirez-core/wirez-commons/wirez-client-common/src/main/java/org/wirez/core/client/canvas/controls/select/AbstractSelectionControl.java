package org.wirez.core.client.canvas.controls.select;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerRegistrationControl;
import org.wirez.core.client.canvas.event.CanvasClearEvent;
import org.wirez.core.client.canvas.event.registration.CanvasShapeRemovedEvent;
import org.wirez.core.client.canvas.event.selection.CanvasClearSelectionEvent;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasCanvasState;
import org.wirez.core.client.shape.view.HasDecorators;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.MouseClickHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;
import org.wirez.core.graph.Element;

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

public abstract class AbstractSelectionControl extends AbstractCanvasHandlerRegistrationControl
        implements SelectionControl<AbstractCanvasHandler, Element> {

    private static Logger LOGGER = Logger.getLogger( AbstractSelectionControl.class.getName() );
    public static final long ANIMATION_SELECTION_DURATION = 250;

    Event<CanvasElementSelectedEvent> elementSelectedEventEvent;
    Event<CanvasClearSelectionEvent> clearSelectionEventEvent;
    ShapeAnimation selectionAnimation;
    ShapeDeSelectionAnimation deSelectionAnimation;

    protected final List<String> selectedElements = new ArrayList<String>();
    protected ViewHandler<?> layerClickHandler;

    @Inject
    public AbstractSelectionControl( final Event<CanvasElementSelectedEvent> elementSelectedEventEvent,
                                     final Event<CanvasClearSelectionEvent> clearSelectionEventEvent,
                                     final @Select ShapeAnimation selectionAnimation,
                                     final @Deselect ShapeDeSelectionAnimation deSelectionAnimation ) {
        this.elementSelectedEventEvent = elementSelectedEventEvent;
        this.clearSelectionEventEvent = clearSelectionEventEvent;
        this.selectionAnimation = selectionAnimation;
        this.deSelectionAnimation = deSelectionAnimation;
    }

    /*
        **************************************************************
        *               CANVAS CONTROL METHODS
        ***************************************************************
     */

    @Override
    public void enable( final AbstractCanvasHandler canvasHandler ) {
        super.enable( canvasHandler );

        final Layer layer = canvasHandler.getCanvas().getLayer();

        // Click event.
        final MouseClickHandler clickHandler = new MouseClickHandler() {

            @Override
            public void handle( final MouseClickEvent event ) {

                handleLayerClick( !event.isShiftKeyDown() );

            }

        };

        layer.addHandler( ViewEventType.MOUSE_CLICK, clickHandler );

        this.layerClickHandler = clickHandler;

    }

    protected abstract void register( Element element, Shape<?> shape );

    @Override
    public void register( final Element element ) {

        final Shape<?> shape = getCanvas().getShape( element.getUUID() );

        if ( null != shape ) {

            register( element, shape );

        }

    }

    protected void handleElementSelection( final Element element,
                                           final boolean selected,
                                           final boolean clearSelection ) {

        if ( clearSelection ) {
            clearSelection();
        }

        if ( selected ) {

            log( Level.FINE, "Deselect [element=" + element.getUUID() + "]" );
            deselect( element );

        } else {

            log( Level.FINE, "Select [element=" + element.getUUID() + "]" );
            select( element );

        }

    }

    protected void handleLayerClick( final boolean clearSelection ) {

        if ( clearSelection ) {
            clearSelection();
        }

        final String canvasRootUUID = canvasHandler.getDiagram().getSettings().getCanvasRootUUID();

        if ( null != canvasRootUUID ) {

            elementSelectedEventEvent.fire( new CanvasElementSelectedEvent( canvasHandler, canvasRootUUID ) );

        } else {

            clearSelectionEventEvent.fire( new CanvasClearSelectionEvent( canvasHandler ) );

        }

    }

    @Override
    public void deregister( Element element ) {
        super.deregister( element );
        selectedElements.remove( element.getUUID() );
    }

    @Override
    protected void doDisable() {

        super.doDisable();

        if ( null != layerClickHandler ) {

            this.getCanvas().getLayer().removeHandler( layerClickHandler );
            this.layerClickHandler = null;
        }

        this.selectedElements.clear();

    }

    @SuppressWarnings( "unchecked" )
    protected void updateViewShapesState() {

        if ( null != getCanvas() ) {

            final List<Shape> shapes = getCanvas().getShapes();

            for ( final Shape shape : shapes ) {
                final boolean isSelected = !selectedElements.isEmpty() && selectedElements.contains( shape.getUUID() );

                if ( isSelected ) {

                    selectShape( shape );

                } else {

                    deselectShape( shape );

                }

            }

            // Batch a show operation.
            getCanvas().draw();

        }

    }

    protected void selectShape( final Shape shape ) {

        if ( shape.getShapeView() instanceof HasCanvasState ) {

            final HasCanvasState canvasStateMutation = ( HasCanvasState ) shape.getShapeView();
            canvasStateMutation.applyState( ShapeState.SELECTED );

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            selectionAnimation.forShape( shape )
                    .forCanvas( getCanvas() )
                    .setDuration( ANIMATION_SELECTION_DURATION )
                    .run();
        }

    }

    protected void deselectShape( final Shape shape ) {

        final boolean isConnector = shape instanceof EdgeShape;

        if ( shape.getShapeView() instanceof HasCanvasState ) {

            final HasCanvasState canvasStateMutation = ( HasCanvasState ) shape.getShapeView();
            canvasStateMutation.applyState( ShapeState.DESELECTED );

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            deSelectionAnimation.setStrokeWidth( isConnector ? 1 : 0 )
                    .setStrokeAlpha( isConnector ? 1 : 0 )
                    .setColor( "#000000" )
                    .forShape( shape )
                    .forCanvas( getCanvas() )
                    .setDuration( ANIMATION_SELECTION_DURATION )
                    .run();

        }

    }
    
    /*
        **************************************************************
        *               SELECTION CONTROL METHODS
        ***************************************************************
     */

    public SelectionControl<AbstractCanvasHandler, Element> select( final String uuid,
                                                                    final boolean fireEvent ) {
        final Element element = canvasHandler.getGraphIndex().get( uuid );
        return this.select( element, fireEvent );
    }

    @Override
    public SelectionControl<AbstractCanvasHandler, Element> select( final Element element ) {
        return select( element, true );
    }

    public SelectionControl<AbstractCanvasHandler, Element> select( final Element element,
                                                                    final boolean fireEvent ) {

        selectedElements.add( element.getUUID() );

        updateViewShapesState();

        if ( fireEvent ) {

            elementSelectedEventEvent.fire( new CanvasElementSelectedEvent( canvasHandler, element.getUUID() ) );

        }

        return this;
    }

    public SelectionControl<AbstractCanvasHandler, Element> deselect( final String uuid,
                                                                      final boolean fireEvent ) {
        final Element element = canvasHandler.getGraphIndex().get( uuid );
        return this.deselect( element, fireEvent );
    }

    @Override
    public SelectionControl<AbstractCanvasHandler, Element> deselect( final Element element ) {
        return deselect( element, true );
    }

    public SelectionControl<AbstractCanvasHandler, Element> deselect( final Element element,
                                                                      final boolean fireEvent ) {

        selectedElements.remove( element.getUUID() );

        updateViewShapesState();

        if ( fireEvent ) {

            fireCanvasClear();

        }

        return this;
    }

    protected boolean isSelected( final String uuid ) {
        return uuid != null && selectedElements.contains( uuid );
    }

    @Override
    public boolean isSelected( final Element element ) {
        return null != element && isSelected( element.getUUID() );
    }

    @Override
    public Collection<String> getSelectedItems() {
        return Collections.unmodifiableCollection( selectedElements );
    }

    @Override
    public SelectionControl<AbstractCanvasHandler, Element> clearSelection() {
        return clearSelection( true );
    }

    public SelectionControl<AbstractCanvasHandler, Element> clearSelection( final boolean fireEvent ) {

        // De-select all currently selected shapes.
        for ( final String uuid : selectedElements ) {

            final Shape<?> shape = canvasHandler.getCanvas().getShape( uuid );

            if ( null != shape ) {

                deselectShape( shape );

            }

        }
        selectedElements.clear();

        if ( null != getCanvas() ) {

            // Force batch re-show.
            getCanvas().draw();

        }

        if ( fireEvent ) {

            fireCanvasClear();

        }

        return this;
    }

    void onShapeRemovedEvent( @Observes CanvasShapeRemovedEvent shapeRemovedEvent ) {
        checkNotNull( "shapeRemovedEvent", shapeRemovedEvent );

        if ( null != getCanvas() && getCanvas().equals( shapeRemovedEvent.getCanvas() ) ) {
            final Shape<?> shape = shapeRemovedEvent.getShape();

            if ( selectedElements.contains( shape.getUUID() ) ) {
                this.deselect( shape.getUUID(), false );
            }

        }

    }


    void onCanvasElementSelectedEvent( @Observes CanvasElementSelectedEvent event ) {
        checkNotNull( "event", event );

        final String uuid = event.getElementUUID();

        if ( null != canvasHandler && canvasHandler.equals( event.getCanvasHandler() ) &&
                !isSelected( uuid ) ) {

            this.clearSelection( false );

            this.select( uuid, false );

        }
    }

    void CanvasClearSelectionEvent( @Observes CanvasClearSelectionEvent event ) {
        checkNotNull( "event", event );

        if ( null != canvasHandler && canvasHandler.equals( event.getCanvasHandler() ) ) {

            this.clearSelection( false );

        }

    }

    protected void fireCanvasClear() {
        clearSelectionEventEvent.fire( new CanvasClearSelectionEvent( canvasHandler ) );
    }

    protected Canvas getCanvas() {
        return null != canvasHandler ? canvasHandler.getCanvas() : null;
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
