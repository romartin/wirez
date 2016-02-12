package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.ResponseStatus;
import org.wirez.core.api.service.ServiceResponseImpl;

import java.util.Collection;

@Portable
public class DiagramsServiceResponseImpl extends ServiceResponseImpl implements DiagramsServiceResponse {
    
    private final Collection<DiagramRepresentation> diagrams;

    public DiagramsServiceResponseImpl(@MapsTo("responseStatus") ResponseStatus responseStatus,
                                       @MapsTo("diagrams") Collection<DiagramRepresentation> diagrams) {
        super(responseStatus);
        this.diagrams = diagrams;
    }

    @Override
    public Collection<DiagramRepresentation> getDiagramRepresentations() {
        return diagrams;
    }

}
