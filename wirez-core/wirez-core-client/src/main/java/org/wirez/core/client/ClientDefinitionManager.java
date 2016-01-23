package org.wirez.core.client;

import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.BaseDefinitionManager;
import org.wirez.core.api.adapter.*;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.service.definition.DefinitionResponse;
import org.wirez.core.api.service.definition.DefinitionSetResponse;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class ClientDefinitionManager extends BaseDefinitionManager {
    
    SyncBeanManager beanManager;
    ClientDefinitionServices clientDefinitionServices;
    private Collection<DefinitionSet> definitionSets = new ArrayList<>();

    // Remove later...
    public static ClientDefinitionManager get() {
        Collection<IOCBeanDef<ClientDefinitionManager>> beans = IOC.getBeanManager().lookupBeans(ClientDefinitionManager.class);
        IOCBeanDef<ClientDefinitionManager> beanDef = beans.iterator().next();
        return beanDef.getInstance();
    }
    
    @Inject
    public ClientDefinitionManager(final SyncBeanManager beanManager,
                                   final ClientDefinitionServices clientDefinitionServices) {
        this.beanManager = beanManager;
        this.clientDefinitionServices = clientDefinitionServices;
    }
    
    @PostConstruct
    public void init() {

        // Definitions sets presents on client side.
        Collection<IOCBeanDef<DefinitionSet>> beanDefs = beanManager.lookupBeans(DefinitionSet.class);
        for (IOCBeanDef<DefinitionSet> defSet : beanDefs) {
            DefinitionSet definitionSet = defSet.getInstance();
            definitionSets.add(definitionSet);
        }
        
        // DefinitionSet client adapters.
        Collection<IOCBeanDef<DefinitionSetAdapter>> beanDefSetAdapters = beanManager.lookupBeans(DefinitionSetAdapter.class);
        for (IOCBeanDef<DefinitionSetAdapter> defSet : beanDefSetAdapters) {
            DefinitionSetAdapter definitionSet = defSet.getInstance();
            definitionSetAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);

        // DefinitionSetRule client adapters.
        Collection<IOCBeanDef<DefinitionSetRuleAdapter>> beanDefSetRuleAdapters = beanManager.lookupBeans(DefinitionSetRuleAdapter.class);
        for (IOCBeanDef<DefinitionSetRuleAdapter> defSet : beanDefSetRuleAdapters) {
            DefinitionSetRuleAdapter definitionSet = defSet.getInstance();
            definitionSetRuleAdapters.add(definitionSet);
        }
        sortAdapters(definitionSetRuleAdapters);

        // Definition client adapters.
        Collection<IOCBeanDef<DefinitionAdapter>> beanDefAdapters = beanManager.lookupBeans(DefinitionAdapter.class);
        for (IOCBeanDef<DefinitionAdapter> defSet : beanDefAdapters) {
            DefinitionAdapter definitionSet = defSet.getInstance();
            definitionAdapters.add(definitionSet);
        }
        sortAdapters(definitionAdapters);
        
        // PropertySet client adapters.
        Collection<IOCBeanDef<PropertySetAdapter>> beanPropSetAdapters = beanManager.lookupBeans(PropertySetAdapter.class);
        for (IOCBeanDef<PropertySetAdapter> defSet : beanPropSetAdapters) {
            PropertySetAdapter definitionSet = defSet.getInstance();
            propertySetAdapters.add(definitionSet);
        }
        sortAdapters(propertySetAdapters);
        
        // Property client adapters.
        Collection<IOCBeanDef<PropertyAdapter>> beanPropAdapters = beanManager.lookupBeans(PropertyAdapter.class);
        for (IOCBeanDef<PropertyAdapter> defSet : beanPropAdapters) {
            PropertyAdapter definitionSet = defSet.getInstance();
            propertyAdapters.add(definitionSet);
        }
        sortAdapters(propertyAdapters);
        
    }

    @Override
    public Collection<DefinitionSet> getDefinitionSets() {
        return definitionSets;
    }

    @Override
    public DefinitionSet getDefinitionSet(final String id) {
        if (null != id) {
            for (DefinitionSet definitionSet : definitionSets) {
                if (definitionSet.getId().equals(id)) {
                    return definitionSet;
                }
            }
        }
        return null;
    }
    
    /*
        ****************************************************************************************************
        * ADAPTORS SHORTCUTS
        * TODO: Improve performance ( caching, refactoring, etc)
        * **************************************************************************************************
     */
    
    public void getPropertySets(final DefinitionSet definitionSet,
                                final ClientDefinitionServices.ServiceCallback<Set<PropertySet>> callback) {
        
        DefinitionSetAdapter definitionSetAdapter = getDefinitionSetAdapter(definitionSet);
        
        if ( null != definitionSetAdapter ) {
            callback.onSuccess(definitionSetAdapter.getPropertySets(definitionSet));
        } else {
            getDefinitionSetResponse(definitionSet.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionSetResponse>() {

                @Override
                public void onSuccess(final DefinitionSetResponse item) {
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
                               final ClientDefinitionServices.ServiceCallback<Set<Definition>> callback) {

        DefinitionSetAdapter definitionSetAdapter = getDefinitionSetAdapter(definitionSet);

        if ( null != definitionSetAdapter ) {
            callback.onSuccess(definitionSetAdapter.getPropertySets(definitionSet));
        } else {
            getDefinitionSetResponse(definitionSet.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionSetResponse>() {

                @Override
                public void onSuccess(final DefinitionSetResponse item) {
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
                                final ClientDefinitionServices.ServiceCallback<Collection<Rule>> callback) {

        DefinitionSetRuleAdapter definitionSetAdapter = getDefinitionSetRuleAdapter(definitionSet);

        if ( null != definitionSetAdapter ) {
            
            callback.onSuccess(definitionSetAdapter.getRules(definitionSet));
            
        } else {
            
            getDefinitionSetResponse(definitionSet.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionSetResponse>() {

                @Override
                public void onSuccess(final DefinitionSetResponse item) {
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
                                final ClientDefinitionServices.ServiceCallback<Set<PropertySet>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getPropertySets(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionResponse>() {

                @Override
                public void onSuccess(final DefinitionResponse item) {
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
                                final ClientDefinitionServices.ServiceCallback<Map<Property, Object>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getPropertiesValues(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionResponse>() {

                @Override
                public void onSuccess(final DefinitionResponse item) {
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
                              final ClientDefinitionServices.ServiceCallback<Set<Property>> callback) {

        DefinitionAdapter definitionAdapter = getDefinitionAdapter(definition);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getProperties(definition));
        } else {
            getDefinitionResponse(definition.getId(), new ClientDefinitionServices.ServiceCallback<DefinitionResponse>() {

                @Override
                public void onSuccess(final DefinitionResponse item) {
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
                              final ClientDefinitionServices.ServiceCallback<Set<Property>> callback) {

        PropertySetAdapter definitionAdapter = getPropertySetAdapter(propertySet);

        if ( null != definitionAdapter ) {
            callback.onSuccess(definitionAdapter.getProperties(propertySet));
        }  else {
            // TODO
        }

    }
    
    
    protected void getDefinitionSetResponse(final String id,
                                            final ClientDefinitionServices.ServiceCallback<DefinitionSetResponse> callback) {
        clientDefinitionServices.getDefinitionSetResponse(id, callback);
    }

    protected void getDefinitionResponse(final String id,
                                         final ClientDefinitionServices.ServiceCallback<DefinitionResponse> callback) {
        clientDefinitionServices.getDefinitionResponse(id, callback);
    }

}
