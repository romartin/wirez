package org.wirez.client.lienzo.canvas.controls.zoom;

import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.IEventFilter;
import com.ait.lienzo.client.core.mediator.MouseSwipeZoomMediator;
import org.wirez.client.lienzo.canvas.controls.AbstractMediatorControl;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.controls.zoom.Swipe;
import org.wirez.core.client.canvas.controls.zoom.ZoomControl;

import javax.enterprise.context.Dependent;

@Dependent
@Swipe
public class ZoomSwipeControlImpl extends AbstractMediatorControl<MouseSwipeZoomMediator> implements ZoomControl<AbstractCanvas> {

    private final IEventFilter[] filters = new IEventFilter[] { EventFilter.CONTROL };
    
    @Override
    protected MouseSwipeZoomMediator buildMediator() {
        return new MouseSwipeZoomMediator( filters );
    }
    
}
