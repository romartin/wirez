package org.kie.workbench.common.stunner.core.definition.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.kie.workbench.common.stunner.core.definition.property.PropertyType;

@Portable
public class PropertyImpl<C> {

    private final String id;
    private String caption;
    private String description;
    private boolean isReadOnly;
    private boolean isOptional;
    protected C defaultValue;
    protected C value;
    private final PropertyType type;

    public PropertyImpl(@MapsTo("id") final String id,
                        @MapsTo("caption") final String caption,
                        @MapsTo("description") final String description,
                        @MapsTo("isReadOnly") final boolean isReadOnly,
                        @MapsTo("isOptional") final boolean isOptional,
                        @MapsTo("defaultValue") final C defaultValue,
                        @MapsTo("value") final C value,
                        @MapsTo("type") final PropertyType type) {
        this.id = PortablePreconditions.checkNotNull( "id",
                id );
        this.caption = PortablePreconditions.checkNotNull( "caption",
                caption );
        this.description = PortablePreconditions.checkNotNull( "description",
                description );
        this.type = PortablePreconditions.checkNotNull( "type",
                type );
        this.isReadOnly = isReadOnly;
        this.isOptional = isOptional;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public PropertyType getType() {
        return type;
    }


    public PropertyImpl setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public PropertyImpl setDescription(String description) {
        this.description = description;
        return this;
    }

    public PropertyImpl setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        return this;
    }

    public PropertyImpl setOptional(boolean optional) {
        isOptional = optional;
        return this;
    }

    public C getValue() {
        return value;
    }

    public void setValue(C value) {
        this.value = value;
    }

    public C getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof PropertyImpl) ) {
            return false;
        }

        PropertyImpl that = (PropertyImpl) o;

        if ( isOptional != that.isOptional ) {
            return false;
        }
        if ( isReadOnly != that.isReadOnly ) {
            return false;
        }
        if ( !caption.equals( that.caption ) ) {
            return false;
        }
        if ( !description.equals( that.description ) ) {
            return false;
        }
        if ( !id.equals( that.id ) ) {
            return false;
        }
        if ( !getType().equals( that.getType() ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = ~~result;
        result = 31 * result + getType().hashCode();
        result = ~~result;
        result = 31 * result + caption.hashCode();
        result = ~~result;
        result = 31 * result + description.hashCode();
        result = ~~result;
        result = 31 * result + ( isReadOnly ? 1 : 0 );
        result = ~~result;
        result = 31 * result + ( isOptional ? 1 : 0 );
        result = ~~result;
        return result;
    }

    @Override
    public String toString() {
        return "PropertyImpl{" +
                "id='" + id + '\'' +
                ", type=" + getType() +
                ", caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ", isReadOnly=" + isReadOnly +
                ", isOptional=" + isOptional +
                '}';
    }

}
