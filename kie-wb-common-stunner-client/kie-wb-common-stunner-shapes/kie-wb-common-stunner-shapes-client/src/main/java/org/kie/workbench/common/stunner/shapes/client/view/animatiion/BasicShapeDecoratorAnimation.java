package org.kie.workbench.common.stunner.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.shapes.client.BasicShape;
import org.kie.workbench.common.stunner.shapes.client.view.BasicShapeView;

public final class BasicShapeDecoratorAnimation extends BasicDecoratorAnimation<BasicShape> {

    public BasicShapeDecoratorAnimation( final String color,
                                         final double strokeWidth,
                                         final double strokeAlpha ) {
        super( color, strokeWidth, strokeAlpha );
    }

    @Override
    Shape getDecorator() {
        return getView().getShape();
    }

    private BasicShapeView<?> getView() {
        return ( BasicShapeView<?> ) getSource().getShapeView();
    }

}
