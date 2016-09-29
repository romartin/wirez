package org.kie.workbench.common.stunner.core.validation.graph;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.validation.ValidatorCallback;

public interface GraphValidatorCallback extends ValidatorCallback<GraphValidationViolation> {

    boolean onValidateNode( Node node );

    void afterValidateNode( Node node, Iterable<GraphValidationViolation> violations );

    boolean onValidateEdge( Edge edge );

    void afterValidateEdge( Edge edge, Iterable<GraphValidationViolation> violations );

}
