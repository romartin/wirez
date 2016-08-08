package org.wirez.core.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.rule.DockingRule;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.graph.GraphDockingRuleManager;
import org.wirez.core.rule.model.ModelDockingRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphDockingRuleManagerImpl extends AbstractGraphRuleManager<DockingRule, ModelDockingRuleManager> 
        implements GraphDockingRuleManager {

    private static final String NAME = "Graph Docking Rule Manager";

    ModelDockingRuleManager modelDockingRuleManager;

    @Inject
    public GraphDockingRuleManagerImpl(final DefinitionManager definitionManager,
                                       final ModelDockingRuleManager modelDockingRuleManager) {
        super( definitionManager );
        this.modelDockingRuleManager = modelDockingRuleManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ModelDockingRuleManager getWrapped() {
        return modelDockingRuleManager;
    }

    @Override
    public RuleViolations evaluate(final Element<?> target,
                                   final Element<? extends Definition<?>> candidateRoles) {

        final String targetId = getElementDefinitionId( target );
        return modelDockingRuleManager.evaluate( targetId, getLabels( candidateRoles ) );
        
    }
    
}
