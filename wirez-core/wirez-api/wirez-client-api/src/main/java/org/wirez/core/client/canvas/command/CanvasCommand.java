package org.wirez.core.client.canvas.command;

import org.wirez.core.command.Command;
import org.wirez.core.client.canvas.CanvasHandler;

public interface CanvasCommand<H extends CanvasHandler> extends Command<H, CanvasViolation> {

}
