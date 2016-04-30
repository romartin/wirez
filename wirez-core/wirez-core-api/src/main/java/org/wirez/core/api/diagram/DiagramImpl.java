package org.wirez.core.api.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.graph.Graph;

@Portable
public final class DiagramImpl extends AbstractDiagram<Graph, Settings> {
    
    public DiagramImpl(@MapsTo("uuid")  String uuid,
                       @MapsTo("graph")  Graph graph,
                       @MapsTo("settings")  Settings settings) {
        super( uuid, graph, settings );
    }

}
