package org.wirez.client.widgets.session.toolbar;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.core.client.session.CanvasSession;

public interface ToolbarCommand<S extends CanvasSession> {

    IconType getIcon();
    
    String getCaption();
    
    String getTooltip();

    void execute( S session );
    
    <T> void 
    execute( S session, ToolbarCommandCallback<T> callback );
    
}
