package org.wirez.client.shapes;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.RegularPolygon;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.client.shapes.animatiion.AnimatedWiresShapeView;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.event.ViewEventType;

public class WiresPolygonView<T extends WiresPolygonView> extends AnimatedWiresShapeView<T> 
        implements HasRadius<T> {

    protected RegularPolygon polygon;
    protected RegularPolygon decorator;

    public WiresPolygonView(final double radius,
                            final String fillColor,
                            final WiresManager manager) {
        super(new MultiPath()
                        .M(0 ,radius)
                        .L(radius, 0)
                        .L(radius * 2, radius)
                        .L(radius, ( radius * 2) )
                        .Z(),
                manager);
    }

    @Override
    protected void initialize() {
        polygon = new RegularPolygon(4, 1);
        this.addChild(polygon, WiresLayoutContainer.Layout.CENTER);
        decorator = new RegularPolygon(4, 1);
        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    protected Shape getDecorator() {
        return decorator;
    }

    @Override
    protected Shape getShape() {
        return polygon;
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
