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

package org.wirez.forms.client.fields.assigneeEditor;

import org.jboss.errai.ui.client.widget.HasModel;
import org.wirez.forms.client.fields.model.AssigneeRow;
import org.wirez.forms.client.fields.util.ListBoxValues;

public interface AssigneeListItemWidgetView extends HasModel<AssigneeRow> {

    String CUSTOM_PROMPT = "New" + ListBoxValues.EDIT_SUFFIX;
    String ENTER_TYPE_PROMPT = "Enter name" + ListBoxValues.EDIT_SUFFIX;

    void init();

    void setParentWidget(AssigneeEditorWidgetView.Presenter parentWidget);

    void notifyModelChanged();

    void setNames(ListBoxValues nameListBoxValues);

    boolean isDuplicateName(String name);

    String getName();

    void setName(String name);

    String getCustomName();

    void setCustomName(String customName);

}
