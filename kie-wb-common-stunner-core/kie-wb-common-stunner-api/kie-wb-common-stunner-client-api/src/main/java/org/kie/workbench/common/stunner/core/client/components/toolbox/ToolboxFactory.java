package org.kie.workbench.common.stunner.core.client.components.toolbox;

import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;

public interface ToolboxFactory {
    
    ToolboxBuilder<?, ?, ?> toolboxBuilder();

    ToolboxButtonGridBuilder toolboxGridBuilder();
    
    ToolboxButtonBuilder<?> toolboxButtonBuilder();
    
}
