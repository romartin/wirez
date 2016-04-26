package org.wirez.core.api.rule.impl.empty;

import org.wirez.core.api.rule.CardinalityRuleManager;
import org.wirez.core.api.rule.ConnectionRuleManager;
import org.wirez.core.api.rule.ContainmentRuleManager;
import org.wirez.core.api.rule.EdgeCardinalityRuleManager;
import org.wirez.core.api.rule.empty.EmptyRulesManager;
import org.wirez.core.api.rule.impl.AbstractRulesManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Empty
public class EmptyRulesManagerImpl extends AbstractRulesManager<ContainmentRuleManager, ConnectionRuleManager,
        CardinalityRuleManager, EdgeCardinalityRuleManager> implements EmptyRulesManager {

    public static final transient EmptyRulesManagerImpl INSTANCE 
            = new EmptyRulesManagerImpl( EmptyContainmentRuleManager.INSTANCE, EmptyConnectionRuleManager.INSTANCE,
            EmptyCardinalityRuleManager.INSTANCE, EmptyEdgeCardinalityRuleManager.INSTANCE);

    private static final String NAME = "Empty Rules Manager";

    @Inject
    public EmptyRulesManagerImpl(final @Empty ContainmentRuleManager containmentRuleManager, 
                                 final @Empty ConnectionRuleManager connectionRuleManager, 
                                 final @Empty CardinalityRuleManager cardinalityRuleManager, 
                                 final @Empty EdgeCardinalityRuleManager edgeCardinalityRuleManager) {
        super(containmentRuleManager, connectionRuleManager, cardinalityRuleManager, edgeCardinalityRuleManager);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
