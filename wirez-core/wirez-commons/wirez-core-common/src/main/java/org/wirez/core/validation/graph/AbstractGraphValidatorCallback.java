package org.wirez.core.validation.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;

public abstract class AbstractGraphValidatorCallback implements GraphValidatorCallback {

    @Override
    public boolean onValidateNode( final Node node ) {
        return true;
    }

    @Override
    public void afterValidateNode( final Node node,
                                   final Iterable<GraphValidationViolation> violations ) {

    }

    @Override
    public boolean onValidateEdge( final Edge edge ) {
        return true;
    }

    @Override
    public void afterValidateEdge( final Edge edge,
                                   final Iterable<GraphValidationViolation> violations ) {

    }

}
