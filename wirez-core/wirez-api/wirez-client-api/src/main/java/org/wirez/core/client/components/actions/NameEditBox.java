package org.wirez.core.client.components.actions;

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.mvp.Command;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.graph.Element;

public interface NameEditBox<C extends CanvasHandler, E extends Element> extends IsWidget {

    void initialize( C canvasHandler, Command closeCallback );

    void show( E element );

    void hide();

}