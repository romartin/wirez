package org.wirez.core.backend.definition.adapter;

import org.wirez.core.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.annotation.definitionset.DefinitionSet;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRuntimeDefinitionSetAdapter<T> extends AbstractRuntimeAdapter<T>
        implements DefinitionSetAdapter<T> {

    protected Set<String> getAnnotatedDefinitions(T definitionSet) {
        Set<String> result = null;

        if ( null != definitionSet ) {

            DefinitionSet annotation = definitionSet.getClass().getAnnotation(DefinitionSet.class);
            if ( null != annotation ) {
                Class<?>[] definitions = annotation.definitions();
                if ( definitions.length > 0 ) {
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

}
