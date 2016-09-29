package org.kie.workbench.common.stunner.core.rule.impl.graph;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.rule.CardinalityRule;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.graph.GraphCardinalityRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphCardinalityRuleManagerImpl extends AbstractGraphRuleManager<CardinalityRule, ModelCardinalityRuleManager> 
        implements GraphCardinalityRuleManager {

    private static final String NAME = "Graph Cardinality Rule Manager";

    private ModelCardinalityRuleManager modelCardinalityRuleManager;
    private GraphUtils graphUtils;

    @Inject
    public GraphCardinalityRuleManagerImpl(final DefinitionManager definitionManager,
                                           final GraphUtils graphUtils,
                                           final ModelCardinalityRuleManager modelCardinalityRuleManager) {
        super( definitionManager );
        this.modelCardinalityRuleManager = modelCardinalityRuleManager;
        this.graphUtils = graphUtils;
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
