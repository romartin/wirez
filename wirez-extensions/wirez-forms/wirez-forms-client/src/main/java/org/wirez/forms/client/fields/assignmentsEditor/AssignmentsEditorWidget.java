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

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.UIObject;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.ext.widgets.common.client.colorpicker.ColorPickerDialog;
import org.wirez.forms.client.fields.model.AssignmentData;
import org.wirez.forms.client.fields.model.Variable;

@Dependent
@Templated
public class AssignmentsEditorWidget extends Composite implements HasValue<String> {

    @Inject
    @DataField
    private Button assignmentsButton;

    @Inject
    @DataField
    private TextBox assignmentsTextBox;

    @Inject
    private ActivityDataIOEditor activityDataIOEditor;

    private String assignments;

    @EventHandler( "assignmentsButton" )
    public void onClickAssignmentsButton( ClickEvent clickEvent ) {
        testDataIOEditor();
    }

    @EventHandler( "assignmentsTextBox" )
    public void onClickAssignmentsTextBox( ClickEvent clickEvent ) {
        testDataIOEditor();
    }


    protected void showAssignmentsDialog( final UIObject owner ) {
        final ColorPickerDialog dlg = new ColorPickerDialog();

        dlg.getElement().getStyle().setZIndex(9999);

        dlg.addDialogClosedHandler( event -> {
            if ( !event.isCanceled() ) {
                setValue( "#" + dlg.getColor(), true );
            }
        } );

        String assignments = getValue();

        if (assignments == null || assignments.length() == 0) {
            assignments = "f62843";
        }

        if (assignments.startsWith("#")) {
            assignments = assignments.substring(1, assignments.length());
        }

        dlg.setColor( assignments );

        dlg.showRelativeTo( owner );
    }

    @Override
    public String getValue() {
        return assignments;
    }

    @Override
    public void setValue( String value ) {
        setValue( value, false );
    }

    @Override
    public void setValue( String value, boolean fireEvents ) {
        String oldValue = assignments;

        assignments = value;

        initTextBox();

        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, assignments);
        }
    }

    protected void initTextBox() {
        assignmentsTextBox.getElement().getStyle().setBackgroundColor( assignments );
    }

    @Override
    public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
        return addHandler( handler, ValueChangeEvent.getType() );
    }

    public void testDataIOEditor() {
        //    AssignmentData _assignmentData = new AssignmentData("inStr:String,inInt1:Integer,inCustom1:org.jdl.Custom,inStrConst:String,Skippable",
//            "outStr1:String,outInt1:Integer,outCustom1:org.jdl.Custom",
//            "str1:String,int1:Integer,custom1:org.jdl.Custom",
//            "[din]str1->inStr,[din]int1->inInt1,[din]custom1->inCustom1,[din]inStrConst=TheString,[dout]outStr1->str1,[dout]outInt1->int1,[dout]outCustom1->custom1",
//            "String:String, Integer:Integer, Boolean:Boolean, Float:Float, Object:Object");
        String taskName = "Task1";
        String datainput = null;
        String datainputset = "inStr:String,inInt1:Integer,inCustom1:org.jdl.Custom,inStrConst:String,Skippable";
        String dataoutput = null;
        String dataoutputset = "outStr1:String,outInt1:Integer,outCustom1:org.jdl.Custom";
        String processvars = "str1:String,int1:Integer,custom1:org.jdl.Custom";
        String assignments = "[din]str1->inStr,[din]int1->inInt1,[din]custom1->inCustom1,[din]inStrConst=TheString,[dout]outStr1->str1,[dout]outInt1->int1,[dout]outCustom1->custom1";
        String datatypes = "String:String, Integer:Integer, Boolean:Boolean, Float:Float, Object:Object";
        String disallowedpropertynames = "Skippable";

        ActivityDataIOEditor.GetDataCallback callback = new ActivityDataIOEditor.GetDataCallback() {
            @Override
            public void getData( String assignmentData ) {
                Window.alert(assignmentData);
            }
        };

        showDataIOEditor(taskName, datainput, datainputset, dataoutput, dataoutputset, processvars, assignments, datatypes, disallowedpropertynames, callback);
    }

    public void showDataIOEditor( final String taskName,
            final String datainput,
            final String datainputset,
            final String dataoutput,
            final String dataoutputset,
            final String processvars,
            final String assignments,
            final String datatypes,
            final String disallowedpropertynames,
            final ActivityDataIOEditor.GetDataCallback callback ) {


        activityDataIOEditor.setCallback(callback);

        String inputvars = null;
        boolean hasInputVars = false;
        boolean isSingleInputVar = false;
        if ( datainput != null ) {
            inputvars = datainput;
            hasInputVars = true;
            isSingleInputVar = true;
        }
        if ( datainputset != null ) {
            inputvars = datainputset;
            hasInputVars = true;
            isSingleInputVar = false;
        }

        String outputvars = null;
        boolean hasOutputVars = false;
        boolean isSingleOutputVar = false;
        if ( dataoutput != null ) {
            outputvars = dataoutput;
            hasOutputVars = true;
            isSingleOutputVar = true;
        }
        if ( dataoutputset != null ) {
            outputvars = dataoutputset;
            hasOutputVars = true;
            isSingleOutputVar = false;
        }

        AssignmentData assignmentData = new AssignmentData( inputvars, outputvars, processvars, assignments, datatypes, disallowedpropertynames );
        assignmentData.setVariableCountsString(hasInputVars, isSingleInputVar, hasOutputVars, isSingleOutputVar);
        activityDataIOEditor.setAssignmentData( assignmentData );
        activityDataIOEditor.setDisallowedPropertyNames( assignmentData.getDisallowedPropertyNames() );
        activityDataIOEditor.setInputAssignmentRows( assignmentData.getAssignmentRows( Variable.VariableType.INPUT ) );
        activityDataIOEditor.setOutputAssignmentRows( assignmentData.getAssignmentRows( Variable.VariableType.OUTPUT ) );
        activityDataIOEditor.setDataTypes( assignmentData.getDataTypes(), assignmentData.getDataTypeDisplayNames() );
        activityDataIOEditor.setProcessVariables( assignmentData.getProcessVariableNames() );

        activityDataIOEditor.configureDialog( taskName, hasInputVars, isSingleInputVar, hasOutputVars, isSingleOutputVar );
        activityDataIOEditor.show();
    }

}
