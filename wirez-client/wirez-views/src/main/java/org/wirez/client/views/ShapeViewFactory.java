package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.client.views.primitives.CircleView;
import org.wirez.client.views.primitives.RectangleView;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ShapeViewFactory {
    
    @Produces
    public RectangleView rectangle(final double width, final double height) {
        return new RectangleView(width, height);
    }

    @Produces
    public CircleView circle(final double radius) {
        return new CircleView(radius);
    }

    @Produces
    public WiresRectangleView wiresRectangle(final double width,
                                             final double height,
                                             final WiresManager wiresManager) {
        return new WiresRectangleView(width, height, wiresManager);
    }
    
}
