package org.kie.workbench.common.stunner.core.rule.graph;

import org.kie.workbench.common.stunner.core.rule.EdgeCardinalityRuleManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import java.util.List;

public interface GraphEdgeCardinalityRuleManager
        extends EdgeCardinalityRuleManager<Edge<? extends View<?>, Node>,
                        Node<? extends View<?>, Edge>,
                        List<? extends Edge>> {

}
