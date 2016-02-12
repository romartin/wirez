package org.wirez.core.api.service.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.ServiceResponse;

public interface DiagramRepresentation  {
    
    String getUUID();

    String getName();

    String getDefinitionSetId();

    String getShapeSetId();

    String getPath();
    
}
