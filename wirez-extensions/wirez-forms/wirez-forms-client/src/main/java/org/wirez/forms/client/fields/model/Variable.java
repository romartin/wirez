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

import java.util.List;

public class Variable {

    public enum VariableType {
        INPUT,
        OUTPUT,
        PROCESS
    }

    private VariableType variableType;

    private String name;

    private String dataType;

    private boolean isCustomDataType = false;

    public Variable(VariableType variableType) {
        this.variableType = variableType;
    }

    public Variable(VariableType variableType, String name) {
        this.variableType = variableType;
        this.name = name;
    }

    public Variable(VariableType variableType, String name, String dataType, boolean isCustomDataType) {
        this.name = name;
        this.variableType = variableType;
        this.dataType = dataType;
        this.isCustomDataType = isCustomDataType;
    }

    public Variable(VariableRow variableRow) {
        this.variableType = variableRow.getVariableType();
        this.name = variableRow.getName();
        if (variableRow.getCustomDataType() != null && variableRow.getCustomDataType().length() > 0) {
            this.isCustomDataType = true;
            this.dataType = variableRow.getCustomDataType();
        } else {
            this.isCustomDataType = false;
            this.dataType = variableRow.getDataType();
        }
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
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

    public boolean isCustomDataType() {
        return isCustomDataType;
    }

    public void setCustomDataType(boolean isCustomDataType) {
        this.isCustomDataType = isCustomDataType;
    }

    public String toString() {
        if (name != null && !name.isEmpty()) {
            StringBuilder sb = new StringBuilder().append(name);
            if (dataType != null && !dataType.isEmpty()) {
                sb.append(':').append(dataType);
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * Deserializes a variable, checking whether the datatype is custom or not
     * @param s
     * @param variableType
     * @param dataTypes
     * @return
     */
    public static Variable deserialize(String s, VariableType variableType, List<String> dataTypes) {
        Variable var = new Variable(variableType);
        String[] varParts = s.split(":");
        if (varParts.length > 0) {
            String name = varParts[0];
            if (!name.isEmpty()) {
                var.setName(name);
                if (varParts.length == 2) {
                    String dataType = varParts[1];
                    if (!dataType.isEmpty()) {
                        var.setDataType(dataType);
                        if (dataTypes == null || dataTypes.contains(dataType)) {
                            var.setCustomDataType(false);
                        } else {
                            var.setCustomDataType(true);
                        }
                    }
                }
            }
        }
        return var;
    }

    /**
     * Deserializes a variable, NOT checking whether the datatype is custom
     * @param s
     * @param variableType
     * @return
     */
    public static Variable deserialize(String s, VariableType variableType) {
        return deserialize(s, variableType, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Variable)) {
            return false;
        }

        Variable variable = (Variable) o;

        if (getVariableType() != variable.getVariableType()) {
            return false;
        }
        if (getName() != null ? !getName().equals(variable.getName()) : variable.getName() != null) {
            return false;
        }
        if (getDataType() != null ? !getDataType().equals(variable.getDataType()) : variable.getDataType() != null) {
            return false;
        }
        return (isCustomDataType() == variable.isCustomDataType());

    }

    @Override
    public int hashCode() {
        int result = getVariableType() != null ? getVariableType().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDataType() != null ? getDataType().hashCode() : 0);
        result = 31 * result + Boolean.hashCode(isCustomDataType());
        return result;
    }
}