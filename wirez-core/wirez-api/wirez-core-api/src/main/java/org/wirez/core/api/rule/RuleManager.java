package org.wirez.core.api.rule;

public interface RuleManager<R extends Rule> {

    /**
     * Rules are applied against an unmodified Graph/Model to check whether the proposed mutated state is valid.
     * This is deliberate to avoid, for example, costly "undo" operations if we were to mutate the state
     * first and then validate. An invalidate state would need to be reverted. If we decided to change
     * this we'd need to mutate the graph state first and then validate the whole graph.
     */
    enum Operation {
        ADD,
        DELETE
    }
    
    /**
     * The rule manager name.
     */
    String getName();

    boolean supports( Rule rule ); 
    
    /**
     * Add a rule to the Rule Manager
     */
    RuleManager addRule( R rule );

    /**
     * Clear all rules.
     */
    RuleManager clearRules();
    
}
