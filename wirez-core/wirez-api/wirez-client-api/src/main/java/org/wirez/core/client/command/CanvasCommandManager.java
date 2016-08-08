package org.wirez.core.client.command;

import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.client.canvas.CanvasHandler;

public interface CanvasCommandManager<H extends CanvasHandler> extends BatchCommandManager<H, CanvasViolation> {
    
}
