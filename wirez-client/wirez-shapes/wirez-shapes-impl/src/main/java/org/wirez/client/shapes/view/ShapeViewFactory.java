package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.wires.WiresManager;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShapeViewFactory {

    public WiresRectangleView rectangle(final double width,
                                        final double height,
                                        final WiresManager wiresManager) {
        return new WiresRectangleView(width, height, wiresManager);
    }

    public WiresCircleView circle(final double radius,
                                  final WiresManager wiresManager) {
        return new WiresCircleView(radius, wiresManager);
    }

    public WiresPolygonView polygon(final double radius,
                                    final String fillColor,
                                    final WiresManager wiresManager) {
        return new WiresPolygonView(radius, fillColor, wiresManager);
    }

    public WiresConnectorView connector(final WiresManager manager,
                                        final double... points) {
        return new WiresConnectorView(manager, points);
    }

}
