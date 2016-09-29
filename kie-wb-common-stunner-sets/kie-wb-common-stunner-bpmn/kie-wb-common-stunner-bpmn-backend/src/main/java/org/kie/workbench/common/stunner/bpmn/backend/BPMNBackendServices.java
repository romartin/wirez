package org.kie.workbench.common.stunner.bpmn.backend;

import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.backend.service.diagram.BPMNDiagramMarshaller;
import org.kie.workbench.common.stunner.core.definition.DefinitionSetServices;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.marshall.DiagramMarshaller;

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
