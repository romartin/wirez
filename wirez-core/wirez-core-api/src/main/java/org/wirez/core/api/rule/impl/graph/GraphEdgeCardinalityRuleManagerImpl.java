package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.EdgeCardinalityRule;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.graph.GraphEdgeCardinalityRuleManager;
import org.wirez.core.api.rule.impl.model.Model;
import org.wirez.core.api.rule.model.ModelEdgeCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

@Dependent
@Graph
public class GraphEdgeCardinalityRuleManagerImpl extends AbstractGraphRuleManager<EdgeCardinalityRule, ModelEdgeCardinalityRuleManager> 
        implements GraphEdgeCardinalityRuleManager{

    public static final String NAME = "Graph Edge Cardinality Rule Manager";

    ModelEdgeCardinalityRuleManager modelEdgeCardinalityRuleManager;

    @Inject
    public GraphEdgeCardinalityRuleManagerImpl(final DefinitionManager definitionManager,
                                               final @Model ModelEdgeCardinalityRuleManager modelEdgeCardinalityRuleManager) {
        super( definitionManager );
        this.modelEdgeCardinalityRuleManager = modelEdgeCardinalityRuleManager;
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
        final int outCount = count( edgeId, outEdges, operation);
                
        // Incoming count.
        final int inCount = count( edgeId, inEdges, operation);

        return modelEdgeCardinalityRuleManager.evaluate( edgeId, getLabels( outNode), getLabels( inNode ), outCount, inCount, operation );

    }
    
    private int count( final String edgeId, final List<? extends Edge> edges, final Operation operation ) {
        if ( null != edges ) {
            int c = ( operation.equals(org.wirez.core.api.rule.RuleManager.Operation.ADD) ? 1 : -1 );
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
