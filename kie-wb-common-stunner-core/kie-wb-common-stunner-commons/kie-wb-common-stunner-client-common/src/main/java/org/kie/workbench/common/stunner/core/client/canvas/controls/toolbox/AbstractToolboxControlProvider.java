package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.components.toolbox.ToolboxFactory;
import org.kie.workbench.common.stunner.core.graph.Element;

public abstract class AbstractToolboxControlProvider implements ToolboxControlProvider<AbstractCanvasHandler, Element> {

    protected ToolboxFactory toolboxFactory;

    public AbstractToolboxControlProvider( final ToolboxFactory toolboxFactory ) {
        this.toolboxFactory = toolboxFactory;
    }

}
