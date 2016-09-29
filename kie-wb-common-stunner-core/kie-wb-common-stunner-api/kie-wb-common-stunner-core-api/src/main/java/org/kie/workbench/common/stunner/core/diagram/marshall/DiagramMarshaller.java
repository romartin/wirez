package org.kie.workbench.common.stunner.core.diagram.marshall;

import org.kie.workbench.common.stunner.core.diagram.Diagram;

import java.io.IOException;

public interface DiagramMarshaller<D extends Diagram, I, O> {
    
    D unmarhsall(I input) throws IOException;
    
    O marshall(D diagram) throws IOException;
    
}
