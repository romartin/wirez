package org.wirez.core.registry.diagram;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.Registry;

public interface DiagramRegistry<D extends Diagram> extends Registry<D> {
    
    void update( D diagram );
    
}
