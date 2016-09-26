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

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.workbench.events.NotificationEvent;
import org.wirez.forms.client.fields.model.AssigneeRow;

/**
 * A templated widget that will be used to display a row in a table of
 * {@link AssigneeRow}s.
 * <p>
 * The Name field of AssigneeRow is Bound, but other fields are not bound because
 * they use a combination of ListBox and TextBox to implement a drop-down combo
 * to hold the values.
 */
@Templated("AssigneeEditorWidget.html#assigneeRow")
public class AssigneeListItemWidgetViewImpl implements AssigneeListItemWidgetView {

    /**
     * Errai's data binding module will automatically bind the provided instance
     * of the model (see {@link #setModel(AssigneeRow)}) to all fields annotated
     * with {@link Bound}. If not specified otherwise, the bindings occur based on
     * matching field names (e.g. assigneeRow.name will automatically be kept in
     * sync with the data-field "name")
     */
    @Inject
    @AutoBound
    protected DataBinder<AssigneeRow> assigneeRow;

    @Inject
    @Bound
    @DataField
    protected TextBox name;

    private boolean allowDuplicateNames = false;
    private String duplicateNameErrorMessage = "An assignee with this name already exists";

    private String currentValue;

    @Inject
    protected Event<NotificationEvent> notification;

    @Inject
    @DataField
    protected Button deleteButton;

    /**
     * Required for implementation of Delete button.
     */
    private AssigneeEditorWidgetView.Presenter parentWidget;

    public void setParentWidget(AssigneeEditorWidgetView.Presenter parentWidget) {
        this.parentWidget = parentWidget;
    }

    @PostConstruct
    public void init() {
        name.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                if (!allowDuplicateNames) {
                    String value = name.getText();
                    if (isDuplicateName(value)) {
                        notification.fire(new NotificationEvent(duplicateNameErrorMessage, NotificationEvent.NotificationType.ERROR));
                        name.setValue("");
                        ValueChangeEvent.fire(name, "");
                    }
                }
                notifyModelChanged();
            }
        });

    }

    @Override
    public AssigneeRow getModel() {
        return assigneeRow.getModel();
    }

    @Override
    public void setModel(AssigneeRow model) {
        assigneeRow.setModel(model);
        initAssigneeControls();
        currentValue = getModel().toString();
    }

    @Override
    public boolean isDuplicateName(String name) {
        return parentWidget.isDuplicateName(name);
    }

    @EventHandler("deleteButton")
    public void handleDeleteButton(ClickEvent e) {
        parentWidget.removeAssignee(getModel());
    }

    /**
     * Updates the display of this row according to the state of the
     * corresponding {@link AssigneeRow}.
     */
    private void initAssigneeControls() {
        deleteButton.setIcon(IconType.TRASH);
    }

    @Override
    public void notifyModelChanged() {
        String oldValue = currentValue;
        currentValue = getModel().toString();

        if (oldValue == null) {
            if (currentValue != null && currentValue.length() > 0) {
                parentWidget.notifyModelChanged();
            }
        } else if (!oldValue.equals(currentValue)) {
            parentWidget.notifyModelChanged();
        }
    }

}
