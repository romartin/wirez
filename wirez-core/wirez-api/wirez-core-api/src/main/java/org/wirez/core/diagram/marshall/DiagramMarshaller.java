package org.wirez.core.diagram.marshall;

import org.wirez.core.diagram.Diagram;

import java.io.IOException;

public interface DiagramMarshaller<D extends Diagram, I, O> {
    
    D unmarhsall(I input) throws IOException;
    
    O marshall(D diagram) throws IOException;
    
}
