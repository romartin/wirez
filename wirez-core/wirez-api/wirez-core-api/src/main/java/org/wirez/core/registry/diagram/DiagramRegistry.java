package org.wirez.core.registry.diagram;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.DynamicRegistry;
import org.wirez.core.registry.Registry;

public interface DiagramRegistry<D extends Diagram> extends DynamicRegistry<D> {

    D getDiagramByUUID( String uuid );

    void update( D diagram );
    
}
