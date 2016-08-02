package org.wirez.core.registry.impl;

import org.wirez.core.diagram.Diagram;

import java.util.LinkedList;

class DiagramListRegistry<D extends Diagram> extends AbstractDiagramListRegistry<D> {

    DiagramListRegistry(  ) {
        super( new LinkedList<D>() );
    }


}
