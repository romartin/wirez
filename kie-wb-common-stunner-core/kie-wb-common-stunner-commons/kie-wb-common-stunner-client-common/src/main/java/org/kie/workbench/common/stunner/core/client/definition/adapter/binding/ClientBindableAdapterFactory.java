package org.kie.workbench.common.stunner.core.client.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.*;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClientBindableAdapterFactory implements BindableAdapterFactory {
    
    DefinitionUtils definitionUtils;

    protected ClientBindableAdapterFactory() {
        this( null );
    }

    @Inject
    public ClientBindableAdapterFactory(final DefinitionUtils definitionUtils) {
        this.definitionUtils = definitionUtils;
    }

    public BindableDefinitionAdapter newBindableDefinitionAdapter() {
        return new ClientBindableDefinitionAdapter( definitionUtils );
    }
    
    public BindableDefinitionSetAdapter newBindableDefinitionSetAdapter() {
        return new ClientBindableDefinitionSetAdapter();
    }
    
    public BindablePropertyAdapter newBindablePropertyAdapter() {
        return new ClientBindablePropertyAdapter();
    }
    
    public BindablePropertySetAdapter<Object> newBindablePropertySetAdapter() {
        return new ClientBindablePropertySetAdapter();
    }
    
}
