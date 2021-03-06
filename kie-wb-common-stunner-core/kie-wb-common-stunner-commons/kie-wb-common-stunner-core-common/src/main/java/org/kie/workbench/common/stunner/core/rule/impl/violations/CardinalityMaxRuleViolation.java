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

package org.kie.workbench.common.stunner.core.rule.impl.violations;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class CardinalityMaxRuleViolation extends AbstractCardinalityRuleViolation {

    public CardinalityMaxRuleViolation(@MapsTo("target") String target,
                                       @MapsTo("candidate") String candidate,
                                       @MapsTo("restrictedOccurrences") Integer restrictedOccurrences,
                                       @MapsTo("currentOccurrences") Integer currentOccurrences) {
        super(target, candidate, restrictedOccurrences, currentOccurrences);
    }

    @Override
    public String getMessage() {
        return "Labels ['" + target + "'] can have a maximum '" + restrictedOccurrences+ "' of '" + candidate + "' roles. Found '" + currentOccurrences + "'.";
    }

    
}
