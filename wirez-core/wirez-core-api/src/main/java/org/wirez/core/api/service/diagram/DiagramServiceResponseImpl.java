package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.ResponseStatus;
import org.wirez.core.api.service.ServiceResponseImpl;

@Portable
public class DiagramServiceResponseImpl extends ServiceResponseImpl implements DiagramServiceResponse {
    
    private final Diagram diagram;

    public DiagramServiceResponseImpl(@MapsTo("responseStatus") ResponseStatus responseStatus,
                                      @MapsTo("diagram") Diagram diagram) {
        super(responseStatus);
        this.diagram = diagram;
    }

    @Override
    public Diagram getDiagram() {
        return diagram;
    }
    
}
