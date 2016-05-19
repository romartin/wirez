package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public final class ChildrenTraverseProcessorImpl extends AbstractContentTraverseProcessor<Child, Node<View, Edge>, Edge<Child, Node>>
        implements ChildrenTraverseProcessor {

    @Inject
    public ChildrenTraverseProcessorImpl(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        super(treeWalkTraverseProcessor);
    }

    @Override
    protected TreeWalkTraverseProcessor.TraversePolicy getPolicy() {
        return TreeWalkTraverseProcessor.TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE;
    }
    
    @Override
    protected boolean doStartEdgeTraversal(final Edge edge) {
        return edge.getContent() instanceof Child;
    }

    @Override
    protected boolean doEndEdgeTraversal(final Edge edge) {
        return edge.getContent() instanceof Child;
    }

}
