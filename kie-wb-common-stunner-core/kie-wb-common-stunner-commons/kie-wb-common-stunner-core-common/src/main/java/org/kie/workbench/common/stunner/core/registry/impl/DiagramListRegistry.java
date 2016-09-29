package org.kie.workbench.common.stunner.core.registry.impl;

import java.util.LinkedList;

import org.kie.workbench.common.stunner.core.diagram.Diagram;

class DiagramListRegistry<D extends Diagram> extends AbstractDiagramListRegistry<D> {

    DiagramListRegistry() {
        super(new LinkedList<>());
    }

}
