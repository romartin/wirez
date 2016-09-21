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
package org.wirez.bpmn.definition.property.dimensions;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.Slider;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.definition.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class CircleDimensionSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Shape Dimensions";

    @Property
    @FieldDef(label = "Radius", property = "value")
    @Slider( min = 25.0, max = 50.0, step = 1, precision = 0.0 )
    protected Radius radius;

    public CircleDimensionSet() {
        this( new Radius( Radius.defaultValue ));
    }

    public CircleDimensionSet(Double radius) {
        this( new Radius( radius ));
    }

    public CircleDimensionSet( @MapsTo("radius") Radius radius ) {
        this.radius = radius;
    }

    public Radius getRadius() {
        return radius;
    }

    public void setRadius(Radius radius) {
        this.radius = radius;
    }
}
