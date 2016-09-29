package org.kie.workbench.common.stunner.core.definition.adapter;

import org.kie.workbench.common.stunner.core.definition.adapter.bootstrap.BootstrapAdapterFactory;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;
import org.kie.workbench.common.stunner.core.registry.RegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AdapterManagerImpl implements AdapterManager {

    private final AdapterRegistry registry;
    private final DefinitionSetAdapter<Object> definitionSetAdapter;
    private final DefinitionSetRuleAdapter<Object> definitionSetRuleAdapter;
    private final DefinitionAdapter<Object> definitionAdapter;
    private final PropertySetAdapter<Object> propertySetAdapter;
    private final PropertyAdapter<Object, Object> propertyAdapter;

    protected AdapterManagerImpl() {
        this.registry = null;
        this.definitionSetAdapter = null;
        this.definitionSetRuleAdapter = null;
        this.definitionAdapter = null;
        this.propertySetAdapter = null;
        this.propertyAdapter = null;
    }

    @Inject
    public AdapterManagerImpl( final RegistryFactory registryFactory,
                        final BootstrapAdapterFactory bootstrapAdapterFactory ) {
        this ( registryFactory.newAdapterRegistry(), bootstrapAdapterFactory);

    }

    AdapterManagerImpl( final AdapterRegistry registry,
                        final BootstrapAdapterFactory bootstrapAdapterFactory ) {
        this.registry = registry;
        this.definitionSetAdapter = bootstrapAdapterFactory.newDefinitionSetAdapter( registry );
        this.definitionSetRuleAdapter = bootstrapAdapterFactory.newDefinitionSetRuleAdapter( registry );
        this.definitionAdapter = bootstrapAdapterFactory.newDefinitionAdapter( registry );
        this.propertySetAdapter = bootstrapAdapterFactory.newPropertySetAdapter( registry );
        this.propertyAdapter = bootstrapAdapterFactory.newPropertyAdapter( registry );
    }

    @Override
    public DefinitionSetAdapter<Object> forDefinitionSet() {
        return definitionSetAdapter;
    }

    @Override
    public DefinitionSetRuleAdapter<Object> forRules() {
        return definitionSetRuleAdapter;
    }

    @Override
    public DefinitionAdapter<Object> forDefinition() {
        return definitionAdapter;
    }

    @Override
    public PropertySetAdapter<Object> forPropertySet() {
        return propertySetAdapter;
    }

    @Override
    public PropertyAdapter<Object, Object> forProperty() {
        return propertyAdapter;
    }

    @Override
    public AdapterRegistry registry() {
        return registry;
    }

}
