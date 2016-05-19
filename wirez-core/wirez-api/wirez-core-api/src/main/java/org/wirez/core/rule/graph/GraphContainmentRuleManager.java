package org.wirez.core.rule.graph;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.rule.ContainmentRuleManager;

public interface GraphContainmentRuleManager extends ContainmentRuleManager<Element<?>, Element<? extends Definition<?>>> {
    
}
