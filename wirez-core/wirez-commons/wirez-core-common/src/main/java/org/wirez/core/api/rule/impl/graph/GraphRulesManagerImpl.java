package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.rule.graph.*;
import org.wirez.core.api.rule.impl.AbstractRulesManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphRulesManagerImpl extends AbstractRulesManager<GraphContainmentRuleManager, GraphConnectionRuleManager,
        GraphCardinalityRuleManager, GraphEdgeCardinalityRuleManager> implements GraphRulesManager {

    private static final String NAME = "Graph Rules Manager";

    @Inject
    public GraphRulesManagerImpl(final GraphContainmentRuleManager containmentRuleManager, 
                                 final GraphConnectionRuleManager connectionRuleManager, 
                                 final GraphCardinalityRuleManager cardinalityRuleManager, 
                                 final GraphEdgeCardinalityRuleManager edgeCardinalityRuleManager) {
        super(containmentRuleManager, connectionRuleManager, cardinalityRuleManager, edgeCardinalityRuleManager);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
