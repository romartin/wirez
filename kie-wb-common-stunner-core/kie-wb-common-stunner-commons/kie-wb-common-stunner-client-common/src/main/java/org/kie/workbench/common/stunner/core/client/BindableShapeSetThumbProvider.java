package org.kie.workbench.common.stunner.core.client;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.ShapeSetThumbProvider;

public abstract class BindableShapeSetThumbProvider implements ShapeSetThumbProvider {

    DefinitionManager definitionManager;

    public BindableShapeSetThumbProvider(final DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    protected abstract boolean thumbFor(Class<?> clazz );
    
    
    @Override
    public boolean thumbFor(final String definitionSetId) {
        final Object defSet = definitionManager.definitionSets().getDefinitionSetById( definitionSetId );
        return thumbFor( defSet.getClass() );
    }
    
    protected boolean isSameClass( final Class<?> c1, 
                                   final Class<?> c2 ) {
        return null != c1 && c1.getName().equals( c2.getName() );
    }
}
