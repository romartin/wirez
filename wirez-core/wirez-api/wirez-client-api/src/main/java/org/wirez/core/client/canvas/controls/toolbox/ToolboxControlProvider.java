package org.wirez.core.client.canvas.controls.toolbox;

import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;

import java.util.List;

public interface ToolboxControlProvider<T, I> {
    
    boolean supports(Object definition);
    
    List<ToolboxCommand<?, I>> getCommands(T item);
    
}
