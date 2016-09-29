package org.kie.workbench.common.stunner.core.definition;

import org.kie.workbench.common.stunner.core.diagram.marshall.DiagramMarshaller;
import org.kie.workbench.common.stunner.core.diagram.Diagram;

import java.io.InputStream;

public interface DefinitionSetServices {
    
    boolean accepts ( String defSetId );
    
    String getFileExtension();
    
    DiagramMarshaller<Diagram, InputStream, String> getDiagramMarshaller();
    
}
