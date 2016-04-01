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

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named( "empty" )
public class EmptyRuleManager implements RuleManager {

    @Override
    public RuleManager addRule( final Rule rule ) {
        throw new UnsupportedOperationException("No rule additions supported");
    }
    
    @Override
    public RuleViolations checkContainment(final Element<?> target,
                                           final Element<? extends View<?>> candidate ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkCardinality(final Graph<?, ? extends Node> target,
                                           final Node<? extends View, ? extends Edge> candidate,
                                           final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkConnectionRules( final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                                final Node<? extends View<?>, ? extends Edge> incomingNode,
                                                final Edge<? extends View<?>, ? extends Node> edge ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkCardinality( final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                            final Node<? extends View<?>, ? extends Edge> incomingNode,
                                            final Edge<? extends View<?>, ? extends Node> edge,
                                            final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }
    
    @Override
    public RuleManager clearRules() {
        throw new UnsupportedOperationException("No rules to clear");
    }

}
