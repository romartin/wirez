package org.kie.workbench.common.stunner.shapes.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;

public interface BasicShapeProxy<W>
    extends ShapeProxy<W>, BasicGlyphProxy<W> {

    String getBackgroundColor( W element );

    double getBackgroundAlpha( W element );

    String getBorderColor( W element );

    double getBorderSize( W element );

    double getBorderAlpha( W element );

}
