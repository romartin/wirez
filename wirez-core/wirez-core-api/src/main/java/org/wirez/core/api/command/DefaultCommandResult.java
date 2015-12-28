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

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.rule.RuleViolation;

import java.util.ArrayList;
import java.util.Collection;

@Portable
public class DefaultCommandResult implements CommandResult {
    
    private CommandResult.Type type;
    private String message;
    private Collection<RuleViolation> violations;

    public DefaultCommandResult() {
        this.violations = new ArrayList<RuleViolation>();
        init(violations);
    }
    
    public DefaultCommandResult(final Collection<RuleViolation> violations) {
        this.violations = violations;
        init(violations);
    }
    
    private void init(final Collection<RuleViolation> violations) {
        boolean hasError = false;
        boolean hasWarn= false;
        String message = null;
        if (violations != null) {
            for (final RuleViolation violation : violations) {
                if (RuleViolation.Type.ERROR.equals(violation.getViolationType())) {
                    hasError = true;
                    message = violation.getMessage();
                } else if (RuleViolation.Type.WARNING.equals(violation.getViolationType())) {
                    hasWarn= true;
                    if (message == null) {
                        message = violation.getMessage();
                    }
                }
            }
            this.type = hasError ? Type.ERROR : ( hasWarn ? Type.WARNING : Type.INFO );
            this.message = message != null ? message : "";
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    public void setType(Type type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public Iterable<RuleViolation> getRuleViolations() {
        return violations;
    }
}
