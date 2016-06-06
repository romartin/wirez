package org.wirez.core.definition.adapter;

import org.wirez.core.definition.property.PropertyType;

import java.util.Map;

public abstract class PropertyAdapterWrapper<T, V, A extends PropertyAdapter<T, V>> implements PropertyAdapter<T, V> {
    
    protected final A adapter;

    protected PropertyAdapterWrapper() {
        this( null );
    }

    public PropertyAdapterWrapper(final A adapter) {
        this.adapter = adapter;
    }

    @Override
    public String getId( final T pojo ) {
        return adapter.getId( pojo );
    }

    @Override
    public PropertyType getType( final T pojo ) {
        return adapter.getType( pojo );
    }

    @Override
    public String getCaption( final T pojo ) {
        return adapter.getCaption( pojo );
    }

    @Override
    public String getDescription( final T pojo ) {
        return adapter.getDescription( pojo );
    }

    @Override
    public boolean isReadOnly( final T pojo ) {
        return adapter.isReadOnly( pojo );
    }

    @Override
    public boolean isOptional( final T pojo ) {
        return adapter.isOptional( pojo );
    }

    @Override
    public V getValue( final T pojo ) {
        return adapter.getValue( pojo );
    }

    @Override
    public V getDefaultValue( final T pojo ) {
        return adapter.getDefaultValue( pojo );
    }

    @Override
    public Map<V, String> getAllowedValues( final T pojo ) {
        return adapter.getAllowedValues( pojo );
    }

    @Override
    public void setValue( final T pojo, 
                          final V value ) {
        adapter.setValue( pojo, value );
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
