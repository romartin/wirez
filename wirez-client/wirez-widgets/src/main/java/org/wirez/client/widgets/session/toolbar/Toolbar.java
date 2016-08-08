package org.wirez.client.widgets.session.toolbar;

import org.wirez.core.client.session.CanvasSession;

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
