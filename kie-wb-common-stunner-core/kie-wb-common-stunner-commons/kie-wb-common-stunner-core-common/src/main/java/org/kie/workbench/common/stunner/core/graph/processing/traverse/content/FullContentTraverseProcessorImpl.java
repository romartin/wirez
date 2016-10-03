package org.kie.workbench.common.stunner.core.graph.processing.traverse.content;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Child;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Parent;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.FullContentTraverseCallback;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.FullContentTraverseProcessor;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.tree.TreeTraverseCallback;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public final class FullContentTraverseProcessorImpl implements FullContentTraverseProcessor {

    TreeWalkTraverseProcessor treeWalkTraverseProcessor;

    @Inject
    public FullContentTraverseProcessorImpl( final TreeWalkTraverseProcessor treeWalkTraverseProcessor ) {
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void traverse( final Graph<View, Node<View, Edge>> graph,
                          final FullContentTraverseCallback<Node<View, Edge>, Edge<Object, Node>> callback ) {

        treeWalkTraverseProcessor
                .useEdgeVisitorPolicy( TreeWalkTraverseProcessor.EdgeVisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE )
                .traverse( graph, new TreeTraverseCallback<Graph, Node, Edge>() {

                    @Override
                    public void startGraphTraversal( final Graph graph ) {
                        if ( graph.getContent() instanceof DefinitionSet ) {
                            callback.startGraphTraversal( graph );
                        }
                    }

                    @Override
                    public boolean startNodeTraversal( final Node node ) {
                        if ( node.getContent() instanceof View ) {
                            callback.startNodeTraversal( node );
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean startEdgeTraversal( final Edge edge ) {
                        if ( edge.getContent() instanceof View ) {
                            callback.startViewEdgeTraversal( edge );
                        } else if ( edge.getContent() instanceof Child ) {
                            callback.startChildEdgeTraversal( edge );
                        } else if ( edge.getContent() instanceof Parent ) {
                            callback.startParentEdgeTraversal( edge );
                        } else {
                            callback.startEdgeTraversal( edge );
                        }
                        return true;
                    }

                    @Override
                    public void endNodeTraversal( final Node node ) {
                        if ( node.getContent() instanceof View ) {
                            callback.endNodeTraversal( node );
                        }
                    }

                    @Override
                    public void endEdgeTraversal( final Edge edge ) {
                        if ( edge.getContent() instanceof View ) {
                            callback.endViewEdgeTraversal( edge );
                        } else if ( edge.getContent() instanceof Child ) {
                            callback.endChildEdgeTraversal( edge );
                        } else if ( edge.getContent() instanceof Parent ) {
                            callback.endParentEdgeTraversal( edge );
                        } else {
                            callback.endEdgeTraversal( edge );
                        }
                    }

                    @Override
                    public void endGraphTraversal() {
                        callback.endGraphTraversal();
                    }
                } );

    }
}
