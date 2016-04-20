package org.wirez.core.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.api.graph.Graph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class AnnotatedDefinitionSetAdapter<T> extends AbstractAnnotatedAdapter<T> implements DefinitionSetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionSetAdapter.class);

    @Inject
    AnnotatedDefinitionAdapter annotatedDefinitionAdapter;

    @Override
    public boolean accepts(Class<?> pojo) {
        return pojo.getAnnotation(DefinitionSet.class) != null;
    }

    @Override
    public String getId(T definitionSet) {
        String defSetId = BindableAdapterUtils.getDefinitionSetId(definitionSet.getClass());

        // Avoid weld proxy class names issues.
        if ( defSetId.contains("$") ) {
            defSetId = defSetId.substring( 0, defSetId.indexOf("$") );
        }

        return defSetId;
    }

    @Override
    public String getDomain(T definitionSet) {
        String n = definitionSet.getClass().getName();
        return n.substring( n.lastIndexOf(".") + 1, n.length() );
    }

    @Override
    public Class<? extends Graph> getGraph(T definitionSet) {
        Class<? extends Graph> result = null;

        if ( null != definitionSet ) {
            org.wirez.core.api.definition.annotation.definitionset.DefinitionSet annotation = definitionSet.getClass().getAnnotation(org.wirez.core.api.definition.annotation.definitionset.DefinitionSet.class);
            if ( null != annotation ) {
                result = annotation.type();
            }
        }

        return result;
    }

    @Override
    public String getGraphFactory(T definitionSet) {
        String result = null;

        if ( null != definitionSet ) {
            org.wirez.core.api.definition.annotation.definitionset.DefinitionSet annotation = definitionSet.getClass().getAnnotation(org.wirez.core.api.definition.annotation.definitionset.DefinitionSet.class);
            if ( null != annotation ) {
                result = annotation.factory();
            }
        }

        return result;
    }

    @Override
    public String getDescription(T definitionSet) {
        try {
            return getAnnotatedFieldValue( definitionSet, org.wirez.core.api.definition.annotation.Description.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for DefinitionSet with id " + getId( definitionSet ));
        }

        return null;
    }

    @Override
    public Set<String> getDefinitions(T definitionSet) {
        Set<String> result = null;

        if ( null != definitionSet ) {

            org.wirez.core.api.definition.annotation.definitionset.DefinitionSet annotation = definitionSet.getClass().getAnnotation(org.wirez.core.api.definition.annotation.definitionset.DefinitionSet.class);
            if ( null != annotation ) {
                Class<?>[] definitions = annotation.definitions();
                if ( null != definitions ) {
                    result = new HashSet<String>( definitions.length );
                    for ( Class<?> defClass : definitions ) {
                        result.add( BindableAdapterUtils.getDefinitionSetId(defClass) );
                    }
                }
            }
            
        }
        
        return result;
    }

    @Override
    public int getPriority() {
        return 100;
    }
    
}
