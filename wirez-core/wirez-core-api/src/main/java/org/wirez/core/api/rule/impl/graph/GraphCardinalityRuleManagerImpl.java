package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.CardinalityRule;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.graph.GraphCardinalityRuleManager;
import org.wirez.core.api.rule.model.ModelCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphCardinalityRuleManagerImpl extends AbstractGraphRuleManager<CardinalityRule, ModelCardinalityRuleManager> 
        implements GraphCardinalityRuleManager {

    private static final String NAME = "Graph Cardinality Rule Manager";

    ModelCardinalityRuleManager modelCardinalityRuleManager;

    @Inject
    public GraphCardinalityRuleManagerImpl(final DefinitionManager definitionManager,
                                           final GraphUtils graphUtils,
                                           final ModelCardinalityRuleManager modelCardinalityRuleManager) {
        super( definitionManager, graphUtils );
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
        
        final int count = graphUtils.countDefinitions( target, candidate.getContent().getDefinition() );
        
        return modelCardinalityRuleManager.evaluate( getLabels( candidate ), count, operation );
        
    }
    
}
