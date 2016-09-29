package org.kie.workbench.common.stunner.core.backend.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.*;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RuntimeBindableAdapterFactory implements BindableAdapterFactory {

    DefinitionUtils definitionUtils;

    protected RuntimeBindableAdapterFactory() {
        this( null );
    }
    
    @Inject
    public RuntimeBindableAdapterFactory(final DefinitionUtils definitionUtils) {
        this.definitionUtils = definitionUtils;
    }

    public BindableDefinitionAdapter newBindableDefinitionAdapter() {
        return new RuntimeBindableDefinitionAdapter( definitionUtils );
    }
    
    public BindableDefinitionSetAdapter newBindableDefinitionSetAdapter() {
        return new RuntimeBindableDefinitionSetAdapter();
    }
    
    public BindablePropertyAdapter newBindablePropertyAdapter() {
        return new RuntimeBindablePropertyAdapter();
    }
    
    public BindablePropertySetAdapter<Object> newBindablePropertySetAdapter() {
        return new RuntimeBindablePropertySetAdapter<>();
    }
    
}
