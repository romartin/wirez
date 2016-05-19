package org.wirez.core.registry.definition;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.registry.BaseMapRegistry;
import org.wirez.core.registry.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashMap;

@Dependent
@Map
public class MapDefinitionRegistry<D> extends BaseMapRegistry<D> implements DefinitionRegistry<D> {
    
    DefinitionManager definitionManager;

    @Inject
    public MapDefinitionRegistry(final DefinitionManager definitionManager ) {
        super( new HashMap<>() );
        this.definitionManager = definitionManager;
    }

    @Override
    protected String getItemId(final D item) {
        if ( null != item ) {
            final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( item.getClass() );
            return adapter.getId( item );
        }
        
        return null;
    }
    
}
