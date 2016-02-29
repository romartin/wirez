package org.wirez.core.client;

import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.BaseDefinitionManager;
import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.factory.ModelBuilder;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.service.definition.DefinitionServiceResponse;
import org.wirez.core.api.service.definition.DefinitionSetServiceResponse;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class ClientDefinitionManager extends BaseDefinitionManager {
    
    SyncBeanManager beanManager;
    ClientDefinitionServices clientDefinitionServices;

    // Remove later...
    public static ClientDefinitionManager get() {
        Collection<SyncBeanDef<ClientDefinitionManager>> beans = IOC.getBeanManager().lookupBeans(ClientDefinitionManager.class);
        SyncBeanDef<ClientDefinitionManager> beanDef = beans.iterator().next();
        return beanDef.getInstance();
    }

    public ClientDefinitionManager() {
    }

    @Inject
    public ClientDefinitionManager(final DiagramRegistry diagramRegistry,
                                   final SyncBeanManager beanManager,
                                   final ClientDefinitionServices clientDefinitionServices) {
        super(diagramRegistry);
        this.beanManager = beanManager;
        this.clientDefinitionServices = clientDefinitionServices;
    }
    
    @PostConstruct
    public void init() {

        // Client side model builders.
        Collection<SyncBeanDef<ModelBuilder>> modelBuilderDefs = beanManager.lookupBeans(ModelBuilder.class);
        for (SyncBeanDef<ModelBuilder> modelBuilder : modelBuilderDefs) {
            ModelBuilder modelBuilderObject = modelBuilder.getInstance();
            modelFactories.add(modelBuilderObject);
        }
        
        // DefinitionSet client adapters.
        Collection<SyncBeanDef<DefinitionSetAdapter>> beanDefSetAdapters = beanManager.lookupBeans(DefinitionSetAdapter.class);
        for (SyncBeanDef<DefinitionSetAdapter> defSet : beanDefSetAdapters) {
            DefinitionSetAdapter definitionSet = defSet.getInstance();
            definitionSetAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);

        // DefinitionSetRule client adapters.
        Collection<SyncBeanDef<DefinitionSetRuleAdapter>> beanDefSetRuleAdapters = beanManager.lookupBeans(DefinitionSetRuleAdapter.class);
        for (SyncBeanDef<DefinitionSetRuleAdapter> defSet : beanDefSetRuleAdapters) {
            DefinitionSetRuleAdapter definitionSet = defSet.getInstance();
            definitionSetRuleAdapters.add(definitionSet);
        }
        sortAdapters(definitionSetRuleAdapters);

        // Definition client adapters.
        Collection<SyncBeanDef<DefinitionAdapter>> beanDefAdapters = beanManager.lookupBeans(DefinitionAdapter.class);
        for (SyncBeanDef<DefinitionAdapter> defSet : beanDefAdapters) {
            DefinitionAdapter definitionSet = defSet.getInstance();
            definitionAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);
        
        // PropertySet client adapters.
        Collection<SyncBeanDef<PropertySetAdapter>> beanPropSetAdapters = beanManager.lookupBeans(PropertySetAdapter.class);
        for (SyncBeanDef<PropertySetAdapter> defSet : beanPropSetAdapters) {
            PropertySetAdapter definitionSet = defSet.getInstance();
            propertySetAdapters.add(definitionSet);
        }
        sortAdapters(propertySetAdapters);
        
        // Property client adapters.
        Collection<SyncBeanDef<PropertyAdapter>> beanPropAdapters = beanManager.lookupBeans(PropertyAdapter.class);
        for (SyncBeanDef<PropertyAdapter> defSet : beanPropAdapters) {
            PropertyAdapter definitionSet = defSet.getInstance();
            propertyAdapters.add(definitionSet);
        }
        sortAdapters(propertyAdapters);
        
    }

    /*
        ****************************************************************************************************
        * ADAPTORS SHORTCUTS
        * TODO: Improve performance ( caching, refactoring, etc)
        * **************************************************************************************************
     */
    
    public void getPropertySets(final DefinitionSet definitionSet,
                                final ServiceCallback<Set<PropertySet>> callback) {
        
        DefinitionSetAdapter definitionSetAdapter = getDefinitionSetAdapter(definitionSet);
        
        if ( null != definitionSetAdapter ) {
            callback.onSuccess(definitionSetAdapter.getPropertySets(definitionSet));
        } else {
            getDefinitionSetResponse(definitionSet.getId(), new ServiceCallback<DefinitionSetServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionSetServiceResponse item) {
                    callback.onSuccess(item.getPropertySets());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
        }
        
    }

    public void getDefinitions(final DefinitionSet definitionSet,
                               final ServiceCallback<Set<Definition>> callback) {

        DefinitionSetAdapter definitionSetAdapter = getDefinitionSetAdapter(definitionSet);

        if ( null != definitionSetAdapter ) {
            callback.onSuccess(definitionSetAdapter.getPropertySets(definitionSet));
        } else {
            getDefinitionSetResponse(definitionSet.getId(), new ServiceCallback<DefinitionSetServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionSetServiceResponse item) {
                    callback.onSuccess(item.getDefinitions());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
        }

    }

    public void getRules(final DefinitionSet definitionSet,
                                final ServiceCallback<Collection<Rule>> callback) {

        DefinitionSetRuleAdapter definitionSetAdapter = getDefinitionSetRuleAdapter(definitionSet);

        if ( null != definitionSetAdapter ) {
            
            callback.onSuccess(definitionSetAdapter.getRules(definitionSet));
            
        } else {
            
            getDefinitionSetResponse(definitionSet.getId(), new ServiceCallback<DefinitionSetServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionSetServiceResponse item) {
                    callback.onSuccess(item.getRules());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
            
        }

    }

    public void getPropertySets(final Definition definition,
                                final ServiceCallback<Set<PropertySet>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getPropertySets(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ServiceCallback<DefinitionServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionServiceResponse item) {
                    callback.onSuccess(item.getPropertySets().keySet());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
        }

    }

    public void getPropertyValues(final Definition definition,
                                final ServiceCallback<Map<Property, Object>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getPropertiesValues(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ServiceCallback<DefinitionServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionServiceResponse item) {
                    callback.onSuccess(item.getProperties());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
        }

    }

    public void getProperties(final Definition definition,
                              final ServiceCallback<Set<Property>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getProperties(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ServiceCallback<DefinitionServiceResponse>() {

                @Override
                public void onSuccess(final DefinitionServiceResponse item) {
                    callback.onSuccess(item.getProperties().keySet());
                }

                @Override
                public void onError(final ClientRuntimeError error) {
                    callback.onError(error);
                }
            });
        }

    }

    public void getProperties(final PropertySet propertySet,
                              final ServiceCallback<Set<Property>> callback) {

        PropertySetAdapter definitionAdapter = getPropertySetAdapter(propertySet);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getProperties(propertySet));
        }  else {
            // TODO
        }

    }
    
    
    protected void getDefinitionSetResponse(final String id,
                                            final ServiceCallback<DefinitionSetServiceResponse> callback) {
        clientDefinitionServices.getDefinitionSetResponse(id, callback);
    }

    protected void getDefinitionResponse(final String id,
                                         final ServiceCallback<DefinitionServiceResponse> callback) {
        clientDefinitionServices.getDefinitionResponse(id, callback);
    }

}
