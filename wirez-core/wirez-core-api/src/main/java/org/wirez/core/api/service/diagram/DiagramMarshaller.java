package org.wirez.core.api.service.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;

import java.io.InputStream;

public interface DiagramMarshaller<I, S extends Settings> {
    
    Diagram<S> unmarhsall(I input);
    
    String marshall(Diagram<S> diagram);
    
}
