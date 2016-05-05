package org.wirez.client.shapes;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.client.shapes.animatiion.AnimatedWiresShapeView;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.event.ViewEventType;

public class WiresCircleView<T extends WiresCircleView> extends AnimatedWiresShapeView<T> 
        implements HasRadius<T> {

    protected Circle circle;
    protected Circle decorator;

    public WiresCircleView(final double radius, 
                           final WiresManager manager) {
        super(new MultiPath().rect(0,0, radius * 2, radius * 2), manager);
    }

    @Override
    protected Shape getShape() {
        return circle;
    }

    @Override
    protected void initialize() {
        circle = new Circle(1);
        this.addChild(circle, WiresLayoutContainer.Layout.CENTER);
        decorator = new Circle(1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    protected Shape getDecorator() {
        return decorator;
    }
    
    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.DRAG.equals( type );
    }

    @Override
    public T setRadius(final double radius) {
        return super.setRadius( radius );
    }

}
