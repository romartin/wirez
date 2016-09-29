package org.kie.workbench.common.stunner.core.rule.impl.graph;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.rule.ContainmentRule;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.graph.GraphContainmentRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelContainmentRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphContainmentRuleManagerImpl extends AbstractGraphRuleManager<ContainmentRule, ModelContainmentRuleManager> 
        implements GraphContainmentRuleManager {

    private static final String NAME = "Graph Containment Rule Manager";

    ModelContainmentRuleManager modelContainmentRuleManager;

    @Inject
    public GraphContainmentRuleManagerImpl(final DefinitionManager definitionManager,
                                           final ModelContainmentRuleManager modelContainmentRuleManager) {
        super( definitionManager );
        this.modelContainmentRuleManager = modelContainmentRuleManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected ModelContainmentRuleManager getWrapped() {
        return modelContainmentRuleManager;
    }

    @Override
    public RuleViolations evaluate(final Element<?> target,
                                   final Element<? extends Definition<?>> candidateRoles) {

        final String targetId = getElementDefinitionId( target );
        return modelContainmentRuleManager.evaluate( targetId, getLabels( candidateRoles ) );
        
    }
    
}
