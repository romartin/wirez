package org.wirez.core.rule.graph;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.rule.DockingRuleManager;

public interface GraphDockingRuleManager extends DockingRuleManager<Element<?>, Element<? extends Definition<?>>> {
    
}
