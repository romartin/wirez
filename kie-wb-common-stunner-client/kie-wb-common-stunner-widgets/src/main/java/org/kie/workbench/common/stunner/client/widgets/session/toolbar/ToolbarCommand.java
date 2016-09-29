package org.kie.workbench.common.stunner.client.widgets.session.toolbar;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

public interface ToolbarCommand<S extends CanvasSession> {

    IconType getIcon();
    
    String getCaption();
    
    String getTooltip();

    ToolbarCommand<S> initialize( Toolbar<S> toolbar, S session );

    void execute( );

    <T> void execute( ToolbarCommandCallback<T> callback );
    
    void destroy();

}
