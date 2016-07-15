package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindableDefinitionSetAdapter;
import org.wirez.core.graph.Graph;

import java.util.Map;
import java.util.Set;

class ClientBindableDefinitionSetAdapter extends AbstractClientBindableAdapter<Object> implements BindableDefinitionSetAdapter<Object> {

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
        String n = pojo.getClass().getName();
        return n.substring( n.lastIndexOf(".") + 1, n.length() );
    }

    @Override
    public String getDescription(final Object pojo) {
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( pojo.getClass() ) );
    }

    @Override
    public Set<String> getDefinitions(final Object pojo) {
        return getDefinitionIds();
    }

    @Override
    public String getGraphFactory(final Object pojo) {
        return getGraphFactory().get( pojo.getClass() );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Graph> getGraph(final Object pojo) {
        return getGraphTypes().get( pojo.getClass() );
    }

    @Override
    public boolean accepts(final Class<?> pojoClass) {
        if ( null != propertyDescriptionFieldNames ) {
            return getPropertyDescriptionFieldNames().containsKey( pojoClass );
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
