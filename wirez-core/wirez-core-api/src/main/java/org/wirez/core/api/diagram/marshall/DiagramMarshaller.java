package org.wirez.core.api.diagram.marshall;

import org.wirez.core.api.diagram.Diagram;

public interface DiagramMarshaller<D extends Diagram, I, O> {
    
    D unmarhsall(I input);
    
    O marshall(D diagram);
    
}
