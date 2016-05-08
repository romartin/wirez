package org.wirez.core.api.definition.adapter.binding;

import org.wirez.core.api.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.api.graph.Graph;

import java.util.Map;
import java.util.Set;

public abstract class BindableDefinitionSetAdapter<T> extends AbstractBindableAdapter<T> implements DefinitionSetAdapter<T> {

    protected abstract Map<Class, String> getPropertyDescriptionFieldNames();
    protected abstract Map<Class, Class> getGraphTypes();
    protected abstract Map<Class, String> getGraphFactory();
    protected abstract Set<String> getDefinitionIds();

    @Override
    public String getId(final T pojo) {
        String _id = BindableAdapterUtils.getDefinitionSetId( pojo.getClass() );

        // Avoid weld proxy class names issues.
        if ( _id.contains("$") ) {
            _id = _id.substring( 0, _id.indexOf("$") );
        }
        
        return _id;
    }

    @Override
    public String getDomain(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        String n = clazz.getName();
        return n.substring( n.lastIndexOf(".") + 1, n.length() );
    }

    @Override
    public String getDescription(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    @Override
    public Set<String> getDefinitions(final T pojo) {
        return getDefinitionIds();
    }

    @Override
    public String getGraphFactory(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getGraphFactory().get( clazz );
    }

    @Override
    public Class<? extends Graph> getGraph(final T pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getGraphTypes().get( clazz );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
        return getPropertyDescriptionFieldNames().containsKey( clazz );
    }

}
