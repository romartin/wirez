package org.wirez.client.lienzo.canvas.controls.toolbox.command.actions;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.toolbox.command.actions.RemoveToolboxCommand;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.SessionCommandManager;

import javax.inject.Inject;

public class LienzoRemoveToolboxCommand extends RemoveToolboxCommand<Shape<?>> {

    @Inject
    public LienzoRemoveToolboxCommand(final CanvasCommandFactory commandFactory,
                                      final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {

        super( commandFactory, canvasCommandManager, SVGUtils.createSVGIcon( SVGUtils.getTrashIcon() ) );

    }
    
}
