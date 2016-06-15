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

package org.wirez.bpmn.definition.property.dataio;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class DataIOSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Data IO";

    @Property
    @FieldDef(label = "Input Data", property = "value")
    @Valid
    private InputData inputData;

    @Property
    @FieldDef(label = "Output Data", property = "value")
    @Valid
    private OutputData outputData;

    @Property
    @FieldDef(label = "Assignments", property = "value")
    @Valid
    private Assignments assignments;

    public DataIOSet() {
        this( new InputData(), new OutputData(), new Assignments());
    }

    public DataIOSet(@MapsTo("inputData") InputData inputData,
            @MapsTo("outputData") OutputData outputData,
            @MapsTo("assignments") Assignments assignments ) {
        this.inputData = inputData;
        this.outputData = outputData;
        this.assignments = assignments;
    }

    public DataIOSet( String inputData, String outputData, String assignments) {
        this.inputData = new InputData( inputData );
        this.outputData = new OutputData( outputData );
        this.assignments = new Assignments( assignments );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public InputData getInputData() {
        return inputData;
    }

    public OutputData getOutputData() {
        return outputData;
    }

    public Assignments getAssignments() {
        return assignments;
    }

    public void setInputData( InputData inputData ) {
        this.inputData = inputData;
    }

    public void setOutputData( OutputData outputData ) {
        this.outputData = outputData;
    }

    public void setAssignments( Assignments assignments ) {
        this.assignments = assignments;
    }

}
