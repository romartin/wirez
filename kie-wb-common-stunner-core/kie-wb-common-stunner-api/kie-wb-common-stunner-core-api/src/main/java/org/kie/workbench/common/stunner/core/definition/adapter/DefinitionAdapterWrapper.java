package org.kie.workbench.common.stunner.core.definition.adapter;

import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

import java.util.Set;

public abstract class DefinitionAdapterWrapper<T, A extends DefinitionAdapter<T>> implements DefinitionAdapter<T> {
    
    protected final A adapter;

    protected DefinitionAdapterWrapper() {
        this( null );
    }

    public DefinitionAdapterWrapper(final A adapter) {
        this.adapter = adapter;
    }

    @Override
    public String getId( final T pojo ) {
        return adapter.getId( pojo );
    }

    @Override
    public Object getNameProperty( final T pojo ) {
        return adapter.getNameProperty( pojo );
    }

    @Override
    public String getCategory( final T pojo ) {
        return adapter.getCategory( pojo );
    }

    @Override
    public String getTitle( final T pojo ) {
        return adapter.getTitle( pojo );
    }

    @Override
    public String getDescription( final T pojo ) {
        return adapter.getDescription( pojo );
    }

    @Override
    public Set<String> getLabels( final T pojo ) {
        return adapter.getLabels( pojo );
    }

    @Override
    public Set<?> getPropertySets( final T pojo ) {
        return adapter.getPropertySets( pojo );
    }

    @Override
    public Set<?> getProperties( final T pojo ) {
        return adapter.getProperties( pojo );
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
