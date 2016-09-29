package org.kie.workbench.common.stunner.core.rule.graph;

import org.kie.workbench.common.stunner.core.rule.CardinalityRuleManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface GraphCardinalityRuleManager 
        extends CardinalityRuleManager<Graph<?, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
    
}
