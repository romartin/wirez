package org.kie.workbench.common.stunner.client.widgets.session.toolbar;

import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

public interface Toolbar<S extends CanvasSession> {

    void initialize( S session, ToolbarCommandCallback<?> callback  );

    void addCommand( ToolbarCommand<S> command );

    void disable( final ToolbarCommand<S> command );

    void enable( final ToolbarCommand<S> command );

    void show();

    void hide();

    void destroy();

    ToolbarView<S> getView();

}
