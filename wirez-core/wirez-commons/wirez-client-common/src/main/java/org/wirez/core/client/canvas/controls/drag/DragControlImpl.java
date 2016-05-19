package org.wirez.core.client.canvas.controls.drag;

import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerRegistrationControl;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.event.ViewEventType;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DragControlImpl extends AbstractCanvasHandlerRegistrationControl 
        implements DragControl<AbstractCanvasHandler, Element> {

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    @Inject
    public DragControlImpl(final CanvasCommandFactory canvasCommandFactory,
                           final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @Override
    public void register(final Element element) {

        final Shape shape = canvasHandler.getCanvas().getShape( element.getUUID() );
        
        if ( shape.getShapeView() instanceof HasEventHandlers) {
            
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();

            org.wirez.core.client.shape.view.event.DragHandler handler = new org.wirez.core.client.shape.view.event.DragHandler() {

                @Override
                public void handle(final org.wirez.core.client.shape.view.event.DragEvent event) {

                }

                @Override
                public void start(final org.wirez.core.client.shape.view.event.DragEvent event) {

                }

                @Override
                public void end(final org.wirez.core.client.shape.view.event.DragEvent event) {
                    final double[] xy = getContainerXY(shape);
                    CommandResult<CanvasViolation> result = canvasCommandManager.execute( canvasHandler, canvasCommandFactory.UPDATE_POSITION(element, xy[0], xy[1]) );
                    if (CommandUtils.isError( result) ) {
                        // TODO: DragContext#reset
                    }
                }
            };

            hasEventHandlers.addHandler(ViewEventType.DRAG, handler);
            
            registerHandler( element.getUUID(), handler );

        }
        
    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeView().getShapeX(),
                shape.getShapeView().getShapeY()};
    }

}
