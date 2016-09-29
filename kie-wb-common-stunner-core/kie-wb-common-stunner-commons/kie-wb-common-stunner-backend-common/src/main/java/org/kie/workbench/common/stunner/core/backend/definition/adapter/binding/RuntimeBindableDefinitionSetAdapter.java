package org.kie.workbench.common.stunner.core.backend.definition.adapter.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.AbstractRuntimeDefinitionSetAdapter;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableDefinitionSetAdapter;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

import java.util.Map;
import java.util.Set;

class RuntimeBindableDefinitionSetAdapter<T> extends AbstractRuntimeDefinitionSetAdapter<T>
        implements BindableDefinitionSetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeBindableDefinitionSetAdapter.class);

    private Map<Class, String> propertyDescriptionFieldNames;
    private Map<Class, Class> graphFactoryTypes;
    private Set<String> definitionIds;

    @Override
    public void setBindings( final Map<Class, String> propertyDescriptionFieldNames,
                             final Map<Class, Class> graphFactoryTypes,
                             final Set<String> definitionIds) {
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
        this.graphFactoryTypes = graphFactoryTypes;
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
    @SuppressWarnings( "unchecked" )
    public Class<? extends ElementFactory> getGraphFactoryType( final T definitionSet ) {
        Class<?> type = definitionSet.getClass();
        return graphFactoryTypes.get( type );
    }


    @Override
    public boolean accepts(Class<?> type) {
        return null != graphFactoryTypes && graphFactoryTypes.containsKey( type );
    }
}
