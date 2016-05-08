package org.wirez.core.client.components.popup;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractPopupBox<E extends Element> implements PopupBox<AbstractCanvasHandler, E> {

    protected AbstractCanvasHandler canvasHandler;

    @Override
    public void initialize(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;

    }

    // TODO
    /*protected final CanvasElementListener canvasListener = new AbstractCanvasElementListener(canvasHandler) {

        @Override
        public void onElementAdded(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onElementModified(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onElementDeleted(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onClear() {
            BaseToolbox.this.hide();
        }

    };*/

}