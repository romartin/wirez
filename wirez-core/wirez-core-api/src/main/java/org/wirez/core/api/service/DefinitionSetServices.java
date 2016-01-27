package org.wirez.core.api.service;

import org.wirez.core.api.service.diagram.DiagramMarshaller;

public interface DefinitionSetServices {
    
    boolean accepts ( String path );
    
    DiagramMarshaller getDiagramMarshaller();
    
}
