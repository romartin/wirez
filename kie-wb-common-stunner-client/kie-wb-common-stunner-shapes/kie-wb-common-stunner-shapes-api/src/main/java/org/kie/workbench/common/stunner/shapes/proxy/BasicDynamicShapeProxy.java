package org.kie.workbench.common.stunner.shapes.proxy;

public interface BasicDynamicShapeProxy<W>
    extends BasicShapeProxy<W> {

    String getBackgroundColor( W element );

    double getBackgroundAlpha( W element );

    String getBorderColor( W element );

    double getBorderSize( W element );

    double getBorderAlpha( W element );

}
