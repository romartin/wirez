package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.shapes.client.view.animatiion.AnimatedWiresShapeView;

public class RectangleView<T extends RectangleView> extends AnimatedWiresShapeView<T>
        implements HasSize<T> {

    private Rectangle rectangle;
    
    public RectangleView(final double width,
                         final double height,
                         final WiresManager manager) {
        super(new MultiPath().rect(0, 0, width, height), manager);
    }

    @Override
    protected Shape getPrimitive() {
        return rectangle;
    }

    @Override
    protected Shape<?> createChildren() {
        rectangle = new Rectangle(1, 1);
        this.addChild(rectangle, WiresLayoutContainer.Layout.CENTER);
        final Rectangle decorator = new Rectangle(1, 1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        return decorator;
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

    @Override
    protected void doDestroy() {
        super.doDestroy();

        rectangle = null;
    }
    
}
