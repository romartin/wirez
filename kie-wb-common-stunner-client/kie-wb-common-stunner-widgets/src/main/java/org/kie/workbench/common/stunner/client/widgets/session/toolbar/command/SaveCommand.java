package org.kie.workbench.common.stunner.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.actions.CanvasSaveControl;
import org.kie.workbench.common.stunner.core.client.session.impl.DefaultCanvasFullSession;
import org.kie.workbench.common.stunner.core.client.validation.canvas.CanvasValidationViolation;
import org.kie.workbench.common.stunner.core.client.validation.canvas.CanvasValidatorCallback;
import org.kie.workbench.common.stunner.core.diagram.Diagram;

import javax.enterprise.context.Dependent;

@Dependent
public class SaveCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

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

    @Override
    protected boolean getState() {
        // Always active for now.
        return true;
    }

}
