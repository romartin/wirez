package org.wirez.client.views;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.Decorator;
import com.ait.lienzo.client.core.shape.wires.WiresManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ShapeViewFactory {

    @Produces
    public WiresRectangleView rectangle(final double width,
                                        final double height,
                                        final WiresManager wiresManager) {
        return new WiresRectangleView(width, height, wiresManager);
    }

    @Produces
    public WiresCircleView circle(final double radius,
                                  final WiresManager wiresManager) {
        return new WiresCircleView(radius, wiresManager);
    }

    @Produces
    public WiresPolygonView polygon(final double radius,
                                    final String fillColor,
                                    final WiresManager wiresManager) {
        return new WiresPolygonView(radius, fillColor, wiresManager);
    }

    @Produces
    public WiresConnectorView connector(final WiresManager manager,
                                        final double... points) {
        return new WiresConnectorView(manager, points);
    }

}
