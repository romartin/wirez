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
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.violations.CardinalityMaxRuleViolation;
import org.wirez.core.api.rule.violations.CardinalityMinRuleViolation;
import org.wirez.core.api.rule.violations.ConnectionRuleViolation;
import org.wirez.core.api.rule.violations.ContainmentRuleViolation;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

/**
 * Belief graph propagation Rule Manager's implementation.
 */
@Dependent
@Named( "default" )
public class DefaultRuleManager implements RuleManager {

    protected final Set<ContainmentRule> containmentRules = new HashSet<ContainmentRule>();
    protected final Set<CardinalityRule> cardinalityRules = new HashSet<CardinalityRule>();
    protected final Set<ConnectionRule> connectionRules = new HashSet<ConnectionRule>();
    
    @Override
    public RuleManager addRule( final Rule rule ) {
        PortablePreconditions.checkNotNull( "rule",
                rule );
        // Filter Rules upon insertion as different types of validation use different rules
        // It's quicker to filter once here than every time the Rules are needed.
        if ( rule instanceof ContainmentRule ) {
            containmentRules.add( (ContainmentRule) rule );
        } else if ( rule instanceof CardinalityRule ) {
            cardinalityRules.add( (CardinalityRule) rule );
        } else if ( rule instanceof ConnectionRule ) {
            connectionRules.add( (ConnectionRule) rule );
        }

        return this;
    }
    
    @Override
    public RuleViolations checkContainment(final Element<? extends ViewContent<?>> target,
                                           final Element<? extends ViewContent<?>> candidate ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( containmentRules.isEmpty() ) {
            return results;
        }

        for ( ContainmentRule rule : containmentRules ) {
            if ( rule.getId().equals( target.getContent().getDefinition().getId() ) ) {
                final Set<String> permittedStrings = new HashSet<String>( rule.getPermittedRoles() );
                permittedStrings.retainAll( candidate.getLabels() );
                if ( permittedStrings.size() > 0 ) {
                    return results;
                }
            }
        }

        results.addViolation(new ContainmentRuleViolation(target.getUUID(), candidate.getUUID()));
        return results;
    }

    @Override
    public RuleViolations checkCardinality(final Graph<? extends Definition, ? extends Node> target,
                                           final Node<? extends ViewContent, ? extends Edge> candidate,
                                           final DefaultRuleManager.Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( cardinalityRules.isEmpty() ) {
            return results;
        }

        for ( CardinalityRule rule : cardinalityRules ) {
            if ( candidate.getLabels().contains( rule.getRole() ) ) {
                final long minOccurrences = rule.getMinOccurrences();
                final long maxOccurrences = rule.getMaxOccurrences();
                long count = ( operation == Operation.ADD ? 1 : -1 );
                for ( Node<? extends ViewContent, ? extends Edge> node : target.nodes() ) {
                    if (node.getContent().getDefinition().getId().equals( candidate.getContent().getDefinition().getId() ))  {
                        count++;
                    }
                }
                if ( count < minOccurrences ) {
                    results.addViolation(new CardinalityMinRuleViolation(target.getUUID(), candidate.getUUID(), minOccurrences, count));
                } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                    results.addViolation(new CardinalityMaxRuleViolation(target.getUUID(), candidate.getUUID(), maxOccurrences, count));
                }
            }
        }
        return results;
    }

    @Override
    public RuleViolations checkConnectionRules( final Node<? extends ViewContent<?>, ? extends Edge> outgoingNode,
                                                final Node<? extends ViewContent<?>, ? extends Edge> incomingNode,
                                                final Edge<? extends ViewContent<?>, ? extends Node> edge ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( connectionRules.isEmpty() || incomingNode == null || outgoingNode == null ) {
            return results;
        }

        final Set<Pair<String, String>> couples = new HashSet<Pair<String, String>>();
        final String id = edge.getContent().getDefinition().getId();
        for ( ConnectionRule rule : connectionRules ) {
            if ( id.equals( rule.getId() ) ) {
                for ( ConnectionRule.PermittedConnection pc : rule.getPermittedConnections() ) {
                    couples.add( new Pair<String, String>( pc.getStartRole(), pc.getEndRole() ) );
                    if ( outgoingNode.getLabels().contains( pc.getStartRole() ) ) {
                        if ( incomingNode.getLabels().contains( pc.getEndRole() ) ) {
                            return results;
                        }
                    }
                }
            }
        }

        results.addViolation(new ConnectionRuleViolation(id, couples));
        return results;
    }

    @Override
    public RuleViolations checkCardinality( final Node<? extends ViewContent<?>, ? extends Edge> outgoingNode,
                                            final Node<? extends ViewContent<?>, ? extends Edge> incomingNode,
                                            final Edge<? extends ViewContent<?>, ? extends Node> edge,
                                            final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( cardinalityRules.isEmpty() ) {
            return results;
        }

        for ( CardinalityRule rule : cardinalityRules ) {
            //Check outgoing connections
            if ( outgoingNode != null && outgoingNode.getLabels().contains( rule.getRole() ) ) {
                for ( CardinalityRule.ConnectorRule cr : rule.getOutgoingConnectionRules() ) {
                    if( edge.getContent().getDefinition().getId().equals( cr.getId() )) {
                        final long minOccurrences = cr.getMinOccurrences();
                        final long maxOccurrences = cr.getMaxOccurrences();
                        long count = ( operation == Operation.ADD ? 1 : -1 );
                        for ( Edge e : outgoingNode.getOutEdges() ) {
                                if ( (edge.getContent().getDefinition().getId().equals( edge.getContent().getDefinition().getId())) ) {
                                    count++;
                                }
                        }

                        if ( count < minOccurrences ) {
                            results.addViolation(new CardinalityMinRuleViolation(outgoingNode.getUUID(), cr.getId(), minOccurrences, count));
                        } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                            results.addViolation(new CardinalityMaxRuleViolation(outgoingNode.getUUID(), cr.getId(), maxOccurrences, count));
                        }
                    }
                }
            }

            //Check incoming connections
            if ( incomingNode != null && incomingNode.getLabels().contains( rule.getRole() ) ) {
                for ( CardinalityRule.ConnectorRule cr : rule.getIncomingConnectionRules() ) {
                    if( edge.getContent().getDefinition().getId().equals( cr.getId() )) {
                        final long minOccurrences = cr.getMinOccurrences();
                        final long maxOccurrences = cr.getMaxOccurrences();
                        long count = ( operation == Operation.ADD ? 1 : -1 );
                        for ( Edge e : incomingNode.getInEdges() ) {
                            if ( (edge.getContent().getDefinition().getId().equals( edge.getContent().getDefinition().getId())) ) {
                                count++;
                            }
                        }

                        if ( count < minOccurrences ) {
                            results.addViolation(new CardinalityMinRuleViolation(incomingNode.getUUID(), cr.getId(), minOccurrences, count));
                        } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                            results.addViolation(new CardinalityMaxRuleViolation(incomingNode.getUUID(), cr.getId(), maxOccurrences, count));
                        }
                    }
                }
            }
        }
        return results;
    }
    
    private boolean checkLabels(Edge<? extends ViewContent, ? extends Node> e1, Edge<? extends ViewContent, ? extends Node> e2) {
        final Set<String> e1Strings = e1.getLabels();
        final Set<String> e2Strings = e2.getLabels();
        for (final String role : e1Strings) {
            if (e2Strings.contains(role)) return true;
        }
        return false;
    }

    @Override
    public RuleManager clearRules() {
        containmentRules.clear();
        cardinalityRules.clear();
        connectionRules.clear();
        return this;
    }

}
