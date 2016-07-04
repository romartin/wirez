/*
 * Copyright 2016  Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.bpmn.definition.property.variables;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class VariablesSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Variables";
    
    @Property
    @FieldDef(label = "Variables", property = "value")
    @Valid
    private Variables variables;

    public VariablesSet() {
        this( new Variables());
    }

    public VariablesSet(@MapsTo("variables") Variables variables ) {
        this.variables = variables;
    }

    public VariablesSet( String variables) {
        this.variables = new Variables( variables );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables( Variables variables ) {
        this.variables = variables;
    }

}
