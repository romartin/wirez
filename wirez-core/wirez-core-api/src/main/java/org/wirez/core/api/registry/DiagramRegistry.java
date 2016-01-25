package org.wirez.core.api.registry;

import org.wirez.core.api.diagram.Diagram;

public interface DiagramRegistry extends Registry<Diagram> {
    
    void update( Diagram diagram );
    
}
