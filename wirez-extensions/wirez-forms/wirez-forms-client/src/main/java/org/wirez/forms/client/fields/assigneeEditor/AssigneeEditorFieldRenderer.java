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

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.forms.dynamic.client.rendering.FieldRenderer;
import org.wirez.forms.client.fields.model.Assignee;
import org.wirez.forms.client.fields.model.AssigneeRow;
import org.wirez.forms.client.fields.util.StringUtils;
import org.wirez.forms.model.AssigneeEditorFieldDefinition;

@Dependent
public class AssigneeEditorFieldRenderer extends FieldRenderer<AssigneeEditorFieldDefinition>
        implements AssigneeEditorWidgetView.Presenter {

    private AssigneeEditorWidgetView view;

    @Inject
    public AssigneeEditorFieldRenderer(AssigneeEditorWidgetView assigneeEditor) {
        this.view = assigneeEditor;
    }

    @Override
    public String getName() {
        return AssigneeEditorFieldDefinition.CODE;
    }

    @Override
    public void initInputWidget() {
        view.init(this);
    }

    @Override
    public IsWidget getInputWidget() {
        return (AssigneeEditorWidgetViewImpl) view;
    }

    @Override
    public String getSupportedCode() {
        return AssigneeEditorFieldDefinition.CODE;
    }

    @Override
    public void doSave() {
        view.doSave();
    }

    @Override
    public void addAssignee() {
        List<AssigneeRow> as = view.getAssigneeRows();
        if (as.isEmpty()) {
            view.setTableDisplayStyle();
        }

        AssigneeRow newAssignee = new AssigneeRow();
        as.add(newAssignee);

        AssigneeListItemWidgetView widget = view.getAssigneeWidget(view.getAssigneeRowsCount() - 1);
        widget.setParentWidget(this);
    }

    @Override
    public void notifyModelChanged() {
        doSave();
    }

    @Override
    public List<AssigneeRow> deserializeAssignees(String s) {
        List<AssigneeRow> assigneeRows = new ArrayList<AssigneeRow>();
        if (s != null && !s.isEmpty()) {
            String[] as = s.split(",");
            for (String a : as) {
                if (!a.isEmpty()) {
                    Assignee assignee = Assignee.deserialize(a);
                    if (assignee != null && assignee.getName() != null && !assignee.getName().isEmpty()) {
                        assigneeRows.add(new AssigneeRow(assignee));
                    }
                }
            }
        }

        return assigneeRows;
    }

    @Override
    public String serializeAssignees(List<AssigneeRow> assigneeRows) {
        List<Assignee> assignees = new ArrayList<Assignee>();
        for (AssigneeRow row : assigneeRows) {
            if (row.getName() != null && row.getName().length() > 0) {
                assignees.add(new Assignee(row));
            }
        }
        return StringUtils.getStringForList(assignees);
    }

    /**
     * Tests whether a Row name occurs more than once in the list of rows
     * @param name
     * @return
     */
    public boolean isDuplicateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        List<AssigneeRow> as = view.getAssigneeRows();
        if (as != null && !as.isEmpty()) {
            int nameCount = 0;
            for (AssigneeRow row : as) {
                if (name.trim().compareTo(row.getName()) == 0) {
                    nameCount++;
                    if (nameCount > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeAssignee(AssigneeRow assigneeRow) {
        view.getAssigneeRows().remove(assigneeRow);
        doSave();
    }

}
