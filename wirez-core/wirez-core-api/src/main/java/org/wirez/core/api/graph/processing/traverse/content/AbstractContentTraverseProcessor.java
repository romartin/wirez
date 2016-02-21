package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.tree.TreeTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.inject.Inject;

public abstract class AbstractContentTraverseProcessor<C, N extends Node<View, Edge>, E extends Edge<C, Node>>
        implements ContentTraverseProcessor<C, N, E, ContentTraverseCallback<C, N, E>> {

    TreeWalkTraverseProcessor treeWalkTraverseProcessor;

    @Inject
    public AbstractContentTraverseProcessor(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
    }

    protected abstract boolean doTraverse(final Edge edge);

    protected abstract TreeWalkTraverseProcessor.TraversePolicy getPolicy();
    
    @Override
    public void traverse(final Graph<View, N> graph,
                         final ContentTraverseCallback<C, N, E> callback) {

        treeWalkTraverseProcessor
                .usePolicy(getPolicy())
                .traverse(graph, new TreeTraverseCallback<Graph, Node, Edge>() {

                    @Override
                    public void traverseGraph(final Graph graph) {
                        if (graph.getContent() instanceof View) {
                            callback.traverseView(graph);
                        }
                    }

                    @Override
                    public boolean traverseNode(final Node node) {
                        if (node.getContent() instanceof View) {
                            callback.traverseView((N) node);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean traverseEdge(final Edge edge) {
                        if (doTraverse(edge)) {
                            callback.traverse((E) edge);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void traverseCompleted() {
                        callback.traverseCompleted();
                    }
                });

    }

}
