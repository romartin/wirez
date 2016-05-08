package org.wirez.client.lienzo.canvas.controls.pan;

import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.IEventFilter;
import com.ait.lienzo.client.core.mediator.MousePanMediator;
import org.wirez.client.lienzo.canvas.controls.AbstractMediatorControl;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.controls.pan.PanControl;

import javax.enterprise.context.Dependent;

@Dependent
public class PanControlImpl extends AbstractMediatorControl<MousePanMediator> implements PanControl<AbstractCanvas> {

    private final IEventFilter[] filters = new IEventFilter[] { EventFilter.ALT };
    
    @Override
    protected MousePanMediator buildMediator() {
        return new MousePanMediator( filters );
    }
    
}
