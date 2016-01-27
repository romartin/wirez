package org.wirez.core.client.service;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.diagram.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClientDiagramServices {
    
    Caller<DiagramService> diagramService;

    @Inject
    public ClientDiagramServices(Caller<DiagramService> diagramService) {
        this.diagramService = diagramService;
    }
    
    public void create(final String defSetId,
                       final String shapeSetId,
                       final String title, final ServiceCallback<Diagram> callback) {
        
        diagramService.call(new RemoteCallback<DiagramServiceResponse>() {
            @Override
            public void callback(final DiagramServiceResponse response) {
                callback.onSuccess(response.getDiagram());
            }
        }, new ErrorCallback<Message>() {
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
            }
        }).create(new DiagramServiceCreateRequestImpl(defSetId, shapeSetId, title));
        
    }

    public void load(final String uuid,
                   final ServiceCallback<Diagram> callback) {

        diagramService.call(new RemoteCallback<DiagramServiceResponse>() {
            @Override
            public void callback(final DiagramServiceResponse response) {
                callback.onSuccess(response.getDiagram());
            }
        }, new ErrorCallback<Message>() {
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
            }
        }).load(new DiagramServiceLoadRequestImpl(uuid));

    }

    public void save(final Diagram diagram,
                    final ServiceCallback<Diagram> callback) {

        diagramService.call(new RemoteCallback<Void>() {
            @Override
            public void callback(final Void aVoid) {
                callback.onSuccess(diagram);
            }
        }, new ErrorCallback<Message>() {
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
            }
        }).save(new DiagramServiceSaveRequestImpl(diagram));

    }

    public void delete(final String uuid,
                     final ServiceCallback<Diagram> callback) {

        diagramService.call(new RemoteCallback<Diagram>() {
            @Override
            public void callback(final Diagram diagram) {
                callback.onSuccess(diagram);
            }
        }, new ErrorCallback<Message>() {
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError(new ClientRuntimeError(throwable));
                return false;
            }
        }).delete(new DiagramServiceDeleteRequestImpl(uuid));

    }
    
}
