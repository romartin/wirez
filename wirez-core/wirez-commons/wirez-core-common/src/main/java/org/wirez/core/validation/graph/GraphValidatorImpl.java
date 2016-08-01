package org.wirez.core.validation.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.relationship.Dock;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.traverse.tree.AbstractTreeTraverseCallback;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.rule.RuleManager;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.rule.graph.GraphRulesManager;
import org.wirez.core.validation.AbstractValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

@ApplicationScoped
public class GraphValidatorImpl
        extends AbstractValidator<Graph<?, Node<?, Edge>>, GraphValidatorCallback>
        implements GraphValidator {

    TreeWalkTraverseProcessor treeWalkTraverseProcessor;

    private GraphRulesManager rulesManager;

    @Inject
    public GraphValidatorImpl( final TreeWalkTraverseProcessor treeWalkTraverseProcessor ) {
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
    }

    @Override
    public GraphValidator withRulesManager( final GraphRulesManager rulesManager ) {
        this.rulesManager = rulesManager;
        return this;
    }

    @Override
    public void validate( final Graph<?, Node<?, Edge>> graph,
                          final GraphValidatorCallback callback ) {
        assert this.rulesManager != null;

        final Collection<GraphValidationViolation> violations = validate( graph, this.rulesManager, callback );

        if ( isValid( violations ) ) {

            callback.onSuccess();

        } else {

            callback.onFail( violations );

        }

    }

    @SuppressWarnings( "unchecked" )
    private Collection<GraphValidationViolation> validate( final Graph graph,
                                               final GraphRulesManager rulesManager,
                                               final GraphValidatorCallback callback ) {

        final Collection<GraphValidationViolation> result = new LinkedList<GraphValidationViolation>();

        treeWalkTraverseProcessor
                .useEdgeVisitorPolicy( TreeWalkTraverseProcessor.EdgeVisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE )
                .traverse( graph, new AbstractTreeTraverseCallback<Graph, Node, Edge>() {

                    private final Stack<Node> currentParents = new Stack<Node>();

                    @Override
                    public void startGraphTraversal( final Graph graph ) {
                        super.startGraphTraversal( graph );
                        currentParents.clear();
                    }

                    @Override
                    public boolean startEdgeTraversal( final Edge edge ) {
                        super.startEdgeTraversal( edge );

                        if ( callback.onValidateEdge( edge ) ) {

                            final Collection<GraphValidationViolation> _violations = new LinkedList<GraphValidationViolation>();

                            final Object content = edge.getContent();

                            if ( content instanceof Child ) {

                                this.currentParents.push( edge.getSourceNode() );

                            } else if ( content instanceof View ) {

                                // Evaluate containment rules for this edge.
                                final Iterable<RuleViolation> cardinalityViolations = evaluateEdgeCardinality( rulesManager, edge );
                                addViolations( edge, _violations, cardinalityViolations );

                            } else if ( content instanceof Dock ) {

                                final Node parent = edge.getSourceNode();
                                final Node docked = edge.getTargetNode();

                                // Evaluate docking rules for the source & target nodes.
                                final Iterable<RuleViolation> dockingViolations =
                                        evaluateDocking( rulesManager, parent, docked );
                                addViolations( parent, _violations, dockingViolations );

                            }

                            callback.afterValidateEdge( edge, _violations );

                            if ( !_violations.isEmpty() ) {

                                result.addAll( _violations );
                            }


                        }

                        return true;
                    }

                    @Override
                    public void endEdgeTraversal( final Edge edge ) {
                        super.endEdgeTraversal( edge );

                        final Object content = edge.getContent();

                        if ( content instanceof Child ) {

                            this.currentParents.pop();

                        }

                    }

                    @Override
                    public boolean startNodeTraversal( final Node node ) {
                        super.startNodeTraversal( node );

                        if ( callback.onValidateNode( node ) ) {

                            evaluateNode( node, currentParents.isEmpty() ? null : currentParents.peek() );

                        }

                        return true;

                    }

                    @Override
                    public void endNodeTraversal( final Node node ) {
                        super.endNodeTraversal( node );
                    }

                    @Override
                    public void endGraphTraversal() {
                        super.endGraphTraversal();
                    }

                    private void evaluateNode( final Node node,
                                               final Node parent ) {

                        if ( null != node ) {

                            final Collection<GraphValidationViolation> _violations = new LinkedList<GraphValidationViolation>();

                            // Evaluate containment rules for this node.
                            final Iterable<RuleViolation> containmentViolations = null != parent ?
                                    evaluateContainment( rulesManager, parent, node ) : evaluateContainment( rulesManager, graph, node );
                            addViolations( node, _violations, containmentViolations);

                            // Evaluate cardinality rules for this node.
                            final Iterable<RuleViolation> cardinalityViolations = evaluateCardinality( rulesManager, graph, node );
                            addViolations( node, _violations, cardinalityViolations);

                            callback.afterValidateNode( node, _violations );

                            if ( !_violations.isEmpty() ) {

                                result.addAll( _violations );
                            }

                        }

                    }

                } );

        return result;
    }

    private void addViolations( final Element element,
                                final Collection<GraphValidationViolation> result,
                                final Iterable<RuleViolation> violations ) {

        if ( null != violations && violations.iterator().hasNext() ) {

            final Iterator<RuleViolation> it = violations.iterator();

            while ( it.hasNext() ) {

                final RuleViolation violation = it.next();

                final GraphValidationViolation graphValidationViolation = new GraphValidationViolationImpl( element, violation );

                result.add( graphValidationViolation );

            }

        }

    }

    @SuppressWarnings( "unchecked" )
    private Iterable<RuleViolation> evaluateContainment( final GraphRulesManager rulesManager,
                                                         final Graph target,
                                                         final Node candidate ) {
        return rulesManager.containment().evaluate( target, candidate ).violations();
    }

    @SuppressWarnings( "unchecked" )
    private Iterable<RuleViolation> evaluateContainment( final GraphRulesManager rulesManager,
                                                         final Node parent,
                                                         final Node candidate ) {
        return rulesManager.containment().evaluate( parent, candidate ).violations();
    }

    @SuppressWarnings( "unchecked" )
    private Iterable<RuleViolation> evaluateCardinality( final GraphRulesManager rulesManager,
                                                         final Graph target,
                                                         final Node candidate ) {
        return rulesManager.cardinality().evaluate( target, candidate, RuleManager.Operation.ADD ).violations();
    }

    @SuppressWarnings( "unchecked" )
    private Iterable<RuleViolation> evaluateDocking( final GraphRulesManager rulesManager,
                                                     final Node parent,
                                                     final Node candidate ) {
        return rulesManager.docking().evaluate( parent, candidate ).violations();
    }

    @SuppressWarnings( "unchecked" )
    private Iterable<RuleViolation> evaluateEdgeCardinality( final GraphRulesManager rulesManager,
                                                             final Edge<? extends View, Node> edge ) {
        return rulesManager
                .edgeCardinality()
                .evaluate( ( Edge<? extends View<?>, Node> ) edge,
                        edge.getSourceNode(),
                        edge.getTargetNode(),
                        edge.getSourceNode() != null ? edge.getSourceNode().getOutEdges() : null,
                        edge.getTargetNode() != null ? edge.getTargetNode().getInEdges() : null,
                        RuleManager.Operation.ADD )
                .violations();
    }

    private boolean isValid( final Collection<GraphValidationViolation> violations ) {
        return violations == null || violations.isEmpty();
    }

}
