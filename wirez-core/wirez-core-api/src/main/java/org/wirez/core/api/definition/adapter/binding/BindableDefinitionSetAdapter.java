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
    public String getId(T pojo) {
        String _id = getPojoId( pojo );

        // Avoid weld proxy class names issues.
        if ( _id.contains("$") ) {
            _id = _id.substring( 0, _id.indexOf("$") );
        }
        
        return _id;
    }

    @Override
    public String getDomain(T pojo) {
        String n = pojo.getClass().getName();
        return n.substring( n.lastIndexOf(".") + 1, n.length() );
    }

    @Override
    public String getDescription(T pojo) {
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Set<String> getDefinitions(T pojo) {
        return getDefinitionIds();
    }

    @Override
    public String getGraphFactory(T pojo) {
        return getGraphFactory().get(pojo.getClass());
    }

    @Override
    public Class<? extends Graph> getGraph(T pojo) {
        return getGraphTypes().get(pojo.getClass());
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return getPropertyDescriptionFieldNames().containsKey(pojo);
    }

}
