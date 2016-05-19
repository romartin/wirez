package org.wirez.bpmn.client.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Arrow;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ArrowType;
import org.wirez.client.lienzo.shape.view.glyph.AbstractLienzoShapeGlyph;

public class SequenceFlowGlyph extends AbstractLienzoShapeGlyph {

    public SequenceFlowGlyph(final double width, final double height, final String color) {
        super( new Group(), width, height );
        build(width, height, color);
    }

    private void build(final double width, final double height, final String color) {
        group.removeAll();
        final Arrow arrow = new Arrow(new Point2D(0, height), new Point2D(width, 0), 5, 10, 45, 45, ArrowType.AT_END)
                .setStrokeWidth(5).setStrokeColor(color).setDraggable(true);
        group.add(arrow);
    }

}