package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.toolbox.command.actions;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.client.lienzo.util.SVGUtils;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.actions.RemoveToolboxCommand;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.Session;

import javax.inject.Inject;

public class LienzoRemoveToolboxCommand extends RemoveToolboxCommand<Shape<?>> {

    @Inject
    public LienzoRemoveToolboxCommand(final CanvasCommandFactory commandFactory,
                                      final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {

        super( commandFactory, canvasCommandManager, SVGUtils.createSVGIcon( SVGUtils.getTrashIcon() ) );

    }
    
}
