package org.kie.workbench.common.stunner.core.registry.diagram;

import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

public interface DiagramRegistry<D extends Diagram> extends DynamicRegistry<D> {

    D getDiagramByUUID( String uuid );

    void update( D diagram );
    
}
