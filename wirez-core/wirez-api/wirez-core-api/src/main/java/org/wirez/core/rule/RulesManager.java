package org.wirez.core.rule;

public interface RulesManager<  C extends ContainmentRuleManager, 
                                L extends ConnectionRuleManager, 
                                K extends CardinalityRuleManager,
                                E extends EdgeCardinalityRuleManager,
                                D extends DockingRuleManager >
        extends RuleManager<Rule> {
    
    C containment();
    
    L connection();
    
    K cardinality();
    
    E edgeCardinality();
    
    D docking();
    
}
