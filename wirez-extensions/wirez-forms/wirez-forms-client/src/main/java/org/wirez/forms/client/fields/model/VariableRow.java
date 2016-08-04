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

package org.wirez.forms.client.fields.model;

import org.jboss.errai.databinding.client.api.Bindable;

@Bindable
public class VariableRow {

    private long id;

    private String name;

    private Variable.VariableType variableType = Variable.VariableType.PROCESS;

    private String dataType;

    private String customDataType;

    // Field which is incremented for each row.
    // Required to implement equals function which needs a unique field
    private static long lastId = 0;

    public VariableRow() {
        this.id = lastId++;
        this.variableType = variableType;
    }

    public VariableRow(Variable.VariableType variableType, String name, String dataType, String customDataType) {
        this.id = lastId++;
        this.variableType = variableType;
        this.name = name;
        this.dataType = dataType;
        this.customDataType = customDataType;
    }

    public VariableRow(Variable variable) {
        this.id = lastId++;
        this.variableType = variable.getVariableType();
        this.name = variable.getName();
        if (variable.isCustomDataType()) {
            this.customDataType = variable.getDataType();
        } else {
            this.dataType = variable.getDataType();
            ;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Variable.VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(Variable.VariableType variableType) {
        this.variableType = variableType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCustomDataType() {
        return customDataType;
    }

    public void setCustomDataType(String customDataType) {
        this.customDataType = customDataType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VariableRow other = (VariableRow) obj;
        return (id == other.id);
    }

    @Override
    public String toString() {
        return "VariableRow [variableType=" + variableType.toString() + ", name=" + name + ", dataType=" + dataType + ", customDataType=" + customDataType + "]";
    }

}