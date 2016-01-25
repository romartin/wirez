package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.diagram.Diagram;

@Portable
public class DiagramServiceResponseImpl implements DiagramServiceResponse {
    
    private final Diagram diagram;

    public DiagramServiceResponseImpl(@MapsTo("diagram") Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public Diagram getDiagram() {
        return diagram;
    }
    
}
