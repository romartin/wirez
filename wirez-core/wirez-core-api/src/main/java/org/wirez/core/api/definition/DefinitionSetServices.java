package org.wirez.core.api.definition;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.marshall.DiagramMarshaller;

import java.io.InputStream;

public interface DefinitionSetServices {
    
    boolean accepts ( String path );
    
    String getFileExtension();
    
    DiagramMarshaller<Diagram, InputStream, String> getDiagramMarshaller();
    
}
