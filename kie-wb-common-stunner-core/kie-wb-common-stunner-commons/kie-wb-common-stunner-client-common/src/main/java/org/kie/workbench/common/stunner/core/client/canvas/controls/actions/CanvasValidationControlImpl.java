package org.kie.workbench.common.stunner.core.client.canvas.controls.actions;

import org.kie.workbench.common.stunner.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.kie.workbench.common.stunner.core.client.validation.canvas.*;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class CanvasValidationControlImpl
        extends AbstractCanvasHandlerControl
        implements CanvasValidationControl<AbstractCanvasHandler> {

    CanvasValidator canvasValidator;
    Event<CanvasValidationSuccessEvent> validationSuccessEvent;
    Event<CanvasValidationFailEvent> validationFailEvent;

    @Inject
    public CanvasValidationControlImpl( final CanvasValidator canvasValidator,
            final Event<CanvasValidationSuccessEvent> validationSuccessEvent,
            final Event<CanvasValidationFailEvent> validationFailEvent) {
        this.canvasValidator = canvasValidator;
        this.validationSuccessEvent = validationSuccessEvent;
        this.validationFailEvent = validationFailEvent;
    }

    @Override
    protected void doDisable() {

        this.canvasValidator = null;
        this.validationSuccessEvent = null;
        this.validationFailEvent = null;

    }


    @Override
    public void validate( final CanvasValidatorCallback validatorCallback ) {

        if ( null != canvasHandler ) {

            final GraphRulesManager rulesManager = canvasHandler.getRuleManager();

            canvasValidator
                    .withRulesManager( rulesManager )
                    .validate( canvasHandler, new CanvasValidatorCallback() {

                        @Override
                        public void onSuccess() {

                            validatorCallback.onSuccess();

                            validationSuccessEvent.fire( new CanvasValidationSuccessEvent( canvasHandler ) );
                        }

                        @Override
                        public void onFail( final Iterable<CanvasValidationViolation> violations ) {

                            validatorCallback.onFail( violations );

                            validationFailEvent.fire( new CanvasValidationFailEvent( canvasHandler, violations ) );
                        }

                    } );

        }

    }

}
