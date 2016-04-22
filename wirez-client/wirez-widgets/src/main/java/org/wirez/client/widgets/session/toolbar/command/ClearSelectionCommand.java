package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.uberfire.ext.widgets.common.client.common.popups.YesNoCancelPopup;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.controls.event.ClearSelectionEvent;
import org.wirez.core.client.canvas.controls.event.DeselectShapeEvent;
import org.wirez.core.client.canvas.controls.event.SelectShapeEvent;
import org.wirez.core.client.canvas.controls.event.SelectSingleShapeEvent;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class ClearSelectionCommand extends AbstractToolbarCommand<DefaultCanvasReadOnlySession> {

    private DefaultCanvasReadOnlySession session;
    
    @Inject
    public ClearSelectionCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                                 final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public IconType getIcon() {
        return IconType.BAN;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Clear selection";
    }

    @Override
    public void afterDraw() {
        super.afterDraw();
        checkState();
    }
    
    @Override
    public <T> void execute(final DefaultCanvasReadOnlySession session, 
                            final ToolbarCommandCallback<T> callback) {
        this.session = session;


        final Command yesCommand = () -> {

            session.getShapeSelectionControl().clearSelection();

            if ( null != callback ) {
                callback.onSuccess( null );
            }

        };

        final Command noCommand = () -> {

        };

        final YesNoCancelPopup popup = YesNoCancelPopup.newYesNoCancelPopup( "Are you sure?", null, yesCommand, 
                noCommand, noCommand );
        
        popup.show();
        
    }
    
    private void checkState() {

        if ( null != session ) {

            final boolean isEmptySelection = session.getShapeSelectionControl().getSelectedItems().isEmpty();

            if ( isEmptySelection ) {
                disable();
            } else {
                enable();
            }
            
        }
        
    }

    void onSingleShapeSelectedEvent(@Observes SelectSingleShapeEvent selectShapeEvent) {
        checkNotNull("selectShapeEvent", selectShapeEvent);
        checkState();
    }

    void onShapeSelectedEvent(@Observes SelectShapeEvent selectShapeEvent) {
        checkNotNull("selectShapeEvent", selectShapeEvent);
        checkState();
    }

    void onShapeDeselectedEvent(@Observes DeselectShapeEvent deselectShapeEvent) {
        checkNotNull("deselectShapeEvent", deselectShapeEvent);
        checkState();
    }

    void onClearSelectionEvent(@Observes ClearSelectionEvent clearSelectionEvent) {
        checkNotNull("clearSelectionEvent", clearSelectionEvent);
        checkState();
    }

}
