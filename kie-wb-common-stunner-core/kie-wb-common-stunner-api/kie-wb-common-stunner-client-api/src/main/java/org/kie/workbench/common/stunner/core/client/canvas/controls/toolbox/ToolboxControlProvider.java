package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxButtonGrid;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxBuilder;

import java.util.List;

public interface ToolboxControlProvider<C extends CanvasHandler, T> {
    
    boolean supports( Object definition );

    ToolboxButtonGrid getGrid( C context, T item );

    ToolboxBuilder.Direction getOn();

    ToolboxBuilder.Direction getTowards();

    List<ToolboxCommand<?, ?>> getCommands(  C context, T item );


}
