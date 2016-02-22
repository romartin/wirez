package org.wirez.bpmn.client.view;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.bpmn.client.TaskShape;
import org.wirez.client.shapes.WiresPolygonView;
import org.wirez.client.shapes.WiresRectangleView;

public class ParallelGatewayShapeView extends WiresPolygonView<ParallelGatewayShapeView> {

    private Group gatewayTypeIcon;
    
    public ParallelGatewayShapeView(final double radius,
                                    final String fillColor,
                                    final WiresManager manager) {
        super(radius, fillColor, manager);
        init(radius);
    }

    @Override
    protected void init(final double radius) {
        super.init(radius);
        gatewayTypeIcon = new Group();
        final double[] gwTypeIconSize = updateGwTypeIcon(radius);

        this.addChild(decorator, WiresLayoutContainer.Layout.CENTER);
        this.addChild(gatewayTypeIcon, WiresLayoutContainer.Layout.CENTER,
                - ( gwTypeIconSize[0] / 2 ), - ( gwTypeIconSize[1] / 2 ) );
    }

    @Override
    public ParallelGatewayShapeView setRadius(double radius) {
        super.setRadius(radius);
        updateGwTypeIcon(radius);
        return this;
    }

    private double[] updateGwTypeIcon(final double radius) {
        if ( radius > 0 ) {
            gatewayTypeIcon.removeAll();

            final double lineSize = radius / 2;
            final double lineAlpha = 0.8;
            Line hLine = new Line( 0 , 0, lineSize, 0).setY(lineSize / 2);
            hLine.setStrokeWidth(2);
            hLine.setStrokeAlpha(lineAlpha);
            Line vLine = new Line(0, 0, 0, lineSize).setX(lineSize / 2);
            vLine.setStrokeWidth(2);
            vLine.setStrokeAlpha(lineAlpha);
            gatewayTypeIcon.add(hLine);
            gatewayTypeIcon.add(vLine);
            final BoundingBox bb = gatewayTypeIcon.getBoundingBox();
            return new double[] {bb.getWidth(), bb.getHeight()};
        }
        return null;
    }

}
