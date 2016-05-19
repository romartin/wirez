package org.wirez.bpmn.backend;

import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.backend.service.diagram.BPMNDiagramMarshaller;
import org.wirez.core.definition.DefinitionSetServices;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.marshall.DiagramMarshaller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;

@ApplicationScoped
public class BPMNBackendServices implements DefinitionSetServices {

    public static final String EXTENSION = "bpmn";
    
    @Inject
    BPMNDiagramMarshaller bpmnDiagramMarshaller;
    
    @Override
    public boolean accepts(final String defSetId) {
        final String id = BindableAdapterUtils.getDefinitionSetId( BPMNDefinitionSet.class );
        return defSetId != null && defSetId.equals( id );
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
