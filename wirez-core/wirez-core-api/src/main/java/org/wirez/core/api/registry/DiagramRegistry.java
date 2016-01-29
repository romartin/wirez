package org.wirez.core.api.registry;

import org.wirez.core.api.diagram.Diagram;

public interface DiagramRegistry<D extends Diagram> extends Registry<D> {
    
    void update( D diagram );
    
}
