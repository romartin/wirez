package org.wirez.core.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.ConnectionRule;
import org.wirez.core.rule.DefaultRuleViolations;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.graph.GraphConnectionRuleManager;
import org.wirez.core.rule.model.ModelConnectionRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphConnectionRuleManagerImpl extends AbstractGraphRuleManager<ConnectionRule, ModelConnectionRuleManager> 
        implements GraphConnectionRuleManager {

    private static final String NAME = "Graph Connection Rule Manager";

    private ModelConnectionRuleManager modelConnectionRuleManager;

    @Inject
    public GraphConnectionRuleManagerImpl(final DefinitionManager definitionManager,
                                          final ModelConnectionRuleManager modelConnectionRuleManager) {
        super( definitionManager );
        this.modelConnectionRuleManager = modelConnectionRuleManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ModelConnectionRuleManager getWrapped() {
        return modelConnectionRuleManager;
    }


    @Override
    public RuleViolations evaluate(final Edge<? extends View<?>, ? extends Node> edge,
                                   final Node<? extends View<?>, ? extends Edge> outgoing,
                                   final Node<? extends View<?>, ? extends Edge> incoming) {

        if ( incoming == null || outgoing == null ) {
            return new DefaultRuleViolations();
        }
        
        final String edgeId = getElementDefinitionId( edge );
        
        return modelConnectionRuleManager.evaluate( edgeId, getLabels( outgoing ), getLabels( incoming )  );
        
    }
    
}
