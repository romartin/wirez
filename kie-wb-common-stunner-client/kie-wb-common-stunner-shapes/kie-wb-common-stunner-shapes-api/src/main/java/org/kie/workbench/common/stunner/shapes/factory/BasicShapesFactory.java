package org.kie.workbench.common.stunner.shapes.factory;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.MutableShape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeDefFactory;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.BasicShapeDef;

public interface BasicShapesFactory<W, H extends CanvasHandler>
    extends ShapeDefFactory<W, H, MutableShape<W, ShapeView>, ShapeDef<W>> {
}
