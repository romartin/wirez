package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.client.shapes.view.animatiion.AnimatedWiresShapeView;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.core.client.shape.view.event.ViewEventType;

public class WiresRectangleView<T extends WiresRectangleView> extends AnimatedWiresShapeView<T>
        implements HasSize<T> {

    private Rectangle rectangle;
    private Rectangle decorator;
    
    public WiresRectangleView(final double width,
                              final double height,
                              final WiresManager manager) {
        super(new MultiPath().rect(0, 0, width, height), manager);
    }

    @Override
    protected Shape getDecorator() {
        return decorator;
    }

    @Override
    protected Shape getShape() {
        return rectangle;
    }

    @Override
    protected void initialize() {

        rectangle = new Rectangle(1, 1);
        this.addChild(rectangle, WiresLayoutContainer.Layout.CENTER);
        decorator = new Rectangle(1, 1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.DRAG.equals( type );
    }
    
    @Override
    public T setSize(final double width, final double height) {
        super.setSize( width ,height );
        return (T) this;
    }
    
    protected void doMoveChildren(final double width, final double height) {
        this.moveChild(rectangle, getChildCoordinate(width), getChildCoordinate(height));
        this.moveChild(decorator, getChildCoordinate(width), getChildCoordinate(height));
    }

    protected double getChildCoordinate(final double c) {
        return - ( c / 2 );
    }

}
