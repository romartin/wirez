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
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.definition.DefinitionSet;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.violations.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

/**
 * Belief graph propagation Rule Manager's implementation.
 */
@Dependent
@Named( "default" )
public class DefaultRuleManager implements RuleManager {

    protected DefinitionManager definitionManager;
    
    protected final Set<ContainmentRule> containmentRules = new HashSet<ContainmentRule>();
    protected final Set<CardinalityRule> cardinalityRules = new HashSet<CardinalityRule>();
    protected final Set<ConnectionRule> connectionRules = new HashSet<ConnectionRule>();

    @Inject
    public DefaultRuleManager(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

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
    public RuleViolations checkContainment(final Element<?> target,
                                           final Element<? extends View<?>> candidate ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( containmentRules.isEmpty() ) {
            return results;
        }

        String targetId = null;
        if ( target.getContent() instanceof  View ) {
            Object definition = ((View) target.getContent()).getDefinition();
            targetId = getDefinitionId( definition );
        } else if ( target.getContent() instanceof DefinitionSet ) {
            targetId = ((DefinitionSet) target.getContent()).getId();
        }
        
        for ( ContainmentRule rule : containmentRules ) {
            if ( rule.getId().equals( targetId ) ) {
                final Set<String> permittedStrings = new HashSet<String>( rule.getPermittedRoles() );
                permittedStrings.retainAll( candidate.getLabels() );
                if ( permittedStrings.size() > 0 ) {
                    return results;
                }
            }
        }

        results.addViolation(new ContainmentRuleViolation(target, candidate));
        return results;
    }

    @Override
    public RuleViolations checkCardinality(final Graph<?, ? extends Node> target,
                                           final Node<? extends View, ? extends Edge> candidate,
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
                for ( Node<? extends View, ? extends Edge> node : target.nodes() ) {
                    if (getDefinitionId( node.getContent().getDefinition() ).equals( getDefinitionId( candidate.getContent().getDefinition() ) ))  {
                        count++;
                    }
                }
                if ( count < minOccurrences ) {
                    results.addViolation(new CardinalityMinRuleViolation((Element<? extends View<?>>) target, (Element<? extends View<?>>) candidate, minOccurrences, count));
                } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                    results.addViolation(new CardinalityMaxRuleViolation((Element<? extends View<?>>) target, (Element<? extends View<?>>) candidate, maxOccurrences, count));
                }
            }
        }
        return results;
    }

    @Override
    public RuleViolations checkConnectionRules( final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                                final Node<? extends View<?>, ? extends Edge> incomingNode,
                                                final Edge<? extends View<?>, ? extends Node> edge ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( connectionRules.isEmpty() || incomingNode == null || outgoingNode == null ) {
            return results;
        }

        final Set<Pair<String, String>> couples = new HashSet<Pair<String, String>>();
        final String id = getDefinitionId( edge.getContent().getDefinition() );
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
    public RuleViolations checkCardinality( final Node<? extends View<?>, ? extends Edge> outgoingNode,
                                            final Node<? extends View<?>, ? extends Edge> incomingNode,
                                            final Edge<? extends View<?>, ? extends Node> edge,
                                            final Operation operation ) {
        final DefaultRuleViolations results = new DefaultRuleViolations();
        if ( cardinalityRules.isEmpty() ) {
            return results;
        }

        for ( CardinalityRule rule : cardinalityRules ) {
            //Check outgoing connections
            if ( outgoingNode != null && outgoingNode.getLabels().contains( rule.getRole() ) ) {
                for ( CardinalityRule.ConnectorRule cr : rule.getOutgoingConnectionRules() ) {
                    if( getDefinitionId( edge.getContent().getDefinition() ).equals( cr.getId() )) {
                        final long minOccurrences = cr.getMinOccurrences();
                        final long maxOccurrences = cr.getMaxOccurrences();
                        long count = ( operation == Operation.ADD ? 1 : -1 );
                        for ( Edge e : outgoingNode.getOutEdges() ) {
                                if ( (getDefinitionId( edge.getContent().getDefinition() ).equals( getDefinitionId( edge.getContent().getDefinition() ) )) ) {
                                    count++;
                                }
                        }

                        if ( count < minOccurrences ) {
                            results.addViolation(new RoleCardinalityMinRuleViolation( (Element<? extends View<?>>) outgoingNode, cr.getId(), minOccurrences, count));
                        } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                            results.addViolation(new RoleCardinalityMaxRuleViolation((Element<? extends View<?>>) outgoingNode, cr.getId(), maxOccurrences, count));
                        }
                    }
                }
            }

            //Check incoming connections
            if ( incomingNode != null && incomingNode.getLabels().contains( rule.getRole() ) ) {
                for ( CardinalityRule.ConnectorRule cr : rule.getIncomingConnectionRules() ) {
                    if( getDefinitionId( edge.getContent().getDefinition() ).equals( cr.getId() )) {
                        final long minOccurrences = cr.getMinOccurrences();
                        final long maxOccurrences = cr.getMaxOccurrences();
                        long count = ( operation == Operation.ADD ? 1 : -1 );
                        for ( Edge e : incomingNode.getInEdges() ) {
                            if ( e instanceof View ) {
                                View<?> eView = (View<?>) e;
                                String eId = getDefinitionId( eView.getDefinition() );
                                if ( (eId.equals( getDefinitionId( edge.getContent().getDefinition() ) )) ) {
                                    count++;
                                }
                            }
                            
                        }

                        if ( count < minOccurrences ) {
                            results.addViolation(new RoleCardinalityMinRuleViolation((Element<? extends View<?>>) incomingNode, cr.getId(), minOccurrences, count));
                        } else if ( maxOccurrences > -1 && count > maxOccurrences ) {
                            results.addViolation(new RoleCardinalityMaxRuleViolation((Element<? extends View<?>>) incomingNode, cr.getId(), maxOccurrences, count));
                        }
                    }
                }
            }
        }
        return results;
    }
    
    protected String getDefinitionId(final Object definition) {
        DefinitionAdapter adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        return adapter.getId( definition );
    }
    
    private boolean checkLabels(Edge<? extends View, ? extends Node> e1, Edge<? extends View, ? extends Node> e2) {
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
