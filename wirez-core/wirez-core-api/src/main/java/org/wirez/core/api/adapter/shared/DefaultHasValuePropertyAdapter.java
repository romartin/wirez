package org.wirez.core.api.adapter.shared;

import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.HasDefaultValue;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultHasValuePropertyAdapter implements PropertyAdapter<Property> {
    
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

    // TODO: Find an alternative for being able to return HasValue.class.isAssignableFrom(pojoClass) (GWT not emulated)
    @Override
    public boolean accepts(final Class pojoClass) {
        return true;
    }

    @Override
    public int getPriority() {
        return 200;
    }

}
