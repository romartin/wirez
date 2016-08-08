package org.wirez.client.widgets.session.toolbar.command;

import org.wirez.client.widgets.session.toolbar.Toolbar;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.session.CanvasReadOnlySession;

import javax.enterprise.event.Observes;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

public abstract class AbstractSelectionToolbarCommand<S extends CanvasReadOnlySession> 
        extends AbstractToolbarCommand<S> {

    protected boolean getState() {

        final boolean doDisable = null != session && null != session.getShapeSelectionControl() 
                    && session.getShapeSelectionControl().getSelectedItems().isEmpty();
        
        return !doDisable;

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
