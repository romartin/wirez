package org.kie.workbench.common.stunner.core.rule.impl;

import org.kie.workbench.common.stunner.core.rule.*;

public abstract class AbstractRulesManager<C extends ContainmentRuleManager,
        L extends ConnectionRuleManager,
        K extends CardinalityRuleManager,
        E extends EdgeCardinalityRuleManager,
        D extends DockingRuleManager> implements RulesManager<C, L, K, E, D> {

    protected final C containmentRuleManager;
    protected final L connectionRuleManager;
    protected final K cardinalityRuleManager;
    protected final E edgeCardinalityRuleManager;
    protected final D dockingRuleManager;

    public AbstractRulesManager(final C containmentRuleManager, 
                                final L connectionRuleManager, 
                                final K cardinalityRuleManager, 
                                final E edgeCardinalityRuleManager,
                                final D dockingRuleManager) {
        this.containmentRuleManager = containmentRuleManager;
        this.connectionRuleManager = connectionRuleManager;
        this.cardinalityRuleManager = cardinalityRuleManager;
        this.edgeCardinalityRuleManager = edgeCardinalityRuleManager;
        this.dockingRuleManager = dockingRuleManager;
    }

    @Override
    public boolean supports(final Rule rule) {
        return connectionRuleManager.supports( rule ) ||
                containmentRuleManager.supports( rule ) ||
                cardinalityRuleManager.supports( rule ) ||
                edgeCardinalityRuleManager.supports( rule ) || 
                dockingRuleManager.supports( rule );
    }

    @Override
    @SuppressWarnings("unchecked")
    public RuleManager addRule( final Rule rule) {
        
        if ( connectionRuleManager.supports( rule) ) {
            
            connectionRuleManager.addRule( rule );
            
        } else if ( containmentRuleManager.supports( rule ) ) {
            
            containmentRuleManager.addRule( rule );
            
        } else if ( cardinalityRuleManager.supports( rule ) ) {

            cardinalityRuleManager.addRule( rule );
            
        } else if ( edgeCardinalityRuleManager.supports( rule ) ) {
            
            edgeCardinalityRuleManager.addRule( rule );
            
        } else if ( dockingRuleManager.supports( rule ) ) {

            dockingRuleManager.addRule( rule );

        }
        
        return this;
    }

    @Override
    public RuleManager clearRules() {
        containmentRuleManager.clearRules();
        connectionRuleManager.clearRules();
        cardinalityRuleManager.clearRules();
        edgeCardinalityRuleManager.clearRules();
        dockingRuleManager.clearRules();
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

    @Override
    public D docking() {
        return dockingRuleManager;
    }
    
}
