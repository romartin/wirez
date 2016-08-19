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

package org.wirez.forms.client.fields.assignmentsEditor;

import java.util.List;

import org.wirez.forms.client.fields.model.AssignmentRow;

public interface ActivityDataIOEditorWidgetView {

    interface Presenter {
        void handleAddClick();
    }

    void init(Presenter presenter);

    void showOnlySingleEntryAllowed();

    int getAssignmentsCount();

    void setTableTitleInputSingle();

    void setTableTitleInputMultiple();

    void setTableTitleOutputSingle();

    void setTableTitleOutputMultiple();

    void setProcessVarAsSource();

    void setProcessVarAsTarget();

    void setTableDisplayStyle();

    void setNoneDisplayStyle();

    void setAssignmentRows(List<AssignmentRow> rows);

    List<AssignmentRow> getAssignmentRows();

    AssignmentListItemWidgetView getAssignmentWidget(int index);

    void setVisible(boolean visible);
}
