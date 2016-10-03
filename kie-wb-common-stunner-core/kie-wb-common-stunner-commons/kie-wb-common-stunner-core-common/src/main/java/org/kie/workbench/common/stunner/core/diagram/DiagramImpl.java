package org.kie.workbench.common.stunner.core.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.graph.Graph;

@Portable
public final class DiagramImpl extends AbstractDiagram<Graph, Settings> {
    
    public DiagramImpl(@MapsTo("uuid")  String uuid,
                       @MapsTo("graph")  Graph graph,
                       @MapsTo("settings")  Settings settings) {
        super( uuid, graph, settings );
    }

}
