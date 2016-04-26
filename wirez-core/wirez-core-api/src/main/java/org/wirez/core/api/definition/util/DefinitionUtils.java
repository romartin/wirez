package org.wirez.core.api.definition.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class DefinitionUtils {

    DefinitionManager definitionManager;

    protected DefinitionUtils() {
    }
    
    @Inject
    @SuppressWarnings("all")
    public DefinitionUtils(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    public <T> String getDefinitionId( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getId( definition );
        
    }

    public  <T> Set<String> getDefinitionLabels( final T definition ) {
        
        final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        return adapter.getLabels( definition );
        
    }
    
}
