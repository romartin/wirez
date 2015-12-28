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

package org.wirez.core.api.rule;

import org.wirez.core.api.definition.rule.Rule;

/**
 * Rule Manager to report validation issues when attempting to mutate Elements
 */
public interface RuleManager {

    /**
     * Add a rule to the Rule Manager
     */
    RuleManager addRule(final Rule rule);

    /**
     * Clear all rules.
     */
    RuleManager clearRules();
    
    /**
     * Rules are applied against an unmodified Graph to check whether the proposed mutated state is valid.
     * This is deliberate to avoid, for example, costly "undo" operations if we were to mutate the state
     * first and then validate. An invalidate state would need to be reverted. If we decided to change
     * this we'd need to mutate the graph state first and then validate the whole graph.
     */
    enum Operation {
        ADD,
        DELETE
    }

}
