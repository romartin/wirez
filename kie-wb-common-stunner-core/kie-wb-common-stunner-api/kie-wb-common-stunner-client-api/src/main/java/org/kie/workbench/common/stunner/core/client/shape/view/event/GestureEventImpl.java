package org.kie.workbench.common.stunner.core.client.shape.view.event;

public final class GestureEventImpl extends AbstractViewEvent 
        implements GestureEvent {
    
    private final double scale;
    private final double rotation;
    
    public GestureEventImpl(final double scale,
                            final double rotation ) {
        this.scale = scale;
        this.rotation = rotation;
        
    }

    public double getScale() {
        return scale;
    }

    public double getRotation() {
        return rotation;
    }
    
}
