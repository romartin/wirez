package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.*;
import org.wirez.core.definition.util.DefinitionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BindableAdapterFactoryImpl implements BindableAdapterFactory {
    
    DefinitionUtils definitionUtils;

    protected BindableAdapterFactoryImpl() {
        this( null );
    }

    @Inject
    public BindableAdapterFactoryImpl(final DefinitionUtils definitionUtils) {
        this.definitionUtils = definitionUtils;
    }

    public BindableDefinitionAdapter newBindableDefinitionAdapter() {
        return new BindableDefinitionAdapterImpl( definitionUtils );
    }
    
    public BindableDefinitionSetAdapter newBindableDefinitionSetAdapter() {
        return new BindableDefinitionSetAdapterImpl();
    }
    
    public BindablePropertyAdapter  newBindablePropertyAdapter() {
        return new BindablePropertyAdapterImpl();
    }
    
    public BindablePropertySetAdapter<Object> newBindablePropertySetAdapter() {
        return new BindablePropertySetAdapterImpl();
    }
    
}
