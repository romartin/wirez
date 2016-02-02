/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.processors;

public class ProcessingRule {

    public enum TYPE {
        CONTAINMENT, CONNECTION, CARDINALITY;
    }
    
    private final String id;
    private final TYPE type;
    private final StringBuffer content;

    public ProcessingRule(String id, TYPE type, StringBuffer content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public StringBuffer getContent() {
        return content;
    }

    public TYPE getType() {
        return type;
    }

    public String getId() {
        return id;
    }
    
}
