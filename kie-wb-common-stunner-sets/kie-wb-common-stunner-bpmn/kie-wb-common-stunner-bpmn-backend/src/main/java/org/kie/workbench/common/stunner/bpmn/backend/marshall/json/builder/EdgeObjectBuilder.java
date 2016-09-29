package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

public interface EdgeObjectBuilder<W,  T extends Edge<View<W>, Node>> 
        extends GraphObjectBuilder<W, T> {

    Class<?> getDefinitionClass();
    
}
