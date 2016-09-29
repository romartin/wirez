package org.kie.workbench.common.stunner.core.client.shape.view;

import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;

public abstract class AbstractShapeGlyph<G> implements ShapeGlyph<G> {
    
    protected final G group;
    protected final double width;
    protected final double height;

    protected AbstractShapeGlyph(final G group, 
                                 final double width, 
                                 final double height) {
        this.group = group;
        this.width = width;
        this.height = height;
    }
    
    protected abstract G doCopy();

    @Override
    public G getGroup() {
        return group;
    }

    @Override
    public G copy() {
        return doCopy();
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
