package org.wirez.core.registry.impl;

import java.util.LinkedList;

import org.wirez.core.diagram.Diagram;

class DiagramListRegistry<D extends Diagram> extends AbstractDiagramListRegistry<D> {

    DiagramListRegistry() {
        super(new LinkedList<>());
    }

}
