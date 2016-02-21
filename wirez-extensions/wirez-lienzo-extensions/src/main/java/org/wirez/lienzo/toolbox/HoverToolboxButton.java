package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.util.Geometry;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;

public class HoverToolboxButton {
    private final IPrimitive<?> primitive;
    public static final double BUTTON_SIZE = 16;
    private final NodeMouseClickHandler clickHandler;

    public HoverToolboxButton(Shape<?> shape) {
        this(shape, new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
                
            }
        });
    }

    public HoverToolboxButton(Shape<?> shape, NodeMouseClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.primitive = build(shape, BUTTON_SIZE, BUTTON_SIZE);
    }

    public IPrimitive<?> getShape() {
        return primitive;
    }
    
    private IPrimitive<?> build(final Shape<?> shape, final double width, final double height) {
        final Group group = new Group();

        Geometry.setScaleToFit(shape, width, height);
        group.add( shape );
        
        final Rectangle decorator = new Rectangle(width, height)
                .setFillColor(ColorName.LIGHTGREY)
                .setFillAlpha(0.2)
                .setStrokeWidth(0)
                .setCornerRadius(10);
        decorator.addNodeMouseClickHandler(clickHandler);
        group.add( decorator );
        
        return group;
    }
}
