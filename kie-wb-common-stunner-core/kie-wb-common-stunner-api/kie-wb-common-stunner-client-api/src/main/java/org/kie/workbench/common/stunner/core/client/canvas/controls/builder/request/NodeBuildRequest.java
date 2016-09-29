package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request;

import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.BuildRequest;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface NodeBuildRequest extends BuildRequest {
    
    Node<View<?>, Edge> getNode();
    
    Edge<View<?>, Node> getInEdge();

    int getSourceManger();

    int getTargetMagnet();
    
}
