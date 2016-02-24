package org.wirez.core.client.service;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.diagram.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientDiagramServices {
    
    Caller<DiagramService> diagramService;

    public ClientDiagramServices() {
    }

    @Inject
    public ClientDiagramServices(Caller<DiagramService> diagramService) {
        this.diagramService = diagramService;
    }

    public void search(final String query,
                       final ServiceCallback<Collection<DiagramRepresentation>> callback) {

        diagramService.call(new RemoteCallback<DiagramsServiceResponse>() {
            @Override
            public void callback(final DiagramsServiceResponse response) {
                callback.onSuccess(response.getDiagramRepresentations());
            }
        }, (message, throwable) -> {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
        }).search(new DiagramServiceSearchRequestImpl( query ));

    }
    
    public void create(final String defSetId,
                       final String shapeSetId,
                       final String title, final ServiceCallback<Diagram> callback) {
        
        diagramService.call(new RemoteCallback<DiagramServiceResponse>() {
            @Override
            public void callback(final DiagramServiceResponse response) {
                callback.onSuccess(response.getDiagram());
            }
        }, (message, throwable) -> {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
        }).create(new DiagramServiceCreateRequestImpl(defSetId, shapeSetId, title));
        
    }

    public void load(final String path,
                   final ServiceCallback<Diagram> callback) {

        diagramService.call(new RemoteCallback<DiagramServiceResponse>() {
            @Override
            public void callback(final DiagramServiceResponse response) {
                callback.onSuccess(response.getDiagram());
            }
        }, (message, throwable) -> {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
        }).load(new DiagramServiceLoadRequestImpl(path));

    }

    public void save(final Diagram diagram,
                    final ServiceCallback<Diagram> callback) {

        diagramService.call(aVoid -> callback.onSuccess(diagram), 
                (message, throwable) -> {
                    callback.onError(new ClientRuntimeError(throwable));
                    return false;
        }).save(new DiagramServiceSaveRequestImpl(diagram));

    }

    public void delete(final String uuid,
                     final ServiceCallback<Diagram> callback) {

        diagramService.call(new RemoteCallback<Diagram>() {
            @Override
            public void callback(final Diagram diagram) {
                callback.onSuccess(diagram);
            }
        }, (message, throwable) -> {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
        }).delete(new DiagramServiceDeleteRequestImpl(uuid));

    }
    
}
