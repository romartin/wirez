package org.wirez.core.api.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.graph.Graph;

@Portable
public class DiagramImpl implements Diagram<Settings> {
    
    private final String uuid;
    private final Graph graph;
    private final Settings settings;

    public DiagramImpl(@MapsTo("uuid")  String uuid,
                       @MapsTo("graph")  Graph graph,
                       @MapsTo("settings")  Settings settings) {
        this.uuid = uuid;
        this.graph = graph;
        this.settings = settings;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
    
}
