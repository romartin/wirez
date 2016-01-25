package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.util.Geometry;
import com.google.gwt.core.client.GWT;

public class HoverToolboxButton {
    private final Shape<?> shape;
    public static final double BUTTON_SIZE = 16;
    private final NodeMouseClickHandler clickHandler;

    public HoverToolboxButton(Shape<?> shape) {
        this(shape, new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
                GWT.log("Default Click Handler");
            }
        });
    }

    public HoverToolboxButton(Shape<?> shape, NodeMouseClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        Geometry.setScaleToFit(shape, BUTTON_SIZE, BUTTON_SIZE);
        shape.addNodeMouseClickHandler(clickHandler);
        this.shape = shape;
    }

    public Shape<?> getShape() {
        return shape;
    }
}
