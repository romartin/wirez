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

package org.wirez.core.api.rule.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.rule.CardinalityRule;

@Portable
public class DefaultConnectorRule implements CardinalityRule.ConnectorRule {
    
    private Long minOccurrences;
    private Long maxOccurrences;
    private String role;
    private String name;

    public DefaultConnectorRule(@MapsTo("minOccurrences") Long minOccurrences,
                                @MapsTo("maxOccurrences") Long maxOccurrences,
                                @MapsTo("role") String role,
                                @MapsTo("name") String name) {
        this.minOccurrences = minOccurrences;
        this.maxOccurrences = maxOccurrences;
        this.role = role;
        this.name = name;
    }

    @Override
    public long getMinOccurrences() {
        return minOccurrences;
    }

    @Override
    public long getMaxOccurrences() {
        return maxOccurrences;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getName() {
        return name;
    }
}
