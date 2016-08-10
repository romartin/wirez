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

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.text.shared.Renderer;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.ValueListBox;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.workbench.events.NotificationEvent;
import org.wirez.forms.client.fields.model.Variable.VariableType;
import org.wirez.forms.client.fields.model.VariableRow;
import org.wirez.forms.client.fields.widgets.ComboBox;
import org.wirez.forms.client.fields.widgets.ComboBoxView;
import org.wirez.forms.client.fields.widgets.ListBoxValues;
import org.wirez.forms.client.fields.widgets.VariableNameTextBox;

/**
 * A templated widget that will be used to display a row in a table of
 * {@link VariableRow}s.
 * <p>
 * The Name field of VariableRow is Bound, but other fields are not bound because
 * they use a combination of ListBox and TextBox to implement a drop-down combo
 * to hold the values.
 */
@Templated("VariablesEditorWidget.html#variableRow")
public class VariableListItemWidgetViewImpl implements VariableListItemWidgetView, ComboBoxView.ModelPresenter {

    /**
     * Errai's data binding module will automatically bind the provided instance
     * of the model (see {@link #setModel(VariableRow)}) to all fields annotated
     * with {@link Bound}. If not specified otherwise, the bindings occur based on
     * matching field names (e.g. variableRow.name will automatically be kept in
     * sync with the data-field "name")
     */
    @Inject
    @AutoBound
    protected DataBinder<VariableRow> variableRow;

    @Inject
    @Bound
    @DataField
    protected VariableNameTextBox name;

    private boolean allowDuplicateNames = false;
    private String duplicateNameErrorMessage = "A Variable with this name already exists";

    private String currentValue;

    @DataField
    protected ValueListBox<String> dataType = new ValueListBox<String>(new Renderer<String>() {
        public String render(String object) {
            String s = "";
            if (object != null) {
                s = object.toString();
            }
            return s;
        }

        public void render(String object, Appendable appendable) throws IOException {
            String s = render(object);
            appendable.append(s);
        }
    });

    @Inject
    @DataField
    protected TextBox customDataType;

    @Inject
    protected ComboBox dataTypeComboBox;

    @Inject
    protected Event<NotificationEvent> notification;

    @Inject
    @DataField
    protected Button deleteButton;

    /**
     * Required for implementation of Delete button.
     */
    private VariablesEditorWidgetView.Presenter parentWidget;

    public void setParentWidget(VariablesEditorWidgetView.Presenter parentWidget) {
        this.parentWidget = parentWidget;
    }

    @Override
    public void setTextBoxModelValue(final TextBox textBox, String value) {
        setCustomDataType(value);
    }

    @Override
    public void setListBoxModelValue(final ValueListBox<String> listBox, String value) {
        setDataType(value);
    }

    @Override
    public String getModelValue(final ValueListBox<String> listBox) {
        String value = getCustomDataType();
        if (value == null || value.isEmpty()) {
            value = getDataType();
        }
        return value;
    }

    @PostConstruct
    public void init() {
        // Configure dataType and customDataType controls
        dataTypeComboBox.init(this, dataType, customDataType, false,
                VariableListItemWidgetView.CUSTOM_PROMPT,
                VariableListItemWidgetView.ENTER_TYPE_PROMPT);

        name.setRegExp("^[a-zA-Z0-9\\-\\.\\_]*$",
                "Removed invalid characters from name",
                "Invalid character in name");

        customDataType.addKeyDownHandler(new KeyDownHandler() {
            @Override public void onKeyDown(KeyDownEvent event) {
                int iChar = event.getNativeKeyCode();
                if (iChar == ' ') {
                    event.preventDefault();
                }
            }
        });

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
    public VariableRow getModel() {
        return variableRow.getModel();
    }

    @Override
    public void setModel(VariableRow model) {
        variableRow.setModel(model);
        initVariableControls();
        currentValue = getModel().toString();
    }

    @Override
    public VariableType getVariableType() {
        return getModel().getVariableType();
    }

    @Override
    public String getDataType() {
        return getModel().getDataType();
    }

    @Override
    public void setDataType(String dataType) {
        getModel().setDataType(dataType);

        if (dataType != null && dataType.length() > 0) {
            notifyModelChanged();
        }
    }

    @Override
    public String getCustomDataType() {
        return getModel().getCustomDataType();
    }

    @Override
    public void setCustomDataType(String customDataType) {
        getModel().setCustomDataType(customDataType);
    }

    @Override
    public void setDataTypes(ListBoxValues dataTypeListBoxValues) {
        dataTypeComboBox.setCurrentTextValue("");
        dataTypeComboBox.setListBoxValues(dataTypeListBoxValues);
        dataTypeComboBox.setShowCustomValues(true);
        String cdt = getCustomDataType();
        if (cdt != null && !cdt.isEmpty()) {
            dataTypeComboBox.addCustomValueToListBoxValues(cdt, "");
        }
    }

    @Override
    public boolean isDuplicateName(String name) {
        return parentWidget.isDuplicateName(name);
    }

    @EventHandler("deleteButton")
    public void handleDeleteButton(ClickEvent e) {
        parentWidget.removeVariable(getModel());
    }

    /**
     * Updates the display of this row according to the state of the
     * corresponding {@link VariableRow}.
     */
    private void initVariableControls() {
        deleteButton.setIcon(IconType.TRASH);

        String cdt = getCustomDataType();
        if (cdt != null && !cdt.isEmpty()) {
            customDataType.setValue(cdt);
            dataType.setValue(cdt);
        } else if (getDataType() != null) {
            dataType.setValue(getDataType());
        }
    }

    @Override
    public void notifyModelChanged() {
        String oldValue = currentValue;
        currentValue = getModel().toString();

        if (oldValue == null) {
            if (currentValue != null && currentValue.length() > 0) {
                parentWidget.notifyModelChanged();
            }
        }
        else if (!oldValue.equals(currentValue)) {
            parentWidget.notifyModelChanged();
        }
    }

}
