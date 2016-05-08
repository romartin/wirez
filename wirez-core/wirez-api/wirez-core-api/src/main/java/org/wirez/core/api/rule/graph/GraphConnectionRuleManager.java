package org.wirez.core.api.rule.graph;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.ConnectionRuleManager;

public interface GraphConnectionRuleManager 
        extends ConnectionRuleManager<Edge<? extends View<?>, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
}
