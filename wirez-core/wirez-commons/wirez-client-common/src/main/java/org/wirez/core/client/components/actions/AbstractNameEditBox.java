package org.wirez.core.client.components.actions;

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.mvp.Command;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.graph.Element;

public abstract class AbstractNameEditBox<E extends Element> implements NameEditBox<AbstractCanvasHandler, E>, IsWidget {

    protected AbstractCanvasHandler canvasHandler;
    protected Command closeCallback;

    @Override
    public void initialize(final AbstractCanvasHandler canvasHandler,
                           final Command closeCallback) {
        this.canvasHandler = canvasHandler;
        this.closeCallback = closeCallback;

    }

}