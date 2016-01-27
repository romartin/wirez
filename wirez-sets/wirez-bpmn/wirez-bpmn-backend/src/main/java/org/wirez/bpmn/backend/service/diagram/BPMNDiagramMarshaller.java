package org.wirez.bpmn.backend.service.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.service.diagram.DiagramMarshaller;

import javax.enterprise.context.Dependent;
import java.io.InputStream;

//TODO
@Dependent
public class BPMNDiagramMarshaller implements DiagramMarshaller<InputStream, Settings> {
    
    
    @Override
    public Diagram<Settings> unmarhsall(InputStream inputStream) {
        return null;
    }

    @Override
    public String marshall(Diagram<Settings> diagram) {
        return null;
    }
}
