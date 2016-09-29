package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request;

import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.BuildRequest;

public abstract class AbstractBuildRequest implements BuildRequest {
    
    private final double x;
    private final double y;

    public AbstractBuildRequest(final double x, 
                                final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
    
}
