package org.wirez.client.shapes.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import org.wirez.core.client.shape.view.ShapeGlyph;

public abstract class AbstractGlyph implements ShapeGlyph<Group> {
    
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
    public Group copy() {
        return group.copy();
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
