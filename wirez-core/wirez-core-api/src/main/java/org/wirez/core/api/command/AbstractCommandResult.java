/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.api.command;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractCommandResult<V> implements CommandResult<V> {
    
    private Type type;
    private String message;
    private Collection<V> violations;

    public AbstractCommandResult() {
        this(new ArrayList<V>());
    }
    
    public AbstractCommandResult(final Collection<V> violations) {
        this.violations = violations;
        this.type = Type.INFO;
        this.message = "";
    }
    
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Iterable<V> getViolations() {
        return violations;
    }

    @Override
    public void addViolation(final V violation) {
        this.violations.add( violation );
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
