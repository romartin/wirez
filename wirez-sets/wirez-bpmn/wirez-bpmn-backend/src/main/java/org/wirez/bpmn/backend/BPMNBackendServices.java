package org.wirez.bpmn.backend;

import org.wirez.bpmn.backend.service.diagram.BPMNDiagramMarshaller;
import org.wirez.core.api.service.DefinitionSetServices;
import org.wirez.core.api.service.diagram.DiagramMarshaller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNBackendServices implements DefinitionSetServices {

    @Inject
    BPMNDiagramMarshaller bpmnDiagramMarshaller;
    
    @Override
    public boolean accepts(final String path) {
        return true;
    }

    @Override
    public DiagramMarshaller getDiagramMarshaller() {
        return bpmnDiagramMarshaller;
    }
    
}
