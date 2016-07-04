package org.wirez.core.client.canvas.controls.toolbox.command.actions;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.components.popup.PopupBox;
import org.wirez.core.graph.Element;

public abstract class NameToolboxCommand<I> extends AbstractToolboxCommand<I> implements IsWidget {

    PopupBox<AbstractCanvasHandler, Element> toolbox;

    private final I icon;
    
    public NameToolboxCommand( final PopupBox<AbstractCanvasHandler, Element> toolbox,
                               final I icon) {
        this.toolbox = toolbox;
        this.icon = icon;
    }

    @Override
    public I getIcon( final double width, final double height ) {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Edit name";
    }

    @Override
    public void click(final Context<AbstractCanvasHandler> context,
                        final Element element) {
        toolbox.initialize(context.getCanvasHandler());
        toolbox.show(element, context.getX(), context.getY());
    }

    @Override
    public Widget asWidget() {
        return ( (IsWidget )toolbox).asWidget();
    }

    @Override
    public void destroy() {
        toolbox.hide();
        toolbox = null;
    }
    
}
