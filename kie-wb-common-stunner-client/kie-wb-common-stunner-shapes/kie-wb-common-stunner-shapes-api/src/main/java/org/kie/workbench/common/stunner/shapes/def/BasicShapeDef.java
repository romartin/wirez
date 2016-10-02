package org.kie.workbench.common.stunner.shapes.def;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;

public interface BasicShapeDef<W>
    extends ShapeDef<W>, BasicGlyphDef<W> {

    String getBackgroundColor( W element );

    double getBackgroundAlpha( W element );

    String getBorderColor( W element );

    double getBorderSize( W element );

    double getBorderAlpha( W element );

}
