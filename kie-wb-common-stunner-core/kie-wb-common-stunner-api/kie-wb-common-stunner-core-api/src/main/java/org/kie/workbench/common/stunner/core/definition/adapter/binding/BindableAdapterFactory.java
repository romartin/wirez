package org.kie.workbench.common.stunner.core.definition.adapter.binding;

public interface BindableAdapterFactory {
    
    BindableDefinitionAdapter newBindableDefinitionAdapter();
    
    BindableDefinitionSetAdapter newBindableDefinitionSetAdapter();
    
    BindablePropertyAdapter newBindablePropertyAdapter();
    
    BindablePropertySetAdapter newBindablePropertySetAdapter();
    
}
