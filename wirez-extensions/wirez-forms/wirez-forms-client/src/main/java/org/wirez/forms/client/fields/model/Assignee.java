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

public class Assignee {

    private String name;

    public Assignee() {
    }

    public Assignee(String name) {
        this.name = name;
    }

    public Assignee(AssigneeRow row) {
        this.name = row.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    /**
     * Deserializes an assignee
     *
     * @param s
     * @return
     */
    public static Assignee deserialize(String s) {
        return new Assignee(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignee)) return false;

        Assignee assignee = (Assignee) o;

        return getName() != null ? getName().equals(assignee.getName()) : assignee.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        return result;
    }
}