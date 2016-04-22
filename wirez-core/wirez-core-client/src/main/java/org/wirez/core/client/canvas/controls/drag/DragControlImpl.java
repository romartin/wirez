package org.wirez.core.client.canvas.controls.drag;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerRegistrationControl;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.impl.AbstractShape;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.event.DragHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class DragControlImpl extends AbstractCanvasHandlerRegistrationControl 
        implements DragControl<AbstractCanvasHandler, Element> {

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    private final Map<String, DragHandler> handlers = new HashMap<>();

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
            
            handlers.put( element.getUUID(), handler );

        }
        
    }

    @Override
    public void disable() {
        
        // De-register all drag handlers.
        for( Map.Entry<String, org.wirez.core.client.shape.view.event.DragHandler> entry : handlers.entrySet() ) {
            final String uuid = entry.getKey();
            final AbstractShape shape = (AbstractShape) canvasHandler.getCanvas().getShape( uuid );
            final org.wirez.core.client.shape.view.event.DragHandler handler = entry.getValue();
            doDeregister(shape, handler);
        }

    }

    @Override
    public void deregister(final Element element) {
        final AbstractShape shape = (AbstractShape) canvasHandler.getCanvas().getShape( element.getUUID() );
        org.wirez.core.client.shape.view.event.DragHandler handler = handlers.get( element.getUUID() );
        doDeregister(shape, handler);

    }

    protected void doDeregister(final AbstractShape shape,
                                final org.wirez.core.client.shape.view.event.DragHandler handler) {

        if (null != handler) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            hasEventHandlers.removeHandler(handler);
        }

    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeView().getShapeX(),
                shape.getShapeView().getShapeY()};
    }
    
}
