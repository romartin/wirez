package org.wirez.core.validation.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.validation.ValidatorCallback;

public interface GraphValidatorCallback extends ValidatorCallback<GraphValidationViolation> {

    boolean onValidateNode( Node node );

    void afterValidateNode( Node node, Iterable<GraphValidationViolation> violations );

    boolean onValidateEdge( Edge edge );

    void afterValidateEdge( Edge edge, Iterable<GraphValidationViolation> violations );

}
