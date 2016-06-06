package org.wirez.core.definition.adapter.binding;

public interface BindableAdapterFactory {
    
    BindableDefinitionAdapter newBindableDefinitionAdapter();
    
    BindableDefinitionSetAdapter newBindableDefinitionSetAdapter();
    
    BindablePropertyAdapter newBindablePropertyAdapter();
    
    BindablePropertySetAdapter newBindablePropertySetAdapter();
    
}
