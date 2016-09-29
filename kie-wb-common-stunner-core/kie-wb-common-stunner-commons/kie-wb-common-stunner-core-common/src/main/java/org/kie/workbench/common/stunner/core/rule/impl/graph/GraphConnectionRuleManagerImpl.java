package org.kie.workbench.common.stunner.core.rule.impl.graph;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.rule.ConnectionRule;
import org.kie.workbench.common.stunner.core.rule.DefaultRuleViolations;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.graph.GraphConnectionRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelConnectionRuleManager;

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
