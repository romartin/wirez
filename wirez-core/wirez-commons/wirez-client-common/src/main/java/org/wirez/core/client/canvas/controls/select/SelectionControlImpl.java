package org.wirez.core.client.canvas.controls.select;

import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.api.platform.Desktop;
import org.wirez.core.client.canvas.event.ShapeStateModifiedEvent;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.MouseClickHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
@Desktop
public final class SelectionControlImpl extends AbstractSelectionControl {

    @Inject
    public SelectionControlImpl( final Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent,
                                 final @Select  ShapeAnimation selectionAnimation,
                                 final @Deselect ShapeDeSelectionAnimation deSelectionAnimation) {
        super( canvasShapeStateModifiedEvent, selectionAnimation, deSelectionAnimation );
    }

    /*
        **************************************************************
        *               CANVAS CONTROL METHODS
        ***************************************************************
     */

    @Override
    public void register(final Shape shape) {

        // Selection handling.
        final ShapeView shapeView = shape.getShapeView();
        
        if ( shapeView instanceof HasEventHandlers ) {

            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shapeView;

            // Click event.
            final MouseClickHandler clickHandler = new MouseClickHandler() {
                
                @Override
                public void handle(final MouseClickEvent event) {
                    
                    final boolean isSelected = isSelected(shape);
                    
                    SelectionControlImpl.super.handleSelection( shape, isSelected, !event.isShiftKeyDown() );
                    
                }
                
            };
            
            hasEventHandlers.addHandler( ViewEventType.MOUSE_CLICK, clickHandler );

            registerHandler( shape.getUUID(), clickHandler );
        }
        
    }
    
}
