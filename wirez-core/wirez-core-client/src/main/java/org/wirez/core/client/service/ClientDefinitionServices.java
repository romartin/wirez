package org.wirez.core.client.service;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.service.definition.DefinitionServiceResponse;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.service.definition.DefinitionSetServiceResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClientDefinitionServices {
    
    Caller<DefinitionService> definitionService;

    public ClientDefinitionServices() {
    }

    @Inject
    public ClientDefinitionServices(Caller<DefinitionService> definitionService) {
        this.definitionService = definitionService;
    }

    public void getDefinitionSetResponse(final String id, final ServiceCallback<DefinitionSetServiceResponse> callback) {
        assert id != null;
        assert callback != null;
        
        definitionService.call(new RemoteCallback<DefinitionSetServiceResponse>() {
            @Override
            public void callback(final DefinitionSetServiceResponse definitionSetResponse) {
                callback.onSuccess(definitionSetResponse);
            }
        }, new ErrorCallback<Message>() {
            
            
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError( new ClientRuntimeError(message.toString(), throwable) );
                return false;
            }
            
        }).getDefinitionSet(id);
        
    }

    public void getDefinitionResponse(final String id, final ServiceCallback<DefinitionServiceResponse> callback) {
        assert id != null;
        assert callback != null;

        definitionService.call(new RemoteCallback<DefinitionServiceResponse>() {
            @Override
            public void callback(final DefinitionServiceResponse definitionSetResponse) {
                callback.onSuccess(definitionSetResponse);
            }
        }, new ErrorCallback<Message>() {


            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError( new ClientRuntimeError(message.toString(), throwable) );
                return false;
            }

        }).getDefinition(id);
        
    }
    
    public void buildGraphElement(final String uuid, final Definition definition, final ServiceCallback<Element> callback) {
        assert definition != null;
        assert callback != null;
        
        definitionService.call(new RemoteCallback<Element>() {
            
            @Override
            public void callback(final Element response) {
                callback.onSuccess( response );
            }
            
        }, new ErrorCallback<Message>() {
            
            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError( new ClientRuntimeError(message.toString(), throwable) );
                return false;
            }
            
        }).buildGraphElement(uuid, definition.getId());
    }

    public void getGraphElement(final DefinitionSet definitionSet, final ServiceCallback<Definition> callback) {
        assert definitionSet != null;
        assert callback != null;

        definitionService.call(new RemoteCallback<DefinitionSetServiceResponse>() {

            @Override
            public void callback(final DefinitionSetServiceResponse response) {

                callback.onSuccess( response.getGraphElement() );
            }

        }, new ErrorCallback<Message>() {

            @Override
            public boolean error(final Message message, final Throwable throwable) {
                callback.onError( new ClientRuntimeError(message.toString(), throwable) );
                return false;
            }

        }).getDefinitionSet(definitionSet.getId());
    }
    
}
