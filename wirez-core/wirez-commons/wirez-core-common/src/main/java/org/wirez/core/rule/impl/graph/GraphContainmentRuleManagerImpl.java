package org.wirez.core.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.ContainmentRule;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.graph.GraphContainmentRuleManager;
import org.wirez.core.rule.model.ModelContainmentRuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GraphContainmentRuleManagerImpl extends AbstractGraphRuleManager<ContainmentRule, ModelContainmentRuleManager> 
        implements GraphContainmentRuleManager {

    private static final String NAME = "Graph Containment Rule Manager";

    ModelContainmentRuleManager modelContainmentRuleManager;

    @Inject
    public GraphContainmentRuleManagerImpl(final DefinitionManager definitionManager,
                                           final GraphUtils graphUtils,
                                           final ModelContainmentRuleManager modelContainmentRuleManager) {
        super( definitionManager,graphUtils );
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
