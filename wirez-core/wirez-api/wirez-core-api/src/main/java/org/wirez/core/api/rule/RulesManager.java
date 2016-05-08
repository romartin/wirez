package org.wirez.core.api.rule;

public interface RulesManager<C extends ContainmentRuleManager, 
        L extends ConnectionRuleManager, 
        K extends CardinalityRuleManager,
        E extends EdgeCardinalityRuleManager> 
        extends RuleManager<Rule> {
    
    C containment();
    
    L connection();
    
    K cardinality();
    
    E edgeCardinality();
    
}
