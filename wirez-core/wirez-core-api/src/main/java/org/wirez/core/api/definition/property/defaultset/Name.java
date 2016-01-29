package org.wirez.core.api.definition.property.defaultset;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.definition.property.HasDefaultValue;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.StringType;

@Portable
@Bindable
public class Name implements Property, HasDefaultValue<String>, HasValue<String> {

    public static final String ID = "name";

    public static final String DEFAULT_VALUE = "My element";

    private String value = DEFAULT_VALUE;
    
    @Override
    public String getId() {
        return ID;
    }

    @Override
    public PropertyType getType() {
        return new StringType();
    }

    @Override
    public String getCaption() {
        return "Name";
    }

    @Override
    public String getDescription() {
        return "The element's name";
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isOptional() {
        return true;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getDefaultValue() {
        return DEFAULT_VALUE;
    }
}
