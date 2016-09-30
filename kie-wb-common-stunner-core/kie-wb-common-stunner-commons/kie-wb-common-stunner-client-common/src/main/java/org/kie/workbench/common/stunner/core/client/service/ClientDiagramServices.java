package org.kie.workbench.common.stunner.core.client.service;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.lookup.LookupManager;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupRequest;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramRepresentation;
import org.kie.workbench.common.stunner.core.remote.DiagramLookupService;
import org.kie.workbench.common.stunner.core.remote.DiagramService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * A wrapper util class for handling different diagram services from client side.
 */
@ApplicationScoped
public class ClientDiagramServices {

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
                                 final Caller<DiagramLookupService> diagramLookupServiceCaller) {
        this.beanManager = beanManager;
        this.definitionManager = definitionManager;
        this.diagramServiceCaller = diagramServiceCaller;
        this.diagramLookupServiceCaller = diagramLookupServiceCaller;
    }

    @PostConstruct
    public void init() {

    }

    public void update(final Diagram diagram, final ServiceCallback<Diagram> callback ) {

        diagramServiceCaller.call(v -> callback.onSuccess (diagram ), (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; }).saveOrUpdate( diagram );
        
    }

    public void add(final Diagram diagram, final ServiceCallback<Diagram> callback ) {

        diagramServiceCaller.call(v -> callback.onSuccess (diagram ), (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; } ).saveOrUpdate( diagram );

    }

    public void get(final String uuid, final ServiceCallback<Diagram> callback ) {

        diagramServiceCaller.call(new RemoteCallback<Diagram>() {
            
            @Override
            public void callback(final Diagram diagram) {
                callback.onSuccess( diagram );
            }
            
        }, (message, throwable) -> { callback.onError( new ClientRuntimeError(throwable)); return false; }).getDiagramByUUID( uuid );

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
