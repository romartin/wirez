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

import org.uberfire.commons.data.Pair;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.violations.CardinalityMaxRuleViolation;
import org.wirez.core.api.rule.violations.CardinalityMinRuleViolation;
import org.wirez.core.api.rule.violations.ConnectionRuleViolation;
import org.wirez.core.api.rule.violations.ContainmentRuleViolation;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

@Dependent
@Named( "empty" )
public class EmptyRuleManager implements RuleManager {

    @Override
    public RuleManager addRule( final Rule rule ) {
        throw new UnsupportedOperationException("No rule additions supported");
    }
    
    @Override
    public RuleViolations checkContainment(final Element<? extends ViewContent<?>> target,
                                           final Element<? extends ViewContent<?>> candidate ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkCardinality(final DefaultGraph<? extends Definition, ? extends Node, ? extends Edge> target,
                                           final Node<? extends ViewContent, ? extends Edge> candidate,
                                           final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkConnectionRules( final Node<? extends ViewContent<?>, ? extends Edge> outgoingNode,
                                                final Node<? extends ViewContent<?>, ? extends Edge> incomingNode,
                                                final Edge<? extends ViewContent<?>, ? extends Node> edge ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }

    @Override
    public RuleViolations checkCardinality( final Node<? extends ViewContent<?>, ? extends Edge> outgoingNode,
                                            final Node<? extends ViewContent<?>, ? extends Edge> incomingNode,
                                            final Edge<? extends ViewContent<?>, ? extends Node> edge,
                                            final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        return results;
    }
    
    @Override
    public RuleManager clearRules() {
        throw new UnsupportedOperationException("No rules to clear");
    }

}
