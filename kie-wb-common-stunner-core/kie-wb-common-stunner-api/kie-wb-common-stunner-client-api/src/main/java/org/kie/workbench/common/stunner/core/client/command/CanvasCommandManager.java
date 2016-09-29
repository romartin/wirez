package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.command.batch.BatchCommandManager;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface CanvasCommandManager<H extends CanvasHandler> extends BatchCommandManager<H, CanvasViolation> {
    
}
