package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Parent;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public final class ParentTraverseProcessorImpl extends AbstractContentTraverseProcessor<Parent, Node<View, Edge>, Edge<Parent, Node>, ContentTraverseCallback<Parent, Node<View, Edge>, Edge<Parent, Node>>>
        implements ParentTraverseProcessor {

    @Inject
    public ParentTraverseProcessorImpl(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        super(treeWalkTraverseProcessor);
    }

    @Override
    protected TreeWalkTraverseProcessor.TraversePolicy getPolicy() {
        return TreeWalkTraverseProcessor.TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE;
    }

    @Override
    protected boolean accepts( final Edge edge ) {
        return edge.getContent() instanceof Parent ;
    }

}
