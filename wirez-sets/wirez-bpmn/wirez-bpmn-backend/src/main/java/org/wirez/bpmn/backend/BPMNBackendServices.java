package org.wirez.bpmn.backend;

import org.wirez.bpmn.backend.service.diagram.BPMNDiagramMarshaller;
import org.wirez.core.api.definition.DefinitionSetServices;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.marshall.DiagramMarshaller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;

@ApplicationScoped
public class BPMNBackendServices implements DefinitionSetServices {

    public static final String EXTENSION = "bpmn";
    @Inject
    BPMNDiagramMarshaller bpmnDiagramMarshaller;
    
    @Override
    public boolean accepts(final String path) {
        return path != null && path.toLowerCase().endsWith(EXTENSION);
    }

    @Override
    public String getFileExtension() {
        return EXTENSION;
    }

    @Override
    public DiagramMarshaller<Diagram, InputStream, String> getDiagramMarshaller() {
        return bpmnDiagramMarshaller;
    }
    
}
