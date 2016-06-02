package org.wirez.core.rule.impl.model;

import org.wirez.core.rule.impl.AbstractRulesManager;
import org.wirez.core.rule.model.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ModelRulesManagerImpl extends AbstractRulesManager<ModelContainmentRuleManager, ModelConnectionRuleManager,
        ModelCardinalityRuleManager, ModelEdgeCardinalityRuleManager, ModelDockingRuleManager> implements ModelRulesManager {

    private static final String NAME = "Domain Model Rules Manager";

    @Inject
    public ModelRulesManagerImpl( final ModelContainmentRuleManager containmentRuleManager, 
                                  final ModelConnectionRuleManager connectionRuleManager, 
                                  final ModelCardinalityRuleManager cardinalityRuleManager, 
                                  final ModelEdgeCardinalityRuleManager edgeCardinalityRuleManager,
                                  final ModelDockingRuleManager dockingRuleManager) {
        super(containmentRuleManager, connectionRuleManager, cardinalityRuleManager, 
                edgeCardinalityRuleManager, dockingRuleManager);
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
