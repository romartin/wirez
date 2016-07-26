package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.client.validation.canvas.CanvasValidationViolation;
import org.wirez.core.client.validation.canvas.CanvasValidatorCallback;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class ValidateCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    @Inject
    public ValidateCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                            final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public IconType getIcon() {
        return IconType.CHECK;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Validate";
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        session.getCanvasValidationControl().validate( new CanvasValidatorCallback() {

            @Override
            public void onSuccess() {

                // TODO: Review this...
                callback.onCommandExecuted( null );
            }

            @Override
            public void onFail( final Iterable<CanvasValidationViolation> violations ) {

                // TODO: Review this...
                callback.onCommandExecuted( ( T ) violations );

            }

        } );

    }

}
