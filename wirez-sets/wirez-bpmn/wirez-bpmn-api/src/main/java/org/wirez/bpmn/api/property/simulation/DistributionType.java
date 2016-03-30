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

package org.wirez.bpmn.api.property.simulation;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.core.api.annotation.property.DefaultValue;
import org.wirez.core.api.annotation.property.Property;
import org.wirez.core.api.annotation.property.Value;
import org.wirez.core.api.definition.property.BaseProperty;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.ColorType;

@Portable
@Bindable
@Property
public class DistributionType extends BaseProperty implements BPMNProperty {

    public static final String ID = "distributiontype";

    @DefaultValue
    public static final String DEFAULT_VALUE = "normal";

    @Value
    private String value = DEFAULT_VALUE;
    
    public DistributionType() {
        super(ID, 
                "Distribution Type", "Distribution Type.", 
                false, false);
    }

    @Override
    public PropertyType getType() {
        return new ColorType();
    }

    public String getDefaultValue() {
        return DEFAULT_VALUE;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
