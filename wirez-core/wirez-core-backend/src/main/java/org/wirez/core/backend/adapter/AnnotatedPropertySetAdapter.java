package org.wirez.core.backend.adapter;

import org.wirez.core.api.adapter.PropertySetAdapter;
import org.wirez.core.api.definition.property.DefaultPropertySet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class AnnotatedPropertySetAdapter implements PropertySetAdapter<PropertySet> {
    
    @Override
    public Set<Property> getProperties(PropertySet pojo) {
        // TODO
        return null;
    }

    @Override
    public boolean accepts(Class pojoClass) {
        return !pojoClass.equals(DefaultPropertySet.class);
    }
    
}
