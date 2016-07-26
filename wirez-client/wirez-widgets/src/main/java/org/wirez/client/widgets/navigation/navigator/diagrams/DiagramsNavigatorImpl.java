package org.wirez.client.widgets.navigation.navigator.diagrams;

import com.google.gwt.dom.client.Style;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.event.WidgetProcessingCompletedEvent;
import org.wirez.client.widgets.event.WidgetProcessingStartedEvent;
import org.wirez.client.widgets.navigation.navigator.Navigator;
import org.wirez.client.widgets.navigation.navigator.NavigatorItem;
import org.wirez.client.widgets.navigation.navigator.NavigatorView;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.WirezClientLogger;
import org.wirez.core.lookup.LookupManager;
import org.wirez.core.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.lookup.diagram.DiagramLookupRequestImpl;
import org.wirez.core.lookup.diagram.DiagramRepresentation;

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
    Event<WidgetProcessingStartedEvent> widgetProcessingStartedEvent;
    Event<WidgetProcessingCompletedEvent> widgetProcessingCompletedEventEvent;
    NavigatorView<?> view;

    private final List<NavigatorItem<DiagramRepresentation>> items = new LinkedList<>();
    private int width;
    private int height;
    private Style.Unit unit;

    @Inject
    public DiagramsNavigatorImpl( final ClientDiagramServices clientDiagramServices,
                                  final Instance<DiagramNavigatorItem> navigatorItemInstances,
                                  final Event<LoadDiagramEvent> loadDiagramEventEvent,
                                  final Event<WidgetProcessingStartedEvent> widgetProcessingStartedEvent,
                                  final Event<WidgetProcessingCompletedEvent> widgetProcessingCompletedEventEvent,
                                  final NavigatorView<?> view ) {
        this.clientDiagramServices = clientDiagramServices;
        this.navigatorItemInstances = navigatorItemInstances;
        this.loadDiagramEventEvent = loadDiagramEventEvent;
        this.widgetProcessingStartedEvent = widgetProcessingStartedEvent;
        this.widgetProcessingCompletedEventEvent = widgetProcessingCompletedEventEvent;
        this.view = view;
        this.width = 140;
        this.height = 140;
        this.unit = Style.Unit.PX;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public Navigator<DiagramRepresentation> setItemSize( int width, int height, Style.Unit unit ) {
        this.width = width;
        this.height = height;
        this.unit = unit;

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
        return null;
    }

    private void addEntry( final DiagramRepresentation diagramRepresentation ) {
        final DiagramNavigatorItem item = navigatorItemInstances.get();
        view.add( item.getView() );
        items.add( item );
        item.show( diagramRepresentation,
                width,
                height,
                unit,
                () -> {
            final String itemUUID = diagramRepresentation.getUUID();
            fireLoadDiagram( itemUUID );
        } );
    }

    private void fireLoadDiagram( final String uuid ) {
        loadDiagramEventEvent.fire( new LoadDiagramEvent( uuid ) );
    }

    private void fireProcessingStarted() {
        widgetProcessingStartedEvent.fire( new WidgetProcessingStartedEvent() );
    }

    private void fireProcessingCompleted() {
        widgetProcessingCompletedEventEvent.fire( new WidgetProcessingCompletedEvent() );
    }

    private void showError( final ClientRuntimeError error ) {
        final String message = WirezClientLogger.getErrorMessage( error );
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
