package org.wirez.client.lienzo.components.toolbox;

import org.wirez.client.lienzo.components.toolbox.builder.LienzoToolboxBuilderImpl;
import org.wirez.client.lienzo.components.toolbox.builder.LienzoToolboxButtonBuilder;
import org.wirez.client.lienzo.components.toolbox.builder.LienzoToolboxButtonGridBuilder;
import org.wirez.core.client.components.toolbox.ToolboxFactory;
import org.wirez.core.client.components.toolbox.builder.ToolboxBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;

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
