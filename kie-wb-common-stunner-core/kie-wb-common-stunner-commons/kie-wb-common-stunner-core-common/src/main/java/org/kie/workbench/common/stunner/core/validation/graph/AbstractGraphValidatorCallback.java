package org.kie.workbench.common.stunner.core.validation.graph;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.validation.graph.GraphValidationViolation;
import org.kie.workbench.common.stunner.core.validation.graph.GraphValidatorCallback;

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
