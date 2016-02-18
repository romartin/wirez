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

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractCommandResults<V> implements CommandResults<V> {
    
    Collection<CommandResult<V>> results = new LinkedList<CommandResult<V>>();

    @Override
    public void add(final CommandResult<V> result) {
        this.results.add( result );
    }

    @Override
    public Iterable<CommandResult<V>> results() {
        return results;
    }

    @Override
    public Iterable<CommandResult<V>> results(final CommandResult.Type resultType) {
        final Collection<CommandResult<V>> resultsByType = new LinkedList<>();
        if ( results != null ) {
            for (final CommandResult<V> _result : results) {
                if (_result.getType().equals(resultType)) {
                    resultsByType.add(_result);
                }
            }
        }
        return resultsByType;
    }

}