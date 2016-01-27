package org.wirez.core.api.service.diagram;

import org.wirez.core.api.diagram.Diagram;

public interface DiagramServiceCreateRequest {

    String getDefinitionSetId();
    
    String getShapeSetId();
    
    String getTitle();
    
}
