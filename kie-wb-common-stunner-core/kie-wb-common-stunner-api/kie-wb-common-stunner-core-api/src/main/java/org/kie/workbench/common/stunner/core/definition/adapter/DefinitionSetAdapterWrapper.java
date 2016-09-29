package org.kie.workbench.common.stunner.core.definition.adapter;

import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

import java.util.Set;

public abstract class DefinitionSetAdapterWrapper<T, A extends DefinitionSetAdapter<T>> implements DefinitionSetAdapter<T> {
    
    protected final A adapter;

    protected DefinitionSetAdapterWrapper() {
        this( null );
    }
    public DefinitionSetAdapterWrapper(final A adapter) {
        this.adapter = adapter;
    }

    @Override
    public String getId( final T pojo ) {
        return adapter.getId( pojo );
    }

    @Override
    public String getDomain( final T pojo ) {
        return adapter.getDomain( pojo );
    }

    @Override
    public String getDescription( final T pojo ) {
        return adapter.getDescription( pojo );
    }

    @Override
    public Set<String> getDefinitions( final T pojo ) {
        return adapter.getDefinitions( pojo );
    }

    @Override
    public Class<? extends ElementFactory> getGraphFactoryType( final T pojo ) {
        return adapter.getGraphFactoryType( pojo );
    }

    @Override
    public boolean isPojoModel() {
        return adapter.isPojoModel();
    }

    @Override
    public int getPriority() {
        return adapter.getPriority();
    }

    @Override
    public boolean accepts( final Class<?> type ) {
        return adapter.accepts( type );
    }
    
}
