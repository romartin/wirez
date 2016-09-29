package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.command.stack.StackCommandManager;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;


public interface SessionCommandManager<H extends CanvasHandler> 
        extends CanvasCommandManager<H>, StackCommandManager<H, CanvasViolation> {
    
}
