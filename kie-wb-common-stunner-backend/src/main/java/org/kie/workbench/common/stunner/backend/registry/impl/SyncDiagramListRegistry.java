package org.kie.workbench.common.stunner.backend.registry.impl;

import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.registry.impl.AbstractDiagramListRegistry;

import java.util.ArrayList;
import java.util.Collections;

class SyncDiagramListRegistry<D extends Diagram> extends AbstractDiagramListRegistry<D> {

    SyncDiagramListRegistry() {
        super(Collections.synchronizedList(new ArrayList<D>()));
    }
    
}
