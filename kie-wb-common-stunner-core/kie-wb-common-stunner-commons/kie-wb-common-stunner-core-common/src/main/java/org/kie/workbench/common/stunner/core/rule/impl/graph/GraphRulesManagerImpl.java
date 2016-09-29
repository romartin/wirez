package org.kie.workbench.common.stunner.core.rule.impl.graph;

import org.kie.workbench.common.stunner.core.rule.graph.*;
import org.kie.workbench.common.stunner.core.rule.impl.AbstractRulesManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphRulesManagerImpl extends AbstractRulesManager<GraphContainmentRuleManager, GraphConnectionRuleManager,
        GraphCardinalityRuleManager, GraphEdgeCardinalityRuleManager, GraphDockingRuleManager> implements GraphRulesManager {

    private static final String NAME = "Graph Rules Manager";

    @Inject
    public GraphRulesManagerImpl(final GraphContainmentRuleManager containmentRuleManager, 
                                 final GraphConnectionRuleManager connectionRuleManager, 
                                 final GraphCardinalityRuleManager cardinalityRuleManager, 
                                 final GraphEdgeCardinalityRuleManager edgeCardinalityRuleManager,
                                 final GraphDockingRuleManager graphDockingRuleManager) {
        super(containmentRuleManager, connectionRuleManager, cardinalityRuleManager, 
                edgeCardinalityRuleManager, graphDockingRuleManager);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
