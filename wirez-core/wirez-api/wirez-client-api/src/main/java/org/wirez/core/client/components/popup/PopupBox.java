package org.wirez.core.client.components.popup;

import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.CanvasHandler;

public interface PopupBox<C extends CanvasHandler, E extends Element> {

    void initialize(C canvasHandler);

    void show(E element, double x, double y);

    void hide();
}