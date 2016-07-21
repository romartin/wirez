package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.rule.graph.GraphRulesManager;
import org.wirez.core.validation.canvas.*;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class ValidateCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    CanvasValidator canvasValidator;
    Event<CanvasValidationSuccessEvent> validationSuccessEvent;
    Event<CanvasValidationFailEvent> validationFailEvent;

    @Inject
    public ValidateCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                            final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                            final CanvasValidator canvasValidator,
                            final Event<CanvasValidationSuccessEvent> validationSuccessEvent,
                            final Event<CanvasValidationFailEvent> validationFailEvent ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
        this.canvasValidator = canvasValidator;
        this.validationSuccessEvent = validationSuccessEvent;
        this.validationFailEvent = validationFailEvent;
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
        return "Validate the diagram";
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        final CanvasHandler canvasHandler = session.getCanvasHandler();
        final GraphRulesManager rulesManager = session.getCanvasHandler().getRuleManager();

        canvasValidator
                .withRulesManager( rulesManager )
                .validate( canvasHandler, new CanvasValidatorCallback() {

                    @Override
                    public void onSuccess() {

                        callback.onSuccess( null );

                        validationSuccessEvent.fire( new CanvasValidationSuccessEvent( canvasHandler ) );
                    }

                    @Override
                    public void onFail( final Iterable<CanvasValidationViolation> violations ) {

                        callback.onSuccess( ( T ) violations );

                        validationFailEvent.fire( new CanvasValidationFailEvent( canvasHandler, violations ) );
                    }

                } );
        
    }

}
