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
package org.kie.workbench.common.stunner.core.rule.impl.rules;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.uberfire.commons.validation.PortablePreconditions;
import org.kie.workbench.common.stunner.core.rule.EdgeCardinalityRule;

@Portable
public class EdgeCardinalityRuleImpl implements EdgeCardinalityRule {

    private String id;
    private String name;
    private String role;
    private Type type;
    private Integer minOccurrences = 0;
    private Integer maxOccurrences = 0;

    public EdgeCardinalityRuleImpl(@MapsTo("id") String id,
                                   @MapsTo("name") String name,
                                   @MapsTo("role") String role,
                                   @MapsTo("type") Type type,
                                   @MapsTo("minOccurrences") Integer minOccurrences,
                                   @MapsTo("maxOccurrences") Integer maxOccurrences) {
        this.role = PortablePreconditions.checkNotNull( "id",
                id );
        this.name = PortablePreconditions.checkNotNull( "name",
                                                        name );
        this.role = PortablePreconditions.checkNotNull( "role",
                                                        role );
        this.type = PortablePreconditions.checkNotNull( "type",
                type );
        if ( minOccurrences < 0 ) {
            throw new IllegalArgumentException( "minOccurrences cannot be less than 0." );
        }
        this.minOccurrences = minOccurrences;
        if ( maxOccurrences > -1 && maxOccurrences < minOccurrences ) {
            throw new IllegalArgumentException( "maxOccurrences cannot be less than minOccurrences." );
        }
        this.maxOccurrences = maxOccurrences;
       
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
    public int getMinOccurrences() {
        return minOccurrences;
    }

    @Override
    public int getMaxOccurrences() {
        return maxOccurrences;
    }

    @Override
    public Type getType() {
        return type;
    }


    @Override
    public String getId() {
        return id;
    }
}
