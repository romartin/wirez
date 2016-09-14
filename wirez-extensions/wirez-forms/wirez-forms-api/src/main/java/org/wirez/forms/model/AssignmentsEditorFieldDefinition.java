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

package org.wirez.forms.model;

import javax.validation.constraints.Pattern;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.model.FieldDefinition;
import org.wirez.forms.meta.definition.AssignmentsEditor;

@Portable
@Bindable
public class AssignmentsEditorFieldDefinition extends FieldDefinition {

    public static final String CODE = "AssignmentsEditor";


    @FieldDef( label = "Default value")
    @AssignmentsEditor
    private String defaultValue;

    public AssignmentsEditorFieldDefinition() {
        super( CODE );
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    @Override
    protected void doCopyFrom( FieldDefinition other ) {
        if ( other instanceof AssignmentsEditorFieldDefinition) {
            this.setDefaultValue( ( (AssignmentsEditorFieldDefinition) other ).getDefaultValue() );
        }
    }
}
