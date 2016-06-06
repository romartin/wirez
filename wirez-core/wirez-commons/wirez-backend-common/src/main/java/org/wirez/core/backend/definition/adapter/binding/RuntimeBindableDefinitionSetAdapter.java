package org.wirez.core.backend.definition.adapter.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeDefinitionSetAdapter;
import org.wirez.core.definition.adapter.binding.BindableDefinitionSetAdapter;
import org.wirez.core.graph.Graph;

import java.util.Map;
import java.util.Set;

class RuntimeBindableDefinitionSetAdapter<T> extends AbstractRuntimeDefinitionSetAdapter<T>
        implements BindableDefinitionSetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeBindableDefinitionSetAdapter.class);

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
    public String getDescription(T definitionSet) {
        Class<?> type = definitionSet.getClass();
        try {
            return getFieldValue( definitionSet, propertyDescriptionFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining description for Definition Set with id " + getId( definitionSet ));
        }

        return null;
    }

    @Override
    public Set<String> getDefinitions(T definitionSet) {
        return getAnnotatedDefinitions( definitionSet );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Graph> getGraph(T definitionSet) {
        Class<?> type = definitionSet.getClass();
        return graphTypes.get( type );
    }

    @Override
    public String getGraphFactory(T definitionSet) {
        Class<?> type = definitionSet.getClass();
        return graphFactory.get( type );
    }

    @Override
    public boolean accepts(Class<?> type) {
        return null != graphTypes && graphTypes.containsKey( type );
    }
}
