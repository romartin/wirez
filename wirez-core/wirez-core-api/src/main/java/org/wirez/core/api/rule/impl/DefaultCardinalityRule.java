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
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.rule.CardinalityRule;

import java.util.HashSet;
import java.util.Set;

@Portable
public class DefaultCardinalityRule implements CardinalityRule {

    private String name;
    private String role;
    private Long minOccurrences = 0l;
    private Long maxOccurrences = 0l;
    private Set<ConnectorRule> incomingConnectionRules = new HashSet<ConnectorRule>();
    private Set<ConnectorRule> outgoingConnectionRules = new HashSet<ConnectorRule>();

    public DefaultCardinalityRule(@MapsTo("name") String name,
                                  @MapsTo("role") String role,
                                  @MapsTo("minOccurrences") Long minOccurrences,
                                  @MapsTo("maxOccurrences") Long maxOccurrences,
                                  @MapsTo("incomingConnectionRules") Set<ConnectorRule> incomingConnectionRules,
                                  @MapsTo("outgoingConnectionRules") Set<ConnectorRule> outgoingConnectionRules ) {
        this.name = PortablePreconditions.checkNotNull( "name",
                                                        name );
        this.role = PortablePreconditions.checkNotNull( "role",
                                                        role );
        if ( minOccurrences < 0 ) {
            throw new IllegalArgumentException( "minOccurrences cannot be less than 0." );
        }
        this.minOccurrences = minOccurrences;
        if ( maxOccurrences > -1 && maxOccurrences < minOccurrences ) {
            throw new IllegalArgumentException( "maxOccurrences cannot be less than minOccurrences." );
        }
        this.maxOccurrences = maxOccurrences;
        for ( ConnectorRule cr : incomingConnectionRules ) {
            final Long crMinOccurrences = cr.getMinOccurrences();
            final Long crMaxOccurrences = cr.getMaxOccurrences();
            if ( crMinOccurrences < 0 ) {
                throw new IllegalArgumentException( "Incoming ConnectorRule minOccurrences cannot be less than 0." );
            }
            if ( crMaxOccurrences > -1 && crMaxOccurrences < crMinOccurrences ) {
                throw new IllegalArgumentException( "Incoming ConnectorRule maxOccurrences cannot be less than minOccurrences." );
            }
        }
        this.incomingConnectionRules = PortablePreconditions.checkNotNull( "incomingConnectionRules",
                                                                           incomingConnectionRules );
        for ( ConnectorRule cr : outgoingConnectionRules ) {
            final Long crMinOccurrences = cr.getMinOccurrences();
            final Long crMaxOccurrences = cr.getMaxOccurrences();
            if ( crMinOccurrences < 0 ) {
                throw new IllegalArgumentException( "Outgoing ConnectorRule minOccurrences cannot be less than 0." );
            }
            if ( crMaxOccurrences > -1 && crMaxOccurrences < crMinOccurrences ) {
                throw new IllegalArgumentException( "Outgoing ConnectorRule maxOccurrences cannot be less than minOccurrences." );
            }
        }
        this.outgoingConnectionRules = PortablePreconditions.checkNotNull( "outgoingConnectionRules",
                                                                           outgoingConnectionRules );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public Long getMinOccurrences() {
        return minOccurrences;
    }

    @Override
    public Long getMaxOccurrences() {
        return maxOccurrences;
    }

    @Override
    public Set<ConnectorRule> getIncomingConnectionRules() {
        return incomingConnectionRules;
    }

    @Override
    public Set<ConnectorRule> getOutgoingConnectionRules() {
        return outgoingConnectionRules;
    }

}
