package org.wirez.core.api.adapter.impl;

import org.wirez.core.api.adapter.Adapter;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

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
    public boolean accepts(final Class pojoClass) {
        return pojoClass.equals(Property.class);
    }


}
