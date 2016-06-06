package org.wirez.core.backend.definition.adapter.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeAdapter;
import org.wirez.core.definition.adapter.PropertySetAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

import javax.enterprise.context.Dependent;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class RuntimePropertySetAdapter<T> extends AbstractRuntimeAdapter<T> implements PropertySetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimePropertySetAdapter.class);

    @Override
    public String getId(T propertySet) {
        return BindableAdapterUtils.getPropertySetId(propertySet.getClass());
    }

    @Override
    public String getName(T propertySet) {
        try {
            return getAnnotatedFieldValue( propertySet, Name.class );
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
                    Property annotation = field.getAnnotation(Property.class);
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
    
}
