package org.wirez.core.api.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.wirez.core.api.graph.Graph;

public abstract class AbstractDiagram<G extends Graph, S extends Settings>  implements Diagram<G, S> {
    
    private final String uuid;
    private final G graph;
    private final S settings;

    public AbstractDiagram(@MapsTo("uuid")  String uuid,
                           @MapsTo("graph")  G graph,
                           @MapsTo("settings")  S settings) {
        this.uuid = uuid;
        this.graph = graph;
        this.settings = settings;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public G getGraph() {
        return graph;
    }

    @Override
    public S getSettings() {
        return settings;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof Diagram) ) {
            return false;
        }

        Diagram that = (Diagram) o;

        return uuid.equals( that.getUUID() );

    }
    
}
