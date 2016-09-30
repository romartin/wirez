package org.kie.workbench.common.stunner.client.widgets.navigation.navigator.diagrams;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.Widget;
import org.kie.workbench.common.stunner.client.widgets.event.LoadDiagramEvent;
import org.kie.workbench.common.stunner.client.widgets.navigation.navigator.NavigatorView;
import org.kie.workbench.common.stunner.client.widgets.navigation.navigator.Navigator;
import org.kie.workbench.common.stunner.client.widgets.navigation.navigator.NavigatorItem;
import org.kie.workbench.common.stunner.core.client.service.ClientDiagramServices;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.kie.workbench.common.stunner.core.client.util.StunnerClientLogger;
import org.kie.workbench.common.stunner.core.lookup.LookupManager;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupRequest;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupRequestImpl;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramRepresentation;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class DiagramsNavigatorImpl implements DiagramsNavigator {

    private static Logger LOGGER = Logger.getLogger( DiagramsNavigatorImpl.class.getName() );

    ClientDiagramServices clientDiagramServices;
    Instance<DiagramNavigatorItem> navigatorItemInstances;
    Event<LoadDiagramEvent> loadDiagramEventEvent;
    NavigatorView<?> view;

    private final List<NavigatorItem<DiagramRepresentation>> items = new LinkedList<>();
    private int width;
    private int height;

    @Inject
    public DiagramsNavigatorImpl( final ClientDiagramServices clientDiagramServices,
                                  final Instance<DiagramNavigatorItem> navigatorItemInstances,
                                  final Event<LoadDiagramEvent> loadDiagramEventEvent,
                                  final NavigatorView<?> view ) {
        this.clientDiagramServices = clientDiagramServices;
        this.navigatorItemInstances = navigatorItemInstances;
        this.loadDiagramEventEvent = loadDiagramEventEvent;
        this.view = view;
        this.width = 140;
        this.height = 140;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public Navigator<DiagramRepresentation> setItemPxSize( int width, int height ) {
        this.width = width;
        this.height = height;

        return this;
    }

    public DiagramsNavigatorImpl show() {

        // Notify some processing starts.
        fireProcessingStarted();

        clear();

        final DiagramLookupRequest request = new DiagramLookupRequestImpl.Builder().build();

        clientDiagramServices.lookup( request, new ServiceCallback<LookupManager.LookupResponse<DiagramRepresentation>>() {
            @Override
            public void onSuccess( final LookupManager.LookupResponse<DiagramRepresentation> response ) {

                final List<DiagramRepresentation> items = response.getResults();

                if ( null != items && !items.isEmpty() ) {

                    for ( final DiagramRepresentation diagram : items ) {

                        addEntry( diagram );
                    }

                }

                // Notify some processing ends.
                fireProcessingCompleted();

            }

            @Override
            public void onError( final ClientRuntimeError error ) {
                showError( error );
            }

        } );

        return this;
    }

    public DiagramsNavigatorImpl clear() {
        items.clear();
        view.clear();

        return this;
    }

    @Override
    public List<NavigatorItem<DiagramRepresentation>> getItems() {
        return items;
    }

    @Override
    public NavigatorView<?> getView() {
        return view;
    }

    private void addEntry( final DiagramRepresentation diagramRepresentation ) {
        final DiagramNavigatorItem item = navigatorItemInstances.get();
        view.add( item.getView() );
        items.add( item );
        item.show( diagramRepresentation,
                width,
                height,
                () -> {
            final String itemUUID = diagramRepresentation.getUUID();
            fireLoadDiagram( itemUUID );
        } );
    }

    private void fireLoadDiagram( final String uuid ) {
        loadDiagramEventEvent.fire( new LoadDiagramEvent( uuid ) );
    }

    private void fireProcessingStarted() {
        view.setLoading( true );
    }

    private void fireProcessingCompleted() {
        view.setLoading( false );
    }

    private void showError( final ClientRuntimeError error ) {
        final String message = StunnerClientLogger.getErrorMessage( error );
        showError( message );
    }

    private void showError( final String error ) {
        fireProcessingCompleted();
        log( Level.SEVERE, error );
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
