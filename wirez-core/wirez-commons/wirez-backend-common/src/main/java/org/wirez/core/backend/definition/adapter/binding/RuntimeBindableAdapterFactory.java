package org.wirez.core.backend.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.*;
import org.wirez.core.definition.util.DefinitionUtils;

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
        return new RuntimeBindableDefinitionAdapter();
    }
    
    public BindableDefinitionSetAdapter newBindableDefinitionSetAdapter() {
        return new RuntimeBindableDefinitionSetAdapter();
    }
    
    public BindablePropertyAdapter  newBindablePropertyAdapter() {
        return new RuntimeBindablePropertyAdapter();
    }
    
    public BindablePropertySetAdapter<Object> newBindablePropertySetAdapter() {
        return new RuntimeBindablePropertySetAdapter<>();
    }
    
}
