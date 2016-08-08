package org.wirez.core.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.EdgeCardinalityRule;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.graph.GraphEdgeCardinalityRuleManager;
import org.wirez.core.rule.model.ModelEdgeCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

@Dependent
public class GraphEdgeCardinalityRuleManagerImpl extends AbstractGraphRuleManager<EdgeCardinalityRule, ModelEdgeCardinalityRuleManager> 
        implements GraphEdgeCardinalityRuleManager {

    private static final String NAME = "Graph Edge Cardinality Rule Manager";

    private ModelEdgeCardinalityRuleManager modelEdgeCardinalityRuleManager;
    private GraphUtils graphUtils;

    @Inject
    public GraphEdgeCardinalityRuleManagerImpl(final DefinitionManager definitionManager,
                                               final GraphUtils graphUtils,
                                               final ModelEdgeCardinalityRuleManager modelEdgeCardinalityRuleManager) {
        super( definitionManager );
        this.modelEdgeCardinalityRuleManager = modelEdgeCardinalityRuleManager;
        this.graphUtils = graphUtils;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ModelEdgeCardinalityRuleManager getWrapped() {
        return modelEdgeCardinalityRuleManager;
    }


    @Override
    public RuleViolations evaluate(final Edge<? extends View<?>, Node> edge,
                                   final Node<? extends View<?>, Edge> outNode,
                                   final Node<? extends View<?>, Edge> inNode,
                                   final List<? extends Edge> outEdges,
                                   final List<? extends Edge> inEdges,
                                   final Operation operation) {
        
        // The edge defintiion's identifier.
        final String edgeId = getElementDefinitionId( edge );
        
        // Outgoing count.
        final int outCount = graphUtils.countEdges( edgeId, outEdges );
                
        // Incoming count.
        final int inCount = graphUtils.countEdges( edgeId, inEdges );

        return modelEdgeCardinalityRuleManager.evaluate( edgeId, getLabels( outNode), getLabels( inNode ), outCount, inCount, operation );

    }
    
    private int count( final String edgeId, final List<? extends Edge> edges, final Operation operation ) {
        if ( null != edges ) {
            int c = ( operation.equals(Operation.ADD) ? 1 : -1 );
            for ( Edge e : edges ) {
                final String eId = getElementDefinitionId( e );
                if ( null != eId && edgeId.equals( eId ) ) {
                    c++;
                }

            }

            return c;
        }
        
        return 0;
    }
    
}
