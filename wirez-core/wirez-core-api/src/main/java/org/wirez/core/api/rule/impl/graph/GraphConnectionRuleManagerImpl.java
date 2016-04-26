package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.ConnectionRule;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.graph.GraphConnectionRuleManager;
import org.wirez.core.api.rule.impl.model.Model;
import org.wirez.core.api.rule.model.ModelConnectionRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Graph
public class GraphConnectionRuleManagerImpl extends AbstractGraphRuleManager<ConnectionRule, ModelConnectionRuleManager> 
        implements GraphConnectionRuleManager {

    private static final String NAME = "Graph Connection Rule Manager";

    ModelConnectionRuleManager modelConnectionRuleManager;

    @Inject
    public GraphConnectionRuleManagerImpl(final DefinitionManager definitionManager,
                                          final GraphUtils graphUtils,
                                          final @Model ModelConnectionRuleManager modelConnectionRuleManager) {
        super( definitionManager,graphUtils );
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
