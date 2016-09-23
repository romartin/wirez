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
package org.wirez.bpmn.definition.property.task;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.ListBox;
import org.kie.workbench.common.forms.metaModel.SelectorDataProvider;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.definition.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class ReusableSubprocessTaskExecutionSet implements BPMNPropertySet  {
    @Name
    public static final transient String propertySetName = "Implementation/Execution";

    @Property
    @FieldDef( label = "Called Element" )
    @ListBox
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.REMOTE,
            className = "org.wirez.bpmn.backend.dataproviders.CalledElementFormProvider")
    @NotEmpty
    protected String calledelement = "";

    public ReusableSubprocessTaskExecutionSet() {
        this("");
    }

    public ReusableSubprocessTaskExecutionSet(@MapsTo("calledelement") String calledelement) {
        this.calledelement = calledelement;
    }

    public String getCalledelement() {
        return calledelement;
    }

    public void setCalledelement(String calledelement) {
        this.calledelement = calledelement;
    }
}
