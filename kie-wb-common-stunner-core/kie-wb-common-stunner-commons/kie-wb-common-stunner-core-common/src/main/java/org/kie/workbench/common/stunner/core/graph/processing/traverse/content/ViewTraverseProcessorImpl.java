package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public final class ViewTraverseProcessorImpl extends AbstractContentTraverseProcessor<View<?>, Node<View, Edge>, Edge<View<?>, Node>, ContentTraverseCallback<View<?>, Node<View, Edge>, Edge<View<?>, Node>>>
        implements ViewTraverseProcessor {

    @Inject
    public ViewTraverseProcessorImpl(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        super(treeWalkTraverseProcessor);
    }

    @Override
    protected TreeWalkTraverseProcessor.EdgeVisitorPolicy getPolicy() {
        return TreeWalkTraverseProcessor.EdgeVisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE;
    }

    @Override
    protected boolean accepts( final Edge edge ) {
        return edge.getContent() instanceof View ;
    }

}
