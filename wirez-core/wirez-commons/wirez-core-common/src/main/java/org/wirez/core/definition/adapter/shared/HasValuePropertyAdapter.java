package org.wirez.core.definition.adapter.shared;

import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.impl.PropertyImpl;
import org.wirez.core.definition.property.PropertyType;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class HasValuePropertyAdapter implements PropertyAdapter<PropertyImpl, Object> {

    @Override
    public String getId(final PropertyImpl pojo) {
        return pojo.getId();
    }

    @Override
    public PropertyType getType(final PropertyImpl pojo) {
        return pojo.getType();
    }

    @Override
    public String getCaption(final PropertyImpl pojo) {
        return pojo.getCaption();
    }

    @Override
    public String getDescription(final PropertyImpl pojo) {
        return pojo.getDescription();
    }

    @Override
    public boolean isReadOnly(final PropertyImpl pojo) {
        return pojo.isReadOnly();
    }

    @Override
    public boolean isOptional(final PropertyImpl pojo) {
        return pojo.isOptional();
    }

    @Override
    public Object getValue(final PropertyImpl pojo) {
        return pojo.getValue();
    }

    @Override
    public Object getDefaultValue(final PropertyImpl pojo) {
        return pojo.getDefaultValue();
    }

    @Override
    public Map<Object, String> getAllowedValues(final PropertyImpl pojo) {
        // TODO
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValue(final PropertyImpl pojo, final Object value) {
        
        if ( pojo.isReadOnly() ) {
            
            throw new RuntimeException( "Cannot set new value for property [" + pojo.getId() + "] as it is read only! " );
            
        }
        
        pojo.setValue(value);
    }

    @Override
    public boolean accepts(final Class<?> pojo) {
        return PropertyImpl.class.getName().equals(pojo.getName());
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
