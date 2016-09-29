package org.kie.workbench.common.stunner.core.client.canvas.controls;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.view.HasEventHandlers;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCanvasRegistrationControl extends AbstractCanvasControl 
        implements CanvasRegistationControl<AbstractCanvas, Shape> {

    private final Map<String, ViewHandler<?>> handlers = new HashMap<>();

    protected void registerHandler( String uuid, ViewHandler<?> handler ) {
        handlers.put( uuid, handler );
    }

    @Override
    protected void doDisable() {
        
        // De-register all drag handlers.
        for( Map.Entry<String, ViewHandler<?>> entry : handlers.entrySet() ) {
            final String uuid = entry.getKey();
            final Shape shape = canvas.getShape( uuid );
            final ViewHandler<?> handler = entry.getValue();
            doDeregister(shape, handler);
        }
        
    }

    @Override
    public void deregister( final Shape shape ) {

        if ( null != shape ) {
            ViewHandler<?> handler = handlers.get( shape.getUUID() );
            doDeregister(shape, handler);
        }

    }

    protected void doDeregister(final Shape shape,
                                final ViewHandler<?> handler) {

        if (null != handler) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            hasEventHandlers.removeHandler(handler);
        }

    }
    
}
