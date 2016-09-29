package org.kie.workbench.common.stunner.client.lienzo.canvas.controls;

import com.ait.lienzo.client.core.mediator.IMediator;
import com.ait.lienzo.client.core.mediator.Mediators;
import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.controls.AbstractCanvasControl;

public abstract class AbstractMediatorControl<M extends IMediator> extends AbstractCanvasControl {

    protected abstract M buildMediator();

    protected Mediators mediators;
    protected M mediator;
    
    @Override
    public void enable(final AbstractCanvas canvas) {
        super.enable(canvas);
        
        this.mediator = buildMediator();
        final LienzoLayer lienzoLayer = (LienzoLayer) canvas.getLayer();
        mediators = lienzoLayer.getLienzoLayer().getViewport().getMediators();
        mediators.push( getMediator() );
    }

    @Override
    protected void doDisable() {
        mediators.remove( getMediator() );
    }

    public M getMediator() {
        return mediator;
    }
    
}
