package org.wirez.core.api.rule.model;

import org.wirez.core.api.rule.EdgeCardinalityRuleManager;

import java.util.Set;

public interface ModelEdgeCardinalityRuleManager 
        extends EdgeCardinalityRuleManager<String, Set<String>, Integer> {
    
}
