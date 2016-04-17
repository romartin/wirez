package org.wirez.core.client.components.popup;

import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

public interface PopupBox<C extends CanvasHandler, E extends Element> extends IsWidget {

    void initialize(C canvasHandler);

    void show(E element, double x, double y);

    void hide();
}