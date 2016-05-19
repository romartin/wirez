package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.shapes.proxy.icon.ICONS;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShapeViewFactory {

    public RectangleView rectangle(final double width,
                                   final double height,
                                   final WiresManager wiresManager) {
        return new RectangleView(width, height, wiresManager);
    }

    public IconShapeView icon(final ICONS icon,
                              final double width,
                              final double height,
                              final WiresManager manager) {
        return new IconShapeView(icon, width, height, manager);
    }

    public CircleView circle(final double radius,
                             final WiresManager wiresManager) {
        return new CircleView(radius, wiresManager);
    }

    public PolygonView polygon(final double radius,
                               final String fillColor,
                               final WiresManager wiresManager) {
        return new PolygonView(radius, fillColor, wiresManager);
    }

    public ConnectorView connector(final WiresManager manager,
                                   final double... points) {
        return new ConnectorView(manager, points);
    }

}
