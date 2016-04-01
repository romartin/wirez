package org.wirez.core.client.service;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.BaseFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.factory.ElementFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class ClientFactoryServices extends BaseFactoryManager {

    SyncBeanManager beanManager;
    Caller<FactoryManager> factoryManagerCaller;

    protected ClientFactoryServices() {
        super(null);
    }

    @Inject
    public ClientFactoryServices(final SyncBeanManager beanManager,
                                 final DefinitionManager definitionManager,
                                 final Caller<FactoryManager> factoryManagerCaller) {
        super( definitionManager );
        this.beanManager = beanManager;
        this.factoryManagerCaller = factoryManagerCaller;
    }

    @PostConstruct
    public void init() {

        // Client side model builders.
        Collection<SyncBeanDef<ModelFactory>> modelBuilderDefs = beanManager.lookupBeans(ModelFactory.class);
        for (SyncBeanDef<ModelFactory> modelBuilder : modelBuilderDefs) {
            ModelFactory modelBuilderObject = modelBuilder.getInstance();
            modelFactories.add(modelBuilderObject);
        }

    }

    public <W> void model( final String id, final ServiceCallback<W> callback ) {

        W result = super.model( id );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryManagerCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, new ErrorCallback<Message>() {
                @Override
                public boolean error(Message message, Throwable throwable) {
                    return false;
                }
            }).model( id );
        }
        
    }

    public <W extends Graph> void graph(String uuid, String id, final ServiceCallback<W> callback) {

        W result = super.element( uuid, id );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryManagerCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, new ErrorCallback<Message>() {
                @Override
                public boolean error(Message message, Throwable throwable) {
                    return false;
                }
            }).graph( uuid, id );
        }

    }
    
    public <W extends Element> void element(String uuid, String id, final ServiceCallback<W> callback) {

        W result = super.element( uuid, id );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryManagerCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, new ErrorCallback<Message>() {
                @Override
                public boolean error(Message message, Throwable throwable) {
                    return false;
                }
            }).element( uuid, id );
        }
        
    }

    @Override
    protected ElementFactory getElementFactory(final Object definition,
                                                   final Class<?> graphElementClass,
                                                   final String factory) {

        String ref = getFactoryReference( graphElementClass, factory );

        // DefinitionSet client adapters.
        Collection<SyncBeanDef> graphFactories = beanManager.lookupBeans(ref);
        
        if ( graphFactories.size() == 0 ) {
            throw new RuntimeException(" No beans for graph element factory with name [" + ref + "]");
        } else  if ( graphFactories.size() > 1 ) {
            throw new RuntimeException(" More than one bean matches for graph element factory with name [" + ref + "]");
        }
        
        
        return (ElementFactory) graphFactories.iterator().next().getInstance();
    }
    
}
