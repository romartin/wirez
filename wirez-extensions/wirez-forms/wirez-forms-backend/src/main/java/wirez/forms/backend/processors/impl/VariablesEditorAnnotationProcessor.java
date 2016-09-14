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

package wirez.forms.backend.processors.impl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.drools.workbench.models.datamodel.oracle.Annotation;
import org.kie.workbench.common.forms.dynamic.backend.server.impl.FieldSetting;
import org.kie.workbench.common.forms.dynamic.backend.server.impl.processors.AbstractFieldAnnotationProcessor;
import org.kie.workbench.common.forms.dynamic.service.TransformerContext;
import org.wirez.forms.meta.definition.VariablesEditor;
import org.wirez.forms.model.VariablesEditorFieldDefinition;
import org.wirez.forms.service.fieldProviders.VariablesEditorFieldProvider;

@Dependent
public class VariablesEditorAnnotationProcessor extends AbstractFieldAnnotationProcessor<VariablesEditorFieldDefinition, VariablesEditorFieldProvider> {

    @Inject
    public VariablesEditorAnnotationProcessor( VariablesEditorFieldProvider fieldProvider ) {
        super( fieldProvider );
    }

    @Override
    protected void initField( VariablesEditorFieldDefinition field,
                              Annotation annotation,
                              FieldSetting fieldSetting,
                              TransformerContext context ) {
        field.setDefaultValue( (String) annotation.getParameters().get( "defaultValue" ) );
    }

    @Override
    protected Class<VariablesEditor> getSupportedAnnotation() {
        return VariablesEditor.class;
    }
}
