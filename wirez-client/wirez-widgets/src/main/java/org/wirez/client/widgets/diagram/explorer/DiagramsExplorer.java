package org.wirez.client.widgets.diagram.explorer;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.diagram.explorer.item.DiagramExplorerItem;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.event.WidgetProcessingCompletedEvent;
import org.wirez.client.widgets.event.WidgetProcessingStartedEvent;
import org.wirez.core.lookup.LookupManager;
import org.wirez.core.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.lookup.diagram.DiagramLookupRequestImpl;
import org.wirez.core.lookup.diagram.DiagramRepresentation;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.WirezClientLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class DiagramsExplorer implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(DiagramsExplorer.class.getName());
    
    public interface View extends UberView<DiagramsExplorer> {

        View showEmpty();

        View add(IsWidget diagramRepresentationItem);
        
        View clear();
        
    }
    
    public interface ClickCallback {
        
        void onClick( String uuid );
        
    }

    ClientDiagramServices clientDiagramServices;
    Instance<DiagramExplorerItem> explorerItemInstances;
    Event<LoadDiagramEvent> loadDiagramEventEvent;
    Event<WidgetProcessingStartedEvent> widgetProcessingStartedEvent;
    Event<WidgetProcessingCompletedEvent> widgetProcessingCompletedEventEvent;
    View view;

    private final List<DiagramExplorerItem> items = new LinkedList<>();
    private ClickCallback clickCallback;
    
    @Inject
    public DiagramsExplorer(final ClientDiagramServices clientDiagramServices, 
                            final Instance<DiagramExplorerItem> explorerItemInstances,
                            final Event<LoadDiagramEvent> loadDiagramEventEvent, 
                            final Event<WidgetProcessingStartedEvent> widgetProcessingStartedEvent,
                            final Event<WidgetProcessingCompletedEvent> widgetProcessingCompletedEventEvent,
                            final View view) {
        this.clientDiagramServices = clientDiagramServices;
        this.explorerItemInstances = explorerItemInstances;
        this.loadDiagramEventEvent = loadDiagramEventEvent;
        this.widgetProcessingStartedEvent = widgetProcessingStartedEvent;
        this.widgetProcessingCompletedEventEvent = widgetProcessingCompletedEventEvent;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    public void show( final ClickCallback clickCallback) {
        this.clickCallback = clickCallback;

        // Notify some processing starts.
        fireProcessingStarted();
        
        clear();
        
        final DiagramLookupRequest request = new DiagramLookupRequestImpl.Builder().build();
        
        clientDiagramServices.lookup( request, new ServiceCallback<LookupManager.LookupResponse<DiagramRepresentation>>() {
            @Override
            public void onSuccess(final LookupManager.LookupResponse<DiagramRepresentation> response) {

                final List<DiagramRepresentation> items = response.getResults();

                if ( null == items || items.isEmpty() ) {

                    view.showEmpty();

                } else {

                    for (final DiagramRepresentation diagram : items) {

                        addEntry( diagram );
                    }

                }

                // Notify some processing ends.
                fireProcessingCompleted();

            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError( error );
            }
        });
        
    }
    
    public void clear() {
        items.clear();
        view.clear();
    }

    private void addEntry(final DiagramRepresentation diagramRepresentation) {
        final DiagramExplorerItem item = explorerItemInstances.get();
        view.add( item.asWidget() );
        items.add( item );
        item.show(diagramRepresentation, () -> {
            if ( null != clickCallback ) {
                final String itemUUID = diagramRepresentation.getUUID();
                setItemActive( itemUUID );
                clickCallback.onClick( itemUUID );
            }
        });
    }
    
    private void setItemActive( final String itemUUID ) {
        for ( final DiagramExplorerItem item : items ) {
            item.setActive( item.getDiagramUUID().equals( itemUUID ) );
        }
    }

    private void fireProcessingStarted() {
        widgetProcessingStartedEvent.fire( new WidgetProcessingStartedEvent() );
    }

    private void fireProcessingCompleted() {
        widgetProcessingCompletedEventEvent.fire( new WidgetProcessingCompletedEvent() );
    }

    private void showError( final ClientRuntimeError error ) {
        fireProcessingCompleted();
        final String message = WirezClientLogger.getErrorMessage(error);
        log( Level.SEVERE, message);
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
