package org.wirez.client.widgets.session.toolbar.command;

import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.session.CanvasReadOnlySession;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

public abstract class AbstractSelectionToolbarCommand<S extends CanvasReadOnlySession> 
        extends AbstractToolbarCommand<S> {

    @Inject
    public AbstractSelectionToolbarCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                                           final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public void initialize(S session) {
        super.initialize(session);
        checkState();
    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        checkState();
    }
    
    protected void checkState() {

        final boolean doDisable = null != session && null != session.getShapeSelectionControl() 
                    && session.getShapeSelectionControl().getSelectedItems().isEmpty();
        
        if ( doDisable ) {
            
            disable();
            
        } else  {
            
            enable();
            
        }

    }

    void onCanvasElementSelectedEvent( @Observes CanvasElementSelectedEvent event ) {
        checkNotNull( "event", event );
        checkState();
    }

    void CanvasClearSelectionEvent( @Observes CanvasElementSelectedEvent event ) {
        checkNotNull( "event", event );
        checkState();
    }
    


}
