package org.wirez.core.backend.definition.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.definition.adapter.PropertySetAdapter;
import org.wirez.core.api.definition.annotation.propertyset.PropertySet;

import javax.enterprise.context.Dependent;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class AnnotatedPropertySetAdapter<T> extends AbstractAnnotatedAdapter<T> implements PropertySetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedPropertySetAdapter.class);

    @Override
    public String getId(T propertySet) {
        return propertySet.getClass().getSimpleName();
    }

    @Override
    public String getName(T propertySet) {
        try {
            return getAnnotatedFieldValue( propertySet, org.wirez.core.api.definition.annotation.Name.class );
        } catch (Exception e) {
            LOG.error("Error obtaining annotated category for PropertySet with id " + getId( propertySet ));
        }

        return null;
    }

    @Override
    public Set<?> getProperties(T propertySet) {
        Set<Object> result = null;

        if ( null != propertySet ) {
            Field[] fields = propertySet.getClass().getDeclaredFields();
            if ( null != fields ) {
                result = new HashSet<>();
                for (Field field : fields) {
                    org.wirez.core.api.definition.annotation.propertyset.Property annotation = field.getAnnotation(org.wirez.core.api.definition.annotation.propertyset.Property.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            Object property = field.get(propertySet);
                            result.add(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for T with id " + getId( propertySet ));
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean accepts(Class<?> pojo)
    {
        return pojo.getAnnotation(PropertySet.class) != null;
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
    
}
