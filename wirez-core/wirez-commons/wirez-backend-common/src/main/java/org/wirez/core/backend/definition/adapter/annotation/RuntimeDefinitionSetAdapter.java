package org.wirez.core.backend.definition.adapter.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeDefinitionSetAdapter;
import org.wirez.core.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Graph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;

@Dependent
public class RuntimeDefinitionSetAdapter<T> extends AbstractRuntimeDefinitionSetAdapter<T> implements DefinitionSetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeDefinitionSetAdapter.class);

    RuntimeDefinitionAdapter annotatedDefinitionAdapter;

    @Inject
    public RuntimeDefinitionSetAdapter(RuntimeDefinitionAdapter annotatedDefinitionAdapter) {
        this.annotatedDefinitionAdapter = annotatedDefinitionAdapter;
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(DefinitionSet.class) != null;
    }

    @Override
    public Class<? extends ElementFactory> getGraphFactoryType( final T definitionSet ) {
        Class<? extends ElementFactory> result = null;

        if ( null != definitionSet ) {
            DefinitionSet annotation = definitionSet.getClass().getAnnotation(DefinitionSet.class);
            if ( null != annotation ) {
                result = annotation.graphFactory();
            }
        }

        return result;

    }

    @Override
    public String getDescription(T definitionSet) {
        try {
            return getAnnotatedFieldValue( definitionSet, Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for DefinitionSet with id " + getId( definitionSet ));
        }

        return null;
    }

    @Override
    public Set<String> getDefinitions(T definitionSet) {
        return getAnnotatedDefinitions( definitionSet );
    }

}
