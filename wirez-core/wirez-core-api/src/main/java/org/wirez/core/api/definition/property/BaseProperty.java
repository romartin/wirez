/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.api.definition.property;

import org.uberfire.commons.validation.PortablePreconditions;

public abstract class BaseProperty implements Property {

    protected final String id;
    protected String caption;
    protected String description;
    protected boolean isReadOnly;
    protected boolean isOptional;
    protected boolean isPublic;

    public BaseProperty(final String id) {
        this.id = PortablePreconditions.checkNotNull( "id",
                id );
    }

    public BaseProperty(final String id,
                        final String caption,
                        final String description,
                        final boolean isReadOnly,
                        final boolean isOptional,
                        final boolean isPublic) {
        this.id = PortablePreconditions.checkNotNull( "id",
                                                      id );
        this.caption = PortablePreconditions.checkNotNull( "caption",
                                                           caption );
        this.description = PortablePreconditions.checkNotNull( "description",
                                                               description );
        this.isReadOnly = isReadOnly;
        this.isOptional = isOptional;
        this.isPublic = isPublic;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public boolean isOptional() {
        return isOptional;
    }

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    public BaseProperty setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public BaseProperty setDescription(String description) {
        this.description = description;
        return this;
    }

    public BaseProperty setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        return this;
    }

    public BaseProperty setOptional(boolean optional) {
        isOptional = optional;
        return this;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof BaseProperty) ) {
            return false;
        }

        BaseProperty that = (BaseProperty) o;

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
        return "BaseProperty{" +
                "id='" + id + '\'' +
                ", type=" + getType() +
                ", caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ", isReadOnly=" + isReadOnly +
                ", isOptional=" + isOptional +
                ", isPublic=" + isPublic +
                '}';
    }

}
