package org.wirez.bpmn.client.shape.view;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.wirez.client.shapes.view.WiresPolygonView;

public class ParallelGatewayShapeView extends WiresPolygonView<ParallelGatewayShapeView> {

    private Group gatewayTypeIcon;
    
    public ParallelGatewayShapeView(final double radius,
                                    final String fillColor,
                                    final WiresManager manager) {
        super(radius, fillColor, manager);
    }

    @Override
    protected void initialize() {
        super.initialize();
        gatewayTypeIcon = new Group();
        gatewayTypeIcon.setDraggable(false);
        this.addChild(gatewayTypeIcon, WiresLayoutContainer.Layout.CENTER);
    }

    @Override
    protected void doMoveChildren(final double width, 
                                  final double height) {
        super.doMoveChildren(width, height);
        updateGwTypeIcon( width / 2 );
        final BoundingBox bb = gatewayTypeIcon.getBoundingBox();
        final double w = bb.getWidth() / 2;
        final double h = bb.getHeight() / 2;
        this.moveChild(gatewayTypeIcon, - w, - h );
    }

    @Override
    public ParallelGatewayShapeView setRadius(final double radius) {
        super.setRadius(radius);
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
