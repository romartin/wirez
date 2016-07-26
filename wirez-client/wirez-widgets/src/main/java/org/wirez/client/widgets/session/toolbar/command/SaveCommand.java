package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.actions.CanvasSaveControl;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.client.validation.canvas.CanvasValidationViolation;
import org.wirez.core.client.validation.canvas.CanvasValidatorCallback;
import org.wirez.core.diagram.Diagram;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class SaveCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    @Inject
    public SaveCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                        final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public IconType getIcon() {
        return IconType.SAVE;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Save";
    }

    @Override
    public <T> void execute( final ToolbarCommandCallback<T> callback ) {

        if ( null != session && null != session.getCanvasSaveControl() ) {

            final CanvasSaveControl<AbstractCanvasHandler> saveControl = session.getCanvasSaveControl();

            saveControl.save( new CanvasValidatorCallback() {
                @Override
                public void onSuccess() {

                    final Diagram diagram = session.getCanvasHandler().getDiagram();

                    // TODO: Review this...
                    callback.onCommandExecuted( ( T ) diagram );

                }

                @Override
                public void onFail( final Iterable<CanvasValidationViolation> violations ) {

                    // TODO: Review this...
                    callback.onCommandExecuted( ( T ) violations );

                }

            } );

        }

    }

}
