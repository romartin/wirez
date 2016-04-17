package org.wirez.client.shapes.glyph;

import com.ait.lienzo.client.core.shape.Group;
import org.wirez.core.client.shape.view.ShapeGlyph;

public class AbstractGlyph implements ShapeGlyph {
    
    protected final Group group = new Group();
    protected final double width;
    protected final double height;

    public AbstractGlyph(final double width, 
                         final double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
    
}
