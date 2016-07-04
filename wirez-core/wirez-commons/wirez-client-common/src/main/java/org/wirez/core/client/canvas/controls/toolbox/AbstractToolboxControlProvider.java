package org.wirez.core.client.canvas.controls.toolbox;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.components.toolbox.ToolboxFactory;
import org.wirez.core.graph.Element;

public abstract class AbstractToolboxControlProvider implements ToolboxControlProvider<AbstractCanvasHandler, Element> {

    protected ToolboxFactory toolboxFactory;

    public AbstractToolboxControlProvider( final ToolboxFactory toolboxFactory ) {
        this.toolboxFactory = toolboxFactory;
    }

}
