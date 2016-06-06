package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindableDefinitionSetAdapter;
import org.wirez.core.graph.Graph;

import java.util.Map;
import java.util.Set;

class BindableDefinitionSetAdapterImpl extends AbstractBindableAdapter<Object> implements BindableDefinitionSetAdapter<Object> {

    private Map<Class, String> propertyDescriptionFieldNames;
    private Map<Class, Class> graphTypes;
    private Map<Class, String> graphFactory;
    private Set<String> definitionIds;

    @Override
    public void setBindings( final Map<Class, String> propertyDescriptionFieldNames, 
                             final Map<Class, Class> graphTypes, 
                             final Map<Class, String> graphFactory, 
                             final Set<String> definitionIds) {
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
        this.graphTypes = graphTypes;
        this.graphFactory = graphFactory;
        this.definitionIds = definitionIds;
    }
    
    @Override
    public String getId(final Object pojo) {
        String _id = BindableAdapterUtils.getDefinitionSetId( pojo.getClass() );

        // Avoid weld proxy class names issues.
        if ( _id.contains("$") ) {
            _id = _id.substring( 0, _id.indexOf("$") );
        }
        
        return _id;
    }

    @Override
    public String getDomain(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        String n = clazz.getName();
        return n.substring( n.lastIndexOf(".") + 1, n.length() );
    }

    @Override
    public String getDescription(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( clazz ) );
    }

    @Override
    public Set<String> getDefinitions(final Object pojo) {
        return getDefinitionIds();
    }

    @Override
    public String getGraphFactory(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getGraphFactory().get( clazz );
    }

    @Override
    public Class<? extends Graph> getGraph(final Object pojo) {
        final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojo.getClass() );
        return getGraphTypes().get( clazz );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        if ( null != propertyDescriptionFieldNames ) {
            final Class<?> clazz = BindableAdapterUtils.handleBindableProxyClass( pojoClass );
            return getPropertyDescriptionFieldNames().containsKey( clazz );
        }
        return false;
    }

    private Map<Class, String> getPropertyDescriptionFieldNames() {
        return propertyDescriptionFieldNames;
    }
    
    private Map<Class, Class> getGraphTypes() {
        return graphTypes;
    }
    
    private Map<Class, String> getGraphFactory() {
        return graphFactory;
    }
    
    private Set<String> getDefinitionIds() {
        return definitionIds;
    }
    
}
