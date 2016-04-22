package org.wirez.core.client.canvas.controls.zoom;

import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.IEventFilter;
import com.ait.lienzo.client.core.mediator.MouseWheelZoomMediator;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.controls.AbstractMediatorControl;

import javax.enterprise.context.Dependent;

@Dependent
@Wheel
public class ZoomWheelControlImpl extends AbstractMediatorControl<MouseWheelZoomMediator> implements ZoomControl<AbstractCanvas> {

    private final IEventFilter[] filters = new IEventFilter[] { EventFilter.CONTROL };
    
    @Override
    protected MouseWheelZoomMediator buildMediator() {
        return new MouseWheelZoomMediator( filters );
    }
    
}
