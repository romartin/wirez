package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.definition.Definition;
import org.wirez.core.api.rule.ContainmentRule;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.graph.GraphContainmentRuleManager;
import org.wirez.core.api.rule.impl.model.Model;
import org.wirez.core.api.rule.model.ModelContainmentRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Graph
public class GraphContainmentRuleManagerImpl extends AbstractGraphRuleManager<ContainmentRule, ModelContainmentRuleManager> 
        implements GraphContainmentRuleManager {

    public static final String NAME = "Graph Containment Rule Manager";

    ModelContainmentRuleManager modelContainmentRuleManager;

    @Inject
    public GraphContainmentRuleManagerImpl(final DefinitionManager definitionManager,
                                           final @Model ModelContainmentRuleManager modelContainmentRuleManager) {
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
