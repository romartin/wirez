package org.wirez.core.api.definition.property.defaultset;

import org.wirez.core.api.definition.property.HasDefaultValue;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.StringType;

public class ConnectionSourceMagnet implements Property, HasDefaultValue<Integer>, HasValue<Integer> {

    public static final String ID = "connectionSourceMagnet";

    public static final Integer DEFAULT_VALUE = 0;

    private Integer value = DEFAULT_VALUE;
    
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
        return "Source connection magnet";
    }

    @Override
    public String getDescription() {
        return "The source connection magnet";
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isOptional() {
        return true;
    }

    @Override
    public boolean isPublic() {
        return true;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getDefaultValue() {
        return DEFAULT_VALUE;
    }
}
