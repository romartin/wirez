package org.wirez.core.definition.adapter;

import org.wirez.core.graph.Graph;

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
    public Class<? extends Graph> getGraph( final T pojo ) {
        return adapter.getGraph( pojo );
    }

    @Override
    public String getGraphFactory( final T pojo ) {
        return adapter.getGraphFactory( pojo );
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
