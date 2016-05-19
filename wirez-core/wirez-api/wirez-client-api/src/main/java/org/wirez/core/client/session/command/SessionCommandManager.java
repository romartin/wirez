package org.wirez.core.client.session.command;

import org.wirez.core.command.stack.StackCommandManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;

public interface SessionCommandManager<H extends CanvasHandler> 
        extends CanvasCommandManager<H>, StackCommandManager<H, CanvasViolation> {
    
}
