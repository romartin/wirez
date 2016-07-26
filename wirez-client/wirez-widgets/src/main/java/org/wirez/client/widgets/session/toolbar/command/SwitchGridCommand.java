package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.canvas.CanvasGrid;
import org.wirez.core.client.canvas.DefaultCanvasGrid;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class SwitchGridCommand extends AbstractToolbarCommand<DefaultCanvasReadOnlySession> {

    private CanvasGrid grid;

    @Inject
    public SwitchGridCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                              final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
    }

    @Override
    public ToolbarCommand<DefaultCanvasReadOnlySession> initialize( final DefaultCanvasReadOnlySession session ) {
        super.initialize( session );
        showGrid();
        return this;
    }

    @Override
    public IconType getIcon() {
        return IconType.TH;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Switch grid";
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        if ( isGridVisible() ) {

            hideGrid();

        } else {

            showGrid();

        }

    }

    private void showGrid() {

         this.grid = DefaultCanvasGrid.INSTANCE;

        session.getCanvas().setGrid(  this.grid );

    }

    private void hideGrid() {

        this.grid = null;

        session.getCanvas().setGrid(  this.grid );

    }

    private boolean isGridVisible() {

        return null != grid;

    }

    @Override
    protected void doDestroy() {

        super.doDestroy();

        this.grid = null;

    }

}
