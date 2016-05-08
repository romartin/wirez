package org.wirez.core.client.service;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.AbstractFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.api.remote.FactoryService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Provides the client side and remote caller for the factory manager and services.
 * If the requested factory is present on client side, it will create the object using it.
 * If the requested factory is not present on client side, whether it's not implemented or the object 
 * cannot be created on client, it performs the service calls to the factory service. 
 */
@ApplicationScoped
public class ClientFactoryServices extends AbstractFactoryManager {

    SyncBeanManager beanManager;
    Caller<FactoryService> factoryServiceCaller;

    protected ClientFactoryServices() {
        super(null);
    }

    @Inject
    public ClientFactoryServices(final SyncBeanManager beanManager,
                                 final DefinitionManager definitionManager,
                                 final Caller<FactoryService> factoryServiceCaller) {
        super( definitionManager );
        this.beanManager = beanManager;
        this.factoryServiceCaller = factoryServiceCaller;
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

    public <W> void newDomainObject(final String id, final ServiceCallback<W> callback ) {

        W result = super.newDomainObject( id );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryServiceCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, (message, throwable) -> false).newDomainObject( id );
        }
        
    }

    public <W extends Graph> void newGraph(String uuid, String definitionSetId, final ServiceCallback<W> callback) {

        W result = super.newGraph( uuid, definitionSetId );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryServiceCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, (message, throwable) -> false).newGraph( uuid, definitionSetId );
        }

    }
    
    public <W extends Element> void newElement(String uuid, String id, final ServiceCallback<W> callback) {

        W result = super.newElement( uuid, id );
        if ( null != result ) {
            callback.onSuccess( result );
        } else {
            factoryServiceCaller.call(new RemoteCallback<W>() {
                @Override
                public void callback(final W w) {
                    callback.onSuccess(w);
                }
            }, (message, throwable) -> false).newElement( uuid, id );
        }
        
    }

    @Override
    public <G extends Graph, S extends Settings> Diagram<G, S> newDiagram(String uuid, G graph, S settings) {
        return (Diagram<G, S>) new DiagramImpl( uuid, graph, settings );
    }

    @Override
    protected ElementFactory getElementFactory(final Object definition,
                                                   final Class<?> graphElementClass,
                                                   final String factory) {
        return getFactory( definition, graphElementClass, factory );
    }

    @Override
    protected GraphFactory getGraphFactory(final Object definition,
                                             final Class<?> graphElementClass,
                                             final String factory) {
        return getFactory( definition, graphElementClass, factory );
    }

    @SuppressWarnings("unchecked")
    protected <T> T getFactory(final Object definition,
                                               final Class<?> graphElementClass,
                                               final String factory) {

        String ref = getFactoryReference( graphElementClass, factory );

        // DefinitionSet client adapters.
        Collection<SyncBeanDef> graphFactories = beanManager.lookupBeans(ref);

        // If no beans found, no problem, then try on backend.
        if ( graphFactories.isEmpty() ) {
            return null;
        }
        
        // If more that one bean, throw error.
        if ( graphFactories.size() > 1 ) {
            throw new RuntimeException(" More than one bean matches for graph element factory with name [" + ref + "]");
        }


        return (T) graphFactories.iterator().next().getInstance();
    }
    
}
