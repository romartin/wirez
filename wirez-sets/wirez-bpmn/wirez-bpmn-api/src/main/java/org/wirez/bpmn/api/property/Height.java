/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.bpmn.api.property;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.property.Caption;
import org.wirez.core.api.definition.annotation.property.DefaultValue;
import org.wirez.core.api.definition.annotation.property.Optional;
import org.wirez.core.api.definition.annotation.property.Property;
import org.wirez.core.api.definition.annotation.property.ReadOnly;
import org.wirez.core.api.definition.annotation.property.Type;
import org.wirez.core.api.definition.annotation.property.Value;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.DoubleType;

@Portable
@Bindable
@Property
public class Height implements BPMNProperty {

    @Caption
    public static final transient String caption = "Height";

    @Description
    public static final transient String description = "The height";

    @ReadOnly
    public static final Boolean readOnly = false;

    @Optional
    public static final Boolean optional = false;

    @Type
    public static final PropertyType type = new DoubleType();

    @DefaultValue
    public static final Double defaultValue = 50d;

    @Value
    @Max( 100 )
    @Min( 40 )
    @NotNull
    private Double value = defaultValue;

    public Height() {

    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isOptional() {
        return optional;
    }

    public PropertyType getType() {
        return type;
    }


    public Double getDefaultValue() {
        return defaultValue;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
