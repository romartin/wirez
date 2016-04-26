package org.wirez.core.api.registry.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.registry.Registry;

public interface DiagramRegistry<D extends Diagram> extends Registry<D> {
    
    void update( D diagram );
    
}
