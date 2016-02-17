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

package org.wirez.core.api.graph.command;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

@Portable
public class GraphCommandResults implements CommandResults<RuleViolation> {
    
    Collection<CommandResult<RuleViolation>> results = new LinkedList<CommandResult<RuleViolation>>();

    public Collection<CommandResult<RuleViolation>> getItems() {
        return results;
    }

    @Override
    public Iterable<CommandResult<RuleViolation>> results() {
        return results;
    }

    @Override
    public Iterable<CommandResult<RuleViolation>> results(final CommandResult.Type resultType) {
        final Collection<CommandResult<RuleViolation>> resultsByType = new LinkedList<>();
        if ( results != null ) {
            for (final CommandResult<RuleViolation> _result : results) {
                if (_result.getType().equals(resultType)) {
                    resultsByType.add(_result);
                }
            }
        }
        return resultsByType;
    }

}
