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

import java.util.Iterator;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.jboss.errai.marshalling.client.Marshalling;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.wirez.bpmn.definition.BPMNDiagram;
import org.wirez.bpmn.definition.UserTask;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.variables.ProcessVariables;
import org.wirez.core.client.session.CanvasSessionManager;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;
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

    @Inject CanvasSessionManager canvasSessionManager;

    private UserTask userTask;

    private String assignmentsInfo;

    @EventHandler( "assignmentsButton" )
    public void onClickAssignmentsButton( ClickEvent clickEvent ) {
        showAssignmentsDialog();
    }

    @EventHandler( "assignmentsTextBox" )
    public void onClickAssignmentsTextBox( ClickEvent clickEvent ) {
        showAssignmentsDialog();
    }

    @Override
    public String getValue() {
        return assignmentsInfo;
    }

    @Override
    public void setValue( String value ) {
        setValue( value, false );
    }

    @Override
    public void setValue( String value, boolean fireEvents ) {
        String oldValue = assignmentsInfo;

        assignmentsInfo = value;

        initTextBox();

        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, assignmentsInfo);
        }
    }

    protected void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }

    protected void initTextBox() {
        assignmentsTextBox.setText(assignmentsInfo);
    }

    @Override
    public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
        return addHandler( handler, ValueChangeEvent.getType() );
    }

    public void showAssignmentsDialog() {
        String taskName = "Task";
        String datainputset = "";
        String dataoutputset = "";
        if (userTask != null) {
            if(userTask.getGeneral() != null && userTask.getGeneral().getName() != null &&
                userTask.getGeneral().getName().getValue() != null && userTask.getGeneral().getName().getValue().length() > 0) {
                taskName = userTask.getGeneral().getName().getValue();
            }
            if (userTask.getDataIOSet() != null) {
                DataIOSet dataIOSet = userTask.getDataIOSet();
                if (dataIOSet.getInputData() != null && dataIOSet.getInputData().getValue() != null) {
                    datainputset = dataIOSet.getInputData().getValue();
                }
                if (dataIOSet.getInputData() != null && dataIOSet.getInputData().getValue() != null) {
                    dataoutputset = dataIOSet.getOutputData().getValue();
                }
            }
        }

//        Window.alert("assignmentsInfo = " + assignmentsInfo + "\ndatainputset = " + datainputset + "\ndataoutputset = " + dataoutputset);
        String processvars = getProcessVariables();
        // TODO: also get dataTypes from server
        String datatypes = "String:String, Integer:Integer, Boolean:Boolean, Float:Float, Object:Object";
        // TODO: use full set of disallowedpropertynames
        String disallowedpropertynames = "Skippable";

        ActivityDataIOEditor.GetDataCallback callback = new ActivityDataIOEditor.GetDataCallback() {
            @Override
            public void getData( String assignmentDataJson ) {
//                Window.alert("assignmentData = " + assignmentDataJson);
                AssignmentData assignmentData = Marshalling.fromJSON(assignmentDataJson, AssignmentData.class);

                String inputVariablesString = assignmentData.getInputVariablesString();
                userTask.getDataIOSet().getInputData().setValue(inputVariablesString);
                String outputVariablesString = assignmentData.getOutputVariablesString();
                userTask.getDataIOSet().getOutputData().setValue(outputVariablesString);

                String assignmentsString = assignmentData.getAssignmentsString();
                setValue(assignmentsString, true);
            }
        };

        showDataIOEditor(taskName, null, datainputset, null, dataoutputset,
                processvars, assignmentsInfo, datatypes, disallowedpropertynames, callback);
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

    protected String getProcessVariables() {
        Diagram diagram = canvasSessionManager.getCurrentSession().getCanvasHandler().getDiagram();
        Iterator<Element> it = diagram.getGraph().nodes().iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getContent() instanceof View) {
                Object oDefinition = ((View) element.getContent()).getDefinition();
                if (oDefinition instanceof BPMNDiagram) {
                    BPMNDiagram bpmnDiagram = (BPMNDiagram) oDefinition;
                    ProcessVariables variables = bpmnDiagram.getProcessData().getProcessVariables();
                    if (variables != null) {
                        return variables.getValue();
                    }
                    break;
                }
            }
        }
        return null;
    }
}
