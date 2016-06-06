package org.wirez.core.backend.definition.utils;

import org.wirez.core.definition.annotation.definitionset.DefinitionSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BackendBindableDefinitionUtils {

    public static <T> Set<Class<?>> getDefinitions(final T definitionSet ) {
        Set<Class<?>> result = null;

        if ( null != definitionSet ) {

            DefinitionSet annotation = definitionSet.getClass().getAnnotation( DefinitionSet.class );
            if ( null != annotation ) {
                Class<?>[] definitions = annotation.definitions();
                if ( definitions.length > 0 ) {
                    result = new HashSet<Class<?>>( definitions.length );
                    Collections.addAll(result, definitions);
                }
            }

        }

        return result;
    }
    
}
