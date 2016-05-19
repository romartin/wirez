package org.wirez.client.lienzo.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.components.popup.PopupBox;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NameToolboxCommand extends org.wirez.core.client.canvas.controls.toolbox.command.NameToolboxCommand<Shape<?>> {
    
    @Inject
    public NameToolboxCommand( final PopupBox<AbstractCanvasHandler, Element> toolbox ) {
        super( toolbox, SVGUtils.createSVGIcon(SVGUtils.getTextEditIcon()) );
    }
    
}
