package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class ClearSelectionCommand extends AbstractSelectionToolbarCommand<DefaultCanvasReadOnlySession> {

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
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        if ( null != session.getShapeSelectionControl() ) {

            session.getShapeSelectionControl().clearSelection();

            if ( null != callback ) {
                callback.onSuccess( null );
            }

        }

    }
    
}
