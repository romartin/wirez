package org.kie.workbench.common.stunner.core.definition.adapter;

import java.util.Set;

public abstract class PropertySetAdapterWrapper<T, A extends PropertySetAdapter<T>> implements PropertySetAdapter<T> {
    
    protected final A adapter;

    protected PropertySetAdapterWrapper() {
        this( null );
    }

    public PropertySetAdapterWrapper(final A adapter) {
        this.adapter = adapter;
    }

    @Override
    public String getId(final T pojo) {
        return adapter.getId( pojo );
    }

    @Override
    public String getName(final T pojo) {
        return adapter.getName( pojo );
    }

    @Override
    public Set<?> getProperties(final T pojo) {
        return adapter.getProperties( pojo );
    }

    @Override
    public int getPriority() {
        return adapter.getPriority();
    }

    @Override
    public boolean accepts(final Class<?> type) {
        return adapter.accepts( type );
    }
    
}
