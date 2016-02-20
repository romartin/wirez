package org.wirez.core.api.graph.processing.traverse.content;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.Child;
import org.wirez.core.api.graph.content.Parent;
import org.wirez.core.api.graph.content.Relationship;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.TraverseProcessor;
import org.wirez.core.api.graph.processing.traverse.tree.TreeTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public final class AllEdgesTraverseProcessorImpl implements AllEdgesTraverseProcessor {

    TreeWalkTraverseProcessor treeWalkTraverseProcessor;

    @Inject
    public AllEdgesTraverseProcessorImpl(final TreeWalkTraverseProcessor treeWalkTraverseProcessor) {
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
    }
    
    @Override
    public void traverse(final Graph<View, Node<View, Edge>> graph, 
                         final AllEdgesTraverseCallback<Node<View, Edge>, Edge<Object, Node>> callback) {

        treeWalkTraverseProcessor
                .usePolicy(TreeWalkTraverseProcessor.TraversePolicy.VISIT_EDGE_BEFORE_TARGET_NODE)
                .traverse(graph, new TreeTraverseCallback<Graph, Node, Edge>() {

                    @Override
                    public void traverseGraph(final Graph graph) {
                        if (graph.getContent() instanceof View) {
                            callback.traverseView( graph );
                        }
                    }

                    @Override
                    public boolean traverseNode(final Node node) {
                        if (node.getContent() instanceof View) {
                            callback.traverseView( node );
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean traverseEdge(final Edge edge) {
                        if ( edge.getContent() instanceof View ) {
                            callback.traverseViewEdge( edge );
                        } else if ( edge.getContent() instanceof Child ) {
                            callback.traverseChildEdge( edge );
                        } else if ( edge.getContent() instanceof Parent ) {
                            callback.traverseParentEdge( edge );
                        } else {
                            callback.traverse( edge );
                        }
                        return true;
                    }

                    @Override
                    public void traverseCompleted() {
                        callback.traverseCompleted();
                    }
                });
        
    }
}
