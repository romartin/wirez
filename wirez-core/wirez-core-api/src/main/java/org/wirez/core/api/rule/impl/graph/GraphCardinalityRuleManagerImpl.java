package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.CardinalityRule;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.graph.GraphCardinalityRuleManager;
import org.wirez.core.api.rule.impl.model.Model;
import org.wirez.core.api.rule.model.ModelCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@org.wirez.core.api.rule.impl.graph.Graph
public class GraphCardinalityRuleManagerImpl extends AbstractGraphRuleManager<CardinalityRule, ModelCardinalityRuleManager> 
        implements GraphCardinalityRuleManager {

    public static final String NAME = "Graph Cardinality Rule Manager";

    ModelCardinalityRuleManager modelCardinalityRuleManager;

    @Inject
    public GraphCardinalityRuleManagerImpl(final DefinitionManager definitionManager,
                                           final @Model ModelCardinalityRuleManager modelCardinalityRuleManager) {
        super( definitionManager );
        this.modelCardinalityRuleManager = modelCardinalityRuleManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ModelCardinalityRuleManager getWrapped() {
        return modelCardinalityRuleManager;
    }

    @Override
    public RuleViolations evaluate(final Graph<?, ? extends Node> target, 
                                   final Node<? extends View<?>, ? extends Edge> candidate, 
                                   final Operation operation) {
        
        final String candidateId = getElementDefinitionId( candidate );
        
        int count = ( operation.equals(org.wirez.core.api.rule.RuleManager.Operation.ADD) ? 1 : -1 );
        for ( Node<? extends View, ? extends Edge> node : target.nodes() ) {
            if (getElementDefinitionId( node ).equals( candidateId ))  {
                count++;
            }
        }
        
        return modelCardinalityRuleManager.evaluate( getLabels( candidate ), count, operation );
        
    }
    
}
