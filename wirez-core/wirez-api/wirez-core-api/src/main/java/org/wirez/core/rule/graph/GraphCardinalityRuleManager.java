package org.wirez.core.rule.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.CardinalityRuleManager;

public interface GraphCardinalityRuleManager 
        extends CardinalityRuleManager<Graph<?, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
    
}
