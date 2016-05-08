package org.wirez.client.lienzo.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.session.command.Session;

import javax.inject.Inject;

public class RemoveToolboxCommand extends org.wirez.core.client.canvas.controls.toolbox.command.RemoveToolboxCommand<Shape<?>> {

    @Inject
    public RemoveToolboxCommand( final CanvasCommandFactory commandFactory, 
                                 final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        super(commandFactory, canvasCommandManager, SVGUtils.createSVGIcon(SVGUtils.getRemoveIcon()) );
    }
    
}
