package org.kie.workbench.common.stunner.client.lienzo.components.toolbox;

import org.kie.workbench.common.stunner.client.lienzo.components.toolbox.builder.LienzoToolboxBuilderImpl;
import org.kie.workbench.common.stunner.client.lienzo.components.toolbox.builder.LienzoToolboxButtonBuilder;
import org.kie.workbench.common.stunner.client.lienzo.components.toolbox.builder.LienzoToolboxButtonGridBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxFactory;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.kie.workbench.common.stunner.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LienzoToolboxFactory implements ToolboxFactory {
    
    @Override
    public ToolboxBuilder<?, ?, ?> toolboxBuilder() {
        return new LienzoToolboxBuilderImpl();
    }

    @Override
    public ToolboxButtonGridBuilder toolboxGridBuilder() {
        return new LienzoToolboxButtonGridBuilder();
    }

    @Override
    public ToolboxButtonBuilder<?> toolboxButtonBuilder() {
        return new LienzoToolboxButtonBuilder();
    }
    
}
