package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButton;
import org.kie.workbench.common.stunner.core.graph.Element;

public interface ToolboxCommand<H extends CanvasHandler, I> {

    enum Status {
        ENABLED, DISABLED;
    }
    
    I getIcon( double width, double height );

    String getTitle();

    ToolboxButton.HoverAnimation getButtonAnimation();

    // TODO: add method -> Status check( Element element );
    
    void execute( Context<H> context, Element element );
    
    void destroy();
    
}
