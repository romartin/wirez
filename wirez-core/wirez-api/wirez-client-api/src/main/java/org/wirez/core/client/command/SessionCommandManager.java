package org.wirez.core.client.command;

import org.wirez.core.command.stack.StackCommandManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;


public interface SessionCommandManager<H extends CanvasHandler> 
        extends CanvasCommandManager<H>, StackCommandManager<H, CanvasViolation> {
    
}
