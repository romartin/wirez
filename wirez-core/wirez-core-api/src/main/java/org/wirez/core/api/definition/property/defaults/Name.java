package org.wirez.core.api.definition.property.defaults;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.wirez.core.api.definition.impl.PropertyImpl;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.StringType;

public abstract class Name extends PropertyImpl<String> {

    public static final String ID = "Name";

    public static final String DEFAULT_VALUE = "My element";

    private String value = DEFAULT_VALUE;

    public Name() {
        super(ID, "Name", "The name", false, false, "No name", null, new StringType());
    }

}
