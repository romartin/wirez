package org.wirez.core.backend.definition.adapter.binding;

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.NonExistingPropertyException;
import org.jboss.errai.databinding.client.PropertyType;
import org.jboss.errai.databinding.client.api.DataBinder;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: DO not use errai binding as it's considered for client side, although for now can be used here.
public class RuntimeBindingUtils {

    private static Logger LOGGER = Logger.getLogger(RuntimeBindingUtils.class.getName());

    @SuppressWarnings("unchecked")
    public static <T, R> R merge(final T source,
                                 final R target) {

        if ( null != source ) {

            final HasProperties hasProperties = (HasProperties) DataBinder.forModel( source ).getModel();

            if ( null != hasProperties ) {

                final Map<String, PropertyType> propertyTypeMap = hasProperties.getBeanProperties();

                if ( null != propertyTypeMap && !propertyTypeMap.isEmpty() ) {

                    final HasProperties targetProperties = (HasProperties) DataBinder.forModel( target ).getModel();

                    for ( final Map.Entry<String, PropertyType> entry : propertyTypeMap.entrySet() ) {

                        final String pId = entry.getKey();

                        try {

                            targetProperties.set( pId, hasProperties.get( pId ) );

                        } catch ( NonExistingPropertyException exception ) {

                            // Just skip it, Go to next property.

                            LOGGER.log( Level.INFO, "RuntimeBindingUtils#merge - Skipping merge property [" + pId + "]" );

                        }

                    }

                    return (R) target;

                }

            }

        }

        return null;
    }

}
