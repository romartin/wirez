package org.kie.workbench.common.stunner.core.rule.impl.graph;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.rule.DockingRule;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.graph.GraphDockingRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelDockingRuleManager;

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
