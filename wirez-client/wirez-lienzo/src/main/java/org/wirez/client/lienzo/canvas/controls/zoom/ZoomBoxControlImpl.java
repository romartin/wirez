package org.wirez.client.lienzo.canvas.controls.zoom;

import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.IEventFilter;
import com.ait.lienzo.client.core.mediator.MouseBoxZoomMediator;
import org.wirez.client.lienzo.canvas.controls.AbstractMediatorControl;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.controls.zoom.Box;
import org.wirez.core.client.canvas.controls.zoom.ZoomControl;

import javax.enterprise.context.Dependent;

@Dependent
@Box
public class ZoomBoxControlImpl extends AbstractMediatorControl<MouseBoxZoomMediator> implements ZoomControl<AbstractCanvas> {

    private final IEventFilter[] filters = new IEventFilter[] { EventFilter.CONTROL };
    
    @Override
    protected MouseBoxZoomMediator buildMediator() {
        return new MouseBoxZoomMediator( filters );
    }

}
