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

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

/**
 * Rule Manager to report validation issues when attempting to mutate Elements
 */
public interface RuleManager {

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

    /**
     * Add a rule to the Rule Manager
     */
    RuleManager addRule(final Rule rule);
    
    /**
     * Check whether adding the proposed Node to the target Process breaks any containment Rules
     * @param target Target process
     * @param candidate Candidate node
     * @return
     */
    RuleViolations checkContainment(final Element<?> target,
                                    final Element<? extends View<?>> candidate);
    
    /**
     * Check whether adding the proposed Node to the target Process breaks any cardinality Rules
     * @param target Target process
     * @param candidate Candidate node
     * @param operation Is the candidate Node being added or removed
     * @return
     */
    RuleViolations checkCardinality(final Graph<?, ? extends Node> target,
                                    final Node<? extends View, ? extends Edge> candidate,
                                    final Operation operation);

    /**
     * Check whether adding the proposed Edge to the target Process breaks any connection Rules
     * @param outgoingNode Node from which the Edge will emanate
     * @param incomingNode Node to which the Edge will terminate
     * @param edge Candidate edge
     * @return Is the Edge being added or removed
     */
    RuleViolations checkConnectionRules(final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                        final Node<? extends View<?>, ? extends Edge> incomingNode,
                                        final Edge<? extends View<?>, ? extends Node> edge);

    /**
     * Check whether adding the proposed Edge to the target Process breaks any cardinality Rules
     * @param outgoingNode Node from which the Edge will emanate
     * @param incomingNode Node to which the Edge will terminate
     * @param edge Candidate edge
     * @param operation
     * @return Is the Edge being added or removed
     */
    RuleViolations checkCardinality(final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                    final Node<? extends View<?>, ? extends Edge> incomingNode,
                                    final Edge<? extends View<?>, ? extends Node> edge,
                                    final Operation operation);
    
    /**
     * Clear all rules.
     */
    RuleManager clearRules();
    
}
