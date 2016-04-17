package org.wirez.core.client.service;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.AbstractDiagramManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.lookup.LookupManager;
import org.wirez.core.api.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.api.lookup.diagram.DiagramRepresentation;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.registry.List;
import org.wirez.core.api.remote.DiagramLookupService;
import org.wirez.core.api.remote.DiagramService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Provides the client side and remote caller for the diagram manager and services.
 * If the requested diagram is not present on client side, it will use the manager's service 
 * in order to perform the operation for it. 
 */
@ApplicationScoped
public class ClientDiagramServices extends AbstractDiagramManager<Diagram> {

    SyncBeanManager beanManager;
    DefinitionManager definitionManager;
    Caller<DiagramService> diagramServiceCaller;
    Caller<DiagramLookupService> diagramLookupServiceCaller;
    
    protected ClientDiagramServices() {

    }

    @Inject
    public ClientDiagramServices(final SyncBeanManager beanManager,
                                 final DefinitionManager definitionManager,
                                 final Caller<DiagramService> diagramServiceCaller,
                                 final Caller<DiagramLookupService> diagramLookupServiceCaller,
                                 final @List DiagramRegistry<Diagram> registry) {
        super( registry );
        this.beanManager = beanManager;
        this.definitionManager = definitionManager;
        this.diagramServiceCaller = diagramServiceCaller;
        this.diagramLookupServiceCaller = diagramLookupServiceCaller;
    }

    @PostConstruct
    public void init() {

    }

    public void update(final Diagram diagram, final ServiceCallback<Diagram> callback ) {

        if ( registry.contains( diagram ) ) {
            
            registry.update( diagram );
            
            diagramServiceCaller.call(v -> callback.onSuccess (diagram ), (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; }).saveOrUpdate( diagram );
            
        } else {
            
            add( diagram, callback );
            
        }
        
    }

    public void add(final Diagram diagram, final ServiceCallback<Diagram> callback ) {

        if ( !registry.contains( diagram ) ) {

            registry.add( diagram );

            diagramServiceCaller.call(v -> callback.onSuccess (diagram ), (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; } ).saveOrUpdate( diagram );
            
        }

    }

    public void get(final String uuid, final ServiceCallback<Diagram> callback ) {

        final Diagram registryDiagram = registry.get( uuid );
        
        if ( null != registryDiagram ) {

            callback.onSuccess( registryDiagram );

        } else {

            diagramServiceCaller.call(new RemoteCallback<Diagram>() {
                @Override
                public void callback(final Diagram diagram) {
                    
                    ClientDiagramServices.this.registry.add( diagram );
                    callback.onSuccess( diagram );
                }
            }, (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; }).get( uuid );

        }

    }
    
    public void lookup(final DiagramLookupRequest request, final ServiceCallback<LookupManager.LookupResponse<DiagramRepresentation>> callback ) {

        diagramLookupServiceCaller.call(new RemoteCallback<LookupManager.LookupResponse<DiagramRepresentation>>() {
            @Override
            public void callback(final LookupManager.LookupResponse<DiagramRepresentation> response) {

                callback.onSuccess( response );
            }
        }, (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; }).lookup( request );
        
    }
    
    
}
