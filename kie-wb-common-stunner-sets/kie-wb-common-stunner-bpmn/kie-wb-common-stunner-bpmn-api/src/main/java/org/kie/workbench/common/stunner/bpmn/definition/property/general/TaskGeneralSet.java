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
package org.kie.workbench.common.stunner.bpmn.definition.property.general;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.TextArea;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.bpmn.definition.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphProperty;

import javax.validation.Valid;

@Portable
@Bindable
@PropertySet
public class TaskGeneralSet implements BPMNPropertySet {

    @org.kie.workbench.common.stunner.core.definition.annotation.Name
    public static final transient String propertySetName = "General";

    @Property
    @FieldDef(label = "Name", property = "value", position = 0)
    @Valid
    private Name name;

    @Property
    @FieldDef( label = "Documentation", property = "value", position = 1)
    @TextArea( rows = 3)
    @Valid
    private Documentation documentation;

    @Property
    @FieldDef(label = "Task Type", property = "value", position = 2)
    @MorphProperty( binder = BaseTask.TaskTypeMorphPropertyBinding.class )
    protected TaskType taskType;

    public TaskGeneralSet() {
        this( new Name(), new Documentation(), new TaskType( TaskTypes.NONE ));
    }

    public TaskGeneralSet(@MapsTo("name") Name name,
                          @MapsTo("documentation") Documentation documentation,
                          @MapsTo("taskType") TaskType taskType) {
        this.name = name;
        this.documentation = documentation;
        this.taskType = taskType;
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }
}
