package org.wirez.backend.registry.impl;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.impl.AbstractDiagramListRegistry;

import java.util.ArrayList;
import java.util.Collections;

class SyncDiagramListRegistry<D extends Diagram> extends AbstractDiagramListRegistry<D> {

    SyncDiagramListRegistry() {
        super(Collections.synchronizedList(new ArrayList<D>()));
    }
    
}
