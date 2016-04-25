package org.wirez.core.api.rule.graph;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.CardinalityRuleManager;

public interface GraphCardinalityRuleManager 
        extends CardinalityRuleManager<Graph<?, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
    
}
