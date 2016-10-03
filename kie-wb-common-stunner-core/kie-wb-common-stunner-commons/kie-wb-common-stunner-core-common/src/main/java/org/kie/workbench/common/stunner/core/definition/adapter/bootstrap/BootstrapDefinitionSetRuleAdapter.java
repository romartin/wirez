package org.kie.workbench.common.stunner.core.definition.adapter.bootstrap;

import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionSetRuleAdapter;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;
import org.kie.workbench.common.stunner.core.rule.Rule;

import java.util.Collection;

class BootstrapDefinitionSetRuleAdapter implements DefinitionSetRuleAdapter<Object> {

    private final AdapterRegistry adapterRegistry;

    BootstrapDefinitionSetRuleAdapter( final AdapterRegistry adapterRegistry ) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public Collection<Rule> getRules( final Object pojo ) {
        return getWrapped( pojo ).getRules( pojo );
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean accepts( final Class<?> type ) {
        return null != getWrapped( type );
    }

    @Override
    public boolean isPojoModel() {
        return false;
    }

    private DefinitionSetRuleAdapter<Object> getWrapped( final Object pojo ) {
        return getWrapped( pojo.getClass() );
    }

    private DefinitionSetRuleAdapter<Object> getWrapped( final Class<?> type ) {
        return adapterRegistry.getDefinitionSetRuleAdapter( type );
    }
}
