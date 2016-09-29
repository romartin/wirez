package org.kie.workbench.common.stunner.core.diagram;

import org.kie.workbench.common.stunner.core.graph.Graph;

public interface Diagram<G extends Graph, S extends Settings> {

    String getUUID();

    G getGraph();

    S getSettings();

}
