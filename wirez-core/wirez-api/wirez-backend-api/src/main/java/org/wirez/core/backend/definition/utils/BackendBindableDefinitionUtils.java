package org.wirez.core.backend.definition.utils;

import org.wirez.core.definition.annotation.definitionset.DefinitionSet;

import java.util.HashSet;
import java.util.Set;

public class BackendBindableDefinitionUtils {

    public static <T> Set<Class<?>> getDefinitions(final T definitionSet ) {
        Set<Class<?>> result = null;

        if ( null != definitionSet ) {

            DefinitionSet annotation = definitionSet.getClass().getAnnotation(DefinitionSet.class);
            if ( null != annotation ) {
                Class<?>[] definitions = annotation.definitions();
                if ( null != definitions ) {
                    result = new HashSet<Class<?>>( definitions.length );
                    for ( Class<?> defClass : definitions ) {
                        result.add( defClass );
                    }
                }
            }

        }

        return result;
    }
    
}
