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

package org.kie.workbench.common.stunner.core.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.command.CommandResult;

import java.util.Collection;

@Portable
public final class CommandResultImpl<V> implements CommandResult<V> {
    
    private final Type type;
    private final String message;
    private final Collection<V> violations;

    public CommandResultImpl(@MapsTo("type") Type type,
                             @MapsTo("message") String message,
                             @MapsTo("violations") Collection<V> violations) {
        this.violations = violations;
        this.type = type;
        this.message = message;
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

}
