package org.wirez.core.client.control.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.util.SVGUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MoveDownCommand implements ToolboxCommand {

    CanvasCommandFactory commandFactory;
    private final Shape<?> icon;

    @Inject
    public MoveDownCommand(CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        this.icon = (Shape<?>) SVGUtils.createSVGIcon(SVGUtils.getMoveDown());;
    }

    @Override
    public Shape<?> getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Move down";
    }

    @Override
    public void execute(final Context context, 
                        final Element element) {
        Canvas canvas = context.getCanvasHandler().getCanvas();
        org.wirez.core.client.Shape shape = canvas.getShape(element.getUUID());
        shape.getShapeView().moveDown();
    }
    
}
