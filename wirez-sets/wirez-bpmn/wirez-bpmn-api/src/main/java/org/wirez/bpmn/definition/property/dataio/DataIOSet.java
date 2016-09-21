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

package org.wirez.bpmn.definition.property.dataio;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;
import org.wirez.forms.meta.definition.AssignmentsEditor;

@Portable
@Bindable
@PropertySet
public class DataIOSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Task Data";

    @Property
    @FieldDef(label = "Assignments", property = "value")
    @AssignmentsEditor
    @Valid
    private AssignmentsInfo assignmentsinfo;

    public DataIOSet() {
        this( new AssignmentsInfo());
    }

    public DataIOSet(@MapsTo("assignmentsinfo") AssignmentsInfo assignmentsinfo) {
        this.assignmentsinfo = assignmentsinfo;
    }

    public DataIOSet( String assignmentsinfo) {
        this.assignmentsinfo = new AssignmentsInfo(assignmentsinfo);
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public AssignmentsInfo getAssignmentsinfo() {
        return assignmentsinfo;
    }

    public void setAssignmentsinfo( AssignmentsInfo assignmentsinfo) {
        this.assignmentsinfo = assignmentsinfo;
    }

}
