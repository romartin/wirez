package org.wirez.core.api.rule.graph;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.definition.Definition;
import org.wirez.core.api.rule.ContainmentRuleManager;

public interface GraphContainmentRuleManager extends ContainmentRuleManager<Element<?>, Element<? extends Definition<?>>> {
    
}
