package org.wirez.core.api.adapter.shared;

import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.HasDefaultValue;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultPropertyAdapter implements PropertyAdapter<Property> {
    
    @Override
    public Object getValue(final Property pojo) {
        Object result = null;
        if ( null != pojo ) {
            HasValue hasValue = (HasValue) pojo;
            result = hasValue.getValue();
        }
        
        return result;
    }

    @Override
    public Object getDefaultValue(final Property pojo) {
        Object result = null;
        if ( null != pojo ) {
            HasDefaultValue hasValue = (HasDefaultValue) pojo;
            result = hasValue.getDefaultValue();
        }

        return result;
    }

    @Override
    public void setValue(final Property pojo, final Object value) {
        if ( null != pojo ) {
            HasValue hasValue = (HasValue) pojo;
            ((HasValue) pojo).setValue(value);
        }
    }

    @Override
    public boolean accepts(final Class pojoClass) {
        return pojoClass.equals(Property.class);
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
