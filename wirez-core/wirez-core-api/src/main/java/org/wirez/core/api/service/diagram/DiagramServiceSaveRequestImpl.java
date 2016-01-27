package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.diagram.Diagram;

@Portable
public class DiagramServiceSaveRequestImpl implements DiagramServiceSaveRequest{
    
    final Diagram diagram;

    public DiagramServiceSaveRequestImpl(@MapsTo("diagram") Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public Diagram getDiagram() {
        return diagram;
    }
}
