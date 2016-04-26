package org.wirez.core.api.rule.impl.model;

import org.wirez.core.api.rule.impl.AbstractRulesManager;
import org.wirez.core.api.rule.model.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Model
public class ModelRulesManagerImpl extends AbstractRulesManager<ModelContainmentRuleManager, ModelConnectionRuleManager,
        ModelCardinalityRuleManager, ModelEdgeCardinalityRuleManager> implements ModelRulesManager {

    private static final String NAME = "Domain Model Rules Manager";

    @Inject
    public ModelRulesManagerImpl( final @Model ModelContainmentRuleManager containmentRuleManager, 
                                  final @Model ModelConnectionRuleManager connectionRuleManager, 
                                  final @Model ModelCardinalityRuleManager cardinalityRuleManager, 
                                  final @Model ModelEdgeCardinalityRuleManager edgeCardinalityRuleManager) {
        super(containmentRuleManager, connectionRuleManager, cardinalityRuleManager, edgeCardinalityRuleManager);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
