package org.wirez.core.client.control.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.control.toolbox.Toolbox;
import org.wirez.core.client.util.SVGUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NameToolboxCommand implements ToolboxCommand, IsWidget {

    Toolbox<Element> toolbox;

    private final Shape<?> icon;
    
    @Inject
    public NameToolboxCommand(Toolbox<Element> toolbox) {
        this.toolbox = toolbox;
        this.icon = (Shape<?>) SVGUtils.createSVGIcon(SVGUtils.getTextEdit());
    }

    @Override
    public Shape<?> getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Edit name";
    }

    @Override
    public void execute(final Context context, 
                        final Element element) {
        toolbox.initialize(context.getCanvasHandler());
        //final double[] xy = ShapeUtils.getContainerXY(shape);
        //toolbox.show(element, xy[0], xy[1]);
        toolbox.show(element, context.getX(), context.getY());
    }

    @Override
    public Widget asWidget() {
        return toolbox.asWidget();
    }
}
