package org.wirez.shapes.client.view.glyph;

import com.ait.lienzo.client.core.shape.Arrow;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ArrowType;
import org.wirez.client.lienzo.shape.view.glyph.AbstractLienzoShapeGlyph;

public final class ConnectorGlyph extends AbstractLienzoShapeGlyph {

    public ConnectorGlyph(final double width, final double height, final String color) {
        super( new Group(), width, height );
        build(width, height, color);
    }

    private void build(final double width, final double height, final String color) {
        group.removeAll();
        final Arrow arrow = new Arrow(new Point2D(0, height), new Point2D(width, 0), 5, 10, 45, 45, ArrowType.AT_END)
                .setStrokeWidth(5).setStrokeColor(color).setDraggable(true);
        group.add(arrow);
        scaleTo( group, width - arrow.getStrokeWidth() , height - arrow.getStrokeWidth() );
    }

    private void scaleTo( Group group, final double width, final double height ) {
        final BoundingBox bb = group.getBoundingBox();
        final double w = bb.getWidth();
        final double h = bb.getHeight();
        final double sw = w > 0 ?  ( width / w) : 1;
        final double sh = h > 0 ? ( height / h ) : 1;
        group.setScale( sw, sh );
    }

}
