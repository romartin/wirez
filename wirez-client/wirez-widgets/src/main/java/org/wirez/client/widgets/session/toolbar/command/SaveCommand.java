package org.wirez.client.widgets.session.toolbar.command;

import com.google.gwt.user.client.Window;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.validation.canvas.CanvasValidationViolation;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class SaveCommand extends AbstractToolbarCommand<DefaultCanvasFullSession> {

    ClientDiagramServices clientDiagramServices;
    ValidateCommand validateCommand;

    @Inject
    public SaveCommand( final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                        final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                        final ClientDiagramServices clientDiagramServices,
                        final ValidateCommand validateCommand ) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
        this.clientDiagramServices = clientDiagramServices;
        this.validateCommand = validateCommand;
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
    public <T> void execute( final ToolbarCommandCallback<T> callback ) {

        validateCommand
                .initialize( session )
                .execute( new ToolbarCommandCallback<Iterable<CanvasValidationViolation>>() {

                    @Override
                    public void onSuccess( final Iterable<CanvasValidationViolation> result ) {

                        final Diagram d = session.getCanvasHandler().getDiagram();

                        if ( null != result ) {

                            // TODO: Throw event and refactor by the use of Notifications widget.
                            Window.alert( "Diagram save failed  [UUID=" + d.getUUID() + "]" );

                        } else {

                            clientDiagramServices.update( d, new ServiceCallback<Diagram>() {

                                @Override
                                public void onSuccess( final Diagram item ) {

                                    // TODO: Throw event and refactor by the use of Notifications widget.
                                    Window.alert( "Diagram saved successfully [UUID=" + item.getUUID() + "]" );

                                    if ( null != callback ) {

                                        callback.onSuccess( ( T ) item );
                                    }

                                }

                                @Override
                                public void onError( final ClientRuntimeError error ) {

                                    if ( null != callback ) {

                                        callback.onError( error );

                                    }

                                }
                            } );

                        }



                    }

                    @Override
                    public void onError( final ClientRuntimeError error ) {

                        if ( null != callback ) {

                            callback.onError( error );

                        }

                    }

                } );


    }

}
