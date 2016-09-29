package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;

public interface CanvasCommand<H extends CanvasHandler> extends Command<H, CanvasViolation> {

}
