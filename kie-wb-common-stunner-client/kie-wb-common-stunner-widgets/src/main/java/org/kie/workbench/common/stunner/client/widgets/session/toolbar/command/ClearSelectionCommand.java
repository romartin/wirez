package org.kie.workbench.common.stunner.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.kie.workbench.common.stunner.core.client.session.impl.DefaultCanvasReadOnlySession;

import javax.enterprise.context.Dependent;

@Dependent
public class ClearSelectionCommand extends AbstractSelectionToolbarCommand<DefaultCanvasReadOnlySession> {

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
                callback.onCommandExecuted( null );
            }

        }

    }


}
