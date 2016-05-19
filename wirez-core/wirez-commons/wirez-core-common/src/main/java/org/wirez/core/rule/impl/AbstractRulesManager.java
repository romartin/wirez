package org.wirez.core.rule.impl;

import org.wirez.core.rule.*;

public abstract class AbstractRulesManager<C extends ContainmentRuleManager,
        L extends ConnectionRuleManager,
        K extends CardinalityRuleManager,
        E extends EdgeCardinalityRuleManager> implements RulesManager<C, L, K, E> {

    protected final C containmentRuleManager;
    protected final L connectionRuleManager;
    protected final K cardinalityRuleManager;
    protected final E edgeCardinalityRuleManager;

    public AbstractRulesManager(final C containmentRuleManager, 
                                final L connectionRuleManager, 
                                final K cardinalityRuleManager, 
                                final E edgeCardinalityRuleManager) {
        this.containmentRuleManager = containmentRuleManager;
        this.connectionRuleManager = connectionRuleManager;
        this.cardinalityRuleManager = cardinalityRuleManager;
        this.edgeCardinalityRuleManager = edgeCardinalityRuleManager;
    }

    @Override
    public boolean supports(final Rule rule) {
        return connectionRuleManager.supports( rule ) ||
                containmentRuleManager.supports( rule ) ||
                cardinalityRuleManager.supports( rule ) ||
                edgeCardinalityRuleManager.supports( rule );
    }

    @Override
    @SuppressWarnings("unchecked")
    public RuleManager addRule(final Rule rule) {
        
        if ( connectionRuleManager.supports( rule) ) {
            
            connectionRuleManager.addRule( rule );
            
        } else if ( containmentRuleManager.supports( rule ) ) {
            
            containmentRuleManager.addRule( rule );
            
        } else if ( cardinalityRuleManager.supports( rule ) ) {

            cardinalityRuleManager.addRule( rule );
            
        } else if ( edgeCardinalityRuleManager.supports( rule ) ) {
            
            edgeCardinalityRuleManager.addRule( rule );
            
        }
        
        return this;
    }

    @Override
    public RuleManager clearRules() {
        containmentRuleManager.clearRules();
        connectionRuleManager.clearRules();
        cardinalityRuleManager.clearRules();
        edgeCardinalityRuleManager.clearRules();
        return this;
    }

    @Override
    public C containment() {
        return containmentRuleManager;
    }

    @Override
    public L connection() {
        return connectionRuleManager;
    }

    @Override
    public K cardinality() {
        return cardinalityRuleManager;
    }

    @Override
    public E edgeCardinality() {
        return edgeCardinalityRuleManager;
    }
    
}
