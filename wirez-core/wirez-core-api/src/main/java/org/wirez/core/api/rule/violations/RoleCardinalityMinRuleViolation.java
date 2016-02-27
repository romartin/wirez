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

package org.wirez.core.api.rule.violations;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.graph.Element;

@Portable
public class RoleCardinalityMinRuleViolation extends AbstractCardinalityRuleViolation<Element, String> {


    
    public RoleCardinalityMinRuleViolation(@MapsTo("target") Element target,
                                           @MapsTo("candidate") String candidate,
                                           @MapsTo("restrictedOccurrences") Long restrictedOccurrences,
                                           @MapsTo("currentOccurrences") Long currentOccurrences) {
        super(target, candidate, restrictedOccurrences, currentOccurrences);
    }

    @Override
    public String getMessage() {
        return "'" + getTargetText() + "' requires a minimum '" + restrictedOccurrences + "' of '" + getCandidateText() + "' nodes. Found '" + currentOccurrences + "'.";
    }

    
}