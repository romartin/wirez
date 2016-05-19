package org.wirez.client.widgets.session.toolbar.command;

import com.google.gwt.user.client.Window;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class SaveCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    ClientDiagramServices clientDiagramServices;

    @Inject
    public SaveCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                       final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                       final ClientDiagramServices clientDiagramServices) {
        super( enableToolbarCommandEvent ,disableToolbarCommandEvent );
        this.clientDiagramServices = clientDiagramServices;
    }

    @Override
    public IconType getIcon() {
        return IconType.SAVE;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Save the diagram";
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        clientDiagramServices.update(session.getCanvasHandler().getDiagram(), new ServiceCallback<Diagram>() {
            @Override
            public void onSuccess(final Diagram item) {
                Window.alert("Diagram saved successfully [UUID=" + item.getUUID() + "]");

                if (null != callback) {
                    
                    callback.onSuccess((T) item);
                }
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                if (null != callback) {
                    callback.onError( error );
                }
                
            }
        });
        
    }
    
}
