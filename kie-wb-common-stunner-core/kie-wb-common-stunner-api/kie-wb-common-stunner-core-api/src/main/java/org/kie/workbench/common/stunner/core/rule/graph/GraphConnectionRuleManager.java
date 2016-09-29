package org.kie.workbench.common.stunner.core.rule.graph;

import org.kie.workbench.common.stunner.core.rule.ConnectionRuleManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface GraphConnectionRuleManager 
        extends ConnectionRuleManager<Edge<? extends View<?>, ? extends Node>, Node<? extends View<?>, ? extends Edge>> {
}
