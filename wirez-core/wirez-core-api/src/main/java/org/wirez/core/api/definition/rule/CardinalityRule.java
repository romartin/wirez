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
package org.wirez.core.api.definition.rule;

import java.util.Set;

/**
 * Rule to restrict the cardinality of Elements in a diagram.
 */
public interface CardinalityRule extends RuleByRole {

    /**
     * The minimum number of occurrences that an Element can have in a diagram.
     * @return
     */
    Long getMinOccurrences();

    /**
     * The maximum number of occurrences that an Element can have in a diagram.
     * @return
     */
    Long getMaxOccurrences();

    /**
     * Restrictions on the incoming connections to an Element
     * @return
     */
    Set<ConnectorRule> getIncomingConnectionRules();

    /**
     * Restrictions on the outgoing connections from an Element
     * @return
     */
    Set<ConnectorRule> getOutgoingConnectionRules();

    /**
     * Rule to restrict the cardinality of Connections. The direction of the Connection is defined in CardinalityRule.
     */
    interface ConnectorRule extends RuleByRole {

        /**
         * The minimum number of connections an Element can have.
         * @return
         */
        long getMinOccurrences();

        /**
         * The maximum number of connections an Element can have.
         * @return
         */
        long getMaxOccurrences();

    }

}
