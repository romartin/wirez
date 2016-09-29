package org.kie.workbench.common.stunner.core.definition.adapter.bootstrap;

import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BootstrapAdapterFactory {

    public BootstrapDefinitionSetAdapter newDefinitionSetAdapter( final AdapterRegistry registry ) {
        return new BootstrapDefinitionSetAdapter( registry );
    }

    public BootstrapDefinitionSetRuleAdapter newDefinitionSetRuleAdapter( final AdapterRegistry registry ) {
        return new BootstrapDefinitionSetRuleAdapter( registry );
    }

    public BootstrapDefinitionAdapter newDefinitionAdapter( final AdapterRegistry registry ) {
        return new BootstrapDefinitionAdapter( registry );
    }

    public BootstrapPropertySetAdapter newPropertySetAdapter( final AdapterRegistry registry ) {
        return new BootstrapPropertySetAdapter( registry );
    }

    public BootstrapPropertyAdapter newPropertyAdapter( final AdapterRegistry registry ) {
        return new BootstrapPropertyAdapter( registry );
    }

}
