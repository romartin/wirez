package org.wirez.client.lienzo.canvas.controls.toolbox.command.actions;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.actions.NameToolboxCommand;
import org.wirez.core.client.components.popup.PopupBox;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoNameToolboxCommand extends NameToolboxCommand<Shape<?>> {
    
    @Inject
    public LienzoNameToolboxCommand(final PopupBox<AbstractCanvasHandler, Element> toolbox ) {
        super( toolbox, SVGUtils.createSVGIcon(SVGUtils.getSpeechBubbleIcon()) );
    }
    
}
