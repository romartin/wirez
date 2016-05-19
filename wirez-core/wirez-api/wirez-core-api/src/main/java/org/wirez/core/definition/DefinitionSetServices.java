package org.wirez.core.definition;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.marshall.DiagramMarshaller;

import java.io.InputStream;

public interface DefinitionSetServices {
    
    boolean accepts ( String defSetId );
    
    String getFileExtension();
    
    DiagramMarshaller<Diagram, InputStream, String> getDiagramMarshaller();
    
}
