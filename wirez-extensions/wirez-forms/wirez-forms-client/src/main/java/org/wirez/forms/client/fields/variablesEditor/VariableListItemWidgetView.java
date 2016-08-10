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

package org.wirez.forms.client.fields.variablesEditor;

import org.jboss.errai.ui.client.widget.HasModel;
import org.wirez.forms.client.fields.model.Variable.VariableType;
import org.wirez.forms.client.fields.model.VariableRow;
import org.wirez.forms.client.fields.widgets.ListBoxValues;

public interface VariableListItemWidgetView extends HasModel<VariableRow> {

    String CUSTOM_PROMPT = "Custom" + ListBoxValues.EDIT_SUFFIX;
    String ENTER_TYPE_PROMPT = "Enter type" + ListBoxValues.EDIT_SUFFIX;

    void init();

    void setParentWidget(VariablesEditorWidgetView.Presenter parentWidget);

    void notifyModelChanged();

    void setDataTypes(ListBoxValues dataTypeListBoxValues);

    boolean isDuplicateName(String name);

    VariableType getVariableType();

    String getDataType();

    void setDataType(String dataType);

    String getCustomDataType();

    void setCustomDataType(String customDataType);

}
