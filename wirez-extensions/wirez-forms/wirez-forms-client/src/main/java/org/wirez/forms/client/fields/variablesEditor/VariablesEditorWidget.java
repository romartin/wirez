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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import org.gwtbootstrap3.client.ui.TextBox;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Dependent
@Templated
public class VariablesEditorWidget extends Composite implements HasValue<String> {

    @Inject
    @DataField
    private TextBox variablesTextBox;

    private String variables;

    @EventHandler( "variablesTextBox" )
    public void onBlurHandler(BlurEvent blurEvent) {
        setValue(variablesTextBox.getValue(), true);
    }

    @Override
    public String getValue() {
        return variables;
    }

    @Override
    public void setValue( String value ) {
        setValue( value, false );
    }

    @Override
    public void setValue( String value, boolean fireEvents ) {
        String oldValue = variables;

        variables = value;

        initTextBox();

        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, variables);
        }
    }

    protected void initTextBox() {
        variablesTextBox.setValue(variables);
    }

    @Override
    public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
        return addHandler( handler, ValueChangeEvent.getType() );
    }
}
