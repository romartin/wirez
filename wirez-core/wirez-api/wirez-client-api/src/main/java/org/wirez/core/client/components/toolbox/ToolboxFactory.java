package org.wirez.core.client.components.toolbox;

import org.wirez.core.client.components.toolbox.builder.ToolboxBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;

public interface ToolboxFactory {
    
    ToolboxBuilder<?, ?, ?> toolboxBuilder();

    ToolboxButtonGridBuilder toolboxGridBuilder();
    
    ToolboxButtonBuilder<?> toolboxButtonBuilder();
    
}
