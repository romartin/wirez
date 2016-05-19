package org.wirez.core.graph.processing.traverse.content;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.tree.TreeTraverseCallback;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.inject.Inject;

public abstract class AbstractContentTraverseProcessor<C, N extends Node<View, Edge>, E extends Edge<C, Node>>
        implements ContentTraverseProcessor<C, N, E, ContentTraverseCallback<C, N, E>> {

    TreeWalkTraverseProcessor treeWalkTraverseProcessor;

    @Inject
    public AbstractContentTraverseProcessor(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
    }

    protected abstract boolean doStartEdgeTraversal(final Edge edge);

    protected abstract boolean doEndEdgeTraversal(final Edge edge);

    protected abstract TreeWalkTraverseProcessor.TraversePolicy getPolicy();
    
    @Override
    public void traverse(final Graph<View, N> graph,
                         final ContentTraverseCallback<C, N, E> callback) {

        treeWalkTraverseProcessor
                .usePolicy(getPolicy())
                .traverse(graph, new TreeTraverseCallback<Graph, Node, Edge>() {

                    @Override
                    public void startGraphTraversal(final Graph graph) {
                        if (graph.getContent() instanceof View) {
                            callback.startGraphTraversal(graph);
                        }
                    }

                    @Override
                    public boolean startNodeTraversal(final Node node) {
                        if (node.getContent() instanceof View) {
                            callback.startNodeTraversal((N) node);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean startEdgeTraversal(final Edge edge) {
                        if (doStartEdgeTraversal(edge)) {
                            callback.startEdgeTraversal((E) edge);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void endNodeTraversal(final Node node) {
                        if (node.getContent() instanceof View) {
                            callback.endNodeTraversal((N) node);
                        }
                    }

                    @Override
                    public void endEdgeTraversal(final Edge edge) {
                        if (doEndEdgeTraversal( edge )) {
                            callback.endEdgeTraversal((E) edge);
                        }
                    }

                    @Override
                    public void endGraphTraversal() {
                        callback.endGraphTraversal();
                    }
                });

    }

}
