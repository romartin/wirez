package org.wirez.bpmn.backend.marshall.json.builder;

import org.uberfire.ext.wirez.bpmn.api.BPMNWirez;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;

public interface EdgeObjectBuilder<W extends BPMNWirez> extends GraphObjectBuilder<W, DefaultEdge<W, DefaultNode>> {
    
}
