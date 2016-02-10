package org.wirez.core.client.control.toolbox.action;

import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.control.toolbox.Toolbox;
import org.wirez.core.client.util.SVGUtils;
import org.wirez.core.client.util.ShapeUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NameEditToolboxAction implements ToolboxCommand {

    Toolbox<Element> toolbox;

    @Inject
    public NameEditToolboxAction(Toolbox<Element> toolbox) {
        this.toolbox = toolbox;
    }

    @Override
    public Shape<?> getIcon() {
        return (Shape<?>) SVGUtils.createSVGIcon(SVGUtils.getTextEdit());
    }

    @Override
    public void execute(final CanvasHandler canvasHandler, 
                        final Element element, 
                        final Shape shape,
                        final double x, 
                        final double y) {
        toolbox.initialize(canvasHandler);
        final double[] xy = ShapeUtils.getContainerXY(shape);
        toolbox.show(element, xy[0], xy[1]);
    }
}
