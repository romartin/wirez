package org.wirez.core.client.canvas.command.impl;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.AbstractCanvasGraphCommand;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.util.ShapeUtils;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.impl.MorphNodeCommand;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.relationship.Dock;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.RuleViolation;

import java.util.List;

public final class MorphCanvasNodeCommand extends AbstractCanvasGraphCommand {

    private Node<? extends Definition<?>, Edge> candidate;
    private MorphDefinition morphDefinition;
    private String morphTarget;
    private ShapeFactory factory;
    private String oldMorphTarget;

    public MorphCanvasNodeCommand( final Node<? extends Definition<?>, Edge> candidate,
                                   final MorphDefinition morphDefinition,
                                   final String morphTarget,
                                   final ShapeFactory factory ) {
        this.candidate = candidate;
        this.morphDefinition = morphDefinition;
        this.morphTarget = morphTarget;
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult<CanvasViolation> execute( final AbstractCanvasHandler context) {

        // Keep undo metadata.
        final Object definition = candidate.getContent().getDefinition();
        this.oldMorphTarget = context.getClientDefinitionManager()
                .adapters()
                .forDefinition()
                .getId( definition );

        // Deregister the existing shape.
        Node parent = getParent();
        if ( null != parent ) {
            context.removeChild( parent.getUUID(), candidate.getUUID()  );
        }
        context.deregister( candidate );

        // Register the shape for the new morphed element.
        context.register( factory, candidate );
        if ( null != parent ) {
            context.addChild( parent, candidate );
        }
        context.applyElementMutation( candidate, MutationContext.STATIC );

        // Update incoming connections for new shape ( so magnets, connectors, etc on view side ).
        final List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {

            for ( final Edge inEdge : inEdges ) {

                if ( isViewEdge(  inEdge ) ) {

                    final Node inNode = inEdge.getSourceNode();

                    updateConnections( context, inEdge, inNode, candidate );

                }

            }

        }

        // Update outgoing connections as well for new shape.
        final List<Edge> outEdges = candidate.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {

            for ( final Edge outEdge : outEdges ) {

                if ( isViewEdge(  outEdge ) ) {

                    final Node targetNode = outEdge.getTargetNode();

                    updateConnections( context, outEdge, candidate, targetNode );

                }

            }

        }


        return buildResult();
    }

    @SuppressWarnings( "unchecked" )
    private void updateConnections( final AbstractCanvasHandler context,
                                    final Edge edge,
                                    final Node sourceNode,
                                    final Node targetNode ) {

        if ( null != edge && null != sourceNode && null != targetNode ) {

            final EdgeShape edgeShape = ( EdgeShape ) context.getCanvas().getShape(  edge.getUUID() );
            final Shape sourceNodeShape = context.getCanvas().getShape(  sourceNode.getUUID() );
            final Shape targetNodeShape = context.getCanvas().getShape(  targetNode.getUUID() );

            edgeShape.applyConnections( edge,
                    sourceNodeShape.getShapeView(),
                    targetNodeShape.getShapeView(),
                    MutationContext.STATIC );

        }

    }

    @Override
    public CommandResult<CanvasViolation> undo(final AbstractCanvasHandler context) {
        final ShapeFactory factory = ShapeUtils.getDefaultShapeFactory( context, candidate );
        final MorphCanvasNodeCommand command =
                new MorphCanvasNodeCommand( candidate, morphDefinition, oldMorphTarget, factory );

        return command.execute( context );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(AbstractCanvasHandler context) {
        return new MorphNodeCommand( (Node) candidate, morphDefinition, morphTarget );
    }

    protected Node getParent() {
        List<Edge> inEdges = candidate.getInEdges();
        if ( null != inEdges && !inEdges.isEmpty() ) {
            for ( final Edge edge : inEdges ) {
                if ( isChildEdge( edge ) || isDockEdge( edge ) ) {
                    return edge.getSourceNode();
                }

            }
        }

        return null;
    }

    private boolean isChildEdge( final Edge edge ) {
        return edge.getContent() instanceof Child;
    }

    private boolean isDockEdge( final Edge edge ) {
        return edge.getContent() instanceof Dock;
    }

    private boolean isViewEdge( final Edge edge ) {
        return edge.getContent() instanceof View;
    }

}
