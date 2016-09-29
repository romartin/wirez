package org.kie.workbench.common.stunner.core.client.canvas.listener;

public interface HasCanvasListeners<L extends CanvasListener> {

    HasCanvasListeners<L> addRegistrationListener( L instance );

    HasCanvasListeners<L> removeRegistrationListener( L instance );

    HasCanvasListeners<L> clearRegistrationListeners();

}
