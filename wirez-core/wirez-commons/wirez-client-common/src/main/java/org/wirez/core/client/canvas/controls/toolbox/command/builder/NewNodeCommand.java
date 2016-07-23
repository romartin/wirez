package org.wirez.core.client.canvas.controls.toolbox.command.builder;

import com.google.gwt.user.client.Timer;
import org.uberfire.mvp.Command;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequestImpl;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.NodeDragProxyCallback;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.util.UUID;

import javax.enterprise.event.Event;


public abstract class NewNodeCommand<I> extends AbstractElementBuilderCommand<I> {

    NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory;
    NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl;
    Event<CanvasElementSelectedEvent> elementSelectedEvent;
    DefinitionUtils definitionUtils;

    protected String definitionId;
    protected int sourceMagnet;
    protected int targetMagnet;

    protected NewNodeCommand() {
        this( null, null, null, null, null, null, null, null, null, null );
    }

    public NewNodeCommand(final ClientDefinitionManager clientDefinitionManager,
                          final ClientFactoryServices clientFactoryServices,
                          final ShapeManager shapeManager,
                          final DefinitionGlyphTooltip<?> glyphTooltip,
                          final GraphBoundsIndexer graphBoundsIndexer,
                          final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                          final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                          final AnimationFactory animationFactory,
                          final DefinitionUtils definitionUtils,
                          final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        super( clientDefinitionManager, clientFactoryServices, shapeManager, glyphTooltip, graphBoundsIndexer,
                animationFactory );
        this.nodeDragProxyFactory = nodeDragProxyFactory;
        this.nodeBuilderControl = nodeBuilderControl;
        this.definitionUtils = definitionUtils;
        this.elementSelectedEvent = elementSelectedEvent;
    }

    public void setDefinitionIdentifier( final String definitionId ) {

        this.definitionId = definitionId;

    }

    protected String getEdgeIdentifier( final Context<AbstractCanvasHandler> context ) {

        final String defSetId = context.getCanvasHandler().getDiagram().getSettings().getDefinitionSetId();

        return definitionUtils.getDefaultConnectorId( defSetId );
    }

    @Override
    protected String getDefinitionIdentifier( final Context<AbstractCanvasHandler> context ) {
        return getEdgeIdentifier( context );
    }

    @Override
    protected String getGlyphDefinitionId() {
        return this.definitionId;
    }

    @Override
    public String getTitle() {
        return "Creates a new node";
    }


    @Override
    protected DragProxyFactory getDragProxyFactory() {
        return nodeDragProxyFactory;
    }

    @Override
    protected BuilderControl getBuilderControl() {
        return nodeBuilderControl;
    }

    @Override
    protected DragProxyCallback getDragProxyCallback( final Context<AbstractCanvasHandler> context,
                                                      final Element element,
                                                      final Element item ) {

        return new NodeDragProxyCallback() {

            @Override
            public void onStart( final int x,
                                 final int y ) {

                NewNodeCommand.this.onStart(  context, element, item, x, y );

            }

            @Override
            public void onMove( final int x,
                                final int y ) {

                NewNodeCommand.this.onMove(  context, element, item, x, y );

            }

            @Override
            public void onComplete( final int x,
                                    final int y ) {

            }

            @Override
            public void onComplete( final int x,
                                    final int y, final int sourceMagnet,
                                    final int targetMagnet ) {

                NewNodeCommand.this.sourceMagnet = sourceMagnet;
                NewNodeCommand.this.targetMagnet = targetMagnet;

                NewNodeCommand.this.onComplete(  context, element, item, x, y );

            }

        };

    }

    @Override
    protected void onStart( final Context<AbstractCanvasHandler> context,
                            final Element element,
                            final Element item,
                            final int x1,
                            final int y1 ) {
        super.onStart( context, element, item, x1, y1 );

        // Disable layer events handlers in order to avoid layer events while using the drag proxy.
        getLayer( context ).disableHandlers();

    }

    @Override
    protected void onItemBuilt( final Context<AbstractCanvasHandler> context,
                                final String uuid ) {
        super.onItemBuilt( context, uuid );

        fireElementSelectedEvent( elementSelectedEvent, context.getCanvasHandler(), uuid );

    }

    @Override
    protected void onError( final Context<AbstractCanvasHandler> context,
                            final ClientRuntimeError error ) {
        super.onError( context, error );

        // Enable layer events handlers again.
        getLayer( context ).enableHandlers();
    }

    protected Layer getLayer( final Context<AbstractCanvasHandler> context ) {

        return context.getCanvasHandler().getCanvas().getLayer();

    }

    @Override
    protected void onDefinitionInstanceBuilt( final Context<AbstractCanvasHandler> context,
                                              final Element source,
                                              final Element newElement,
                                              final Command callback) {


        final Node<View<?>, Edge> sourceNode = (Node<View<?>, Edge>) source;
        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;

        // Create the new node.
        final Node<View<?>, Edge> node = clientFactoryServices.newElement( UUID.uuid(), definitionId );

        // Perform the temporal proxy connections.
        edge.setSourceNode( sourceNode );
        edge.setTargetNode( node );

        super.onDefinitionInstanceBuilt( context, source, newElement, callback );

    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object createtBuilderControlItem( final Context<AbstractCanvasHandler> context,
                                                final Element source,
                                                final Element newElement ) {

        final Node<View<?>, Edge> sourceNode = (Node<View<?>, Edge>) source;
        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;
        final String edgeId = getDefinitionId( edge.getContent().getDefinition() );

        final ShapeFactory<?, AbstractCanvasHandler, ?> nodeShapeFactory = shapeManager.getFactory( definitionId );
        final ShapeFactory<?, AbstractCanvasHandler, ?> edgeShapeFactory = shapeManager.getFactory( edgeId );

        return new NodeDragProxyFactory.Item<AbstractCanvasHandler>() {
            @Override
            public Node<View<?>, Edge> getNode() {
                return edge.getTargetNode();
            }

            @Override
            public ShapeFactory<?, AbstractCanvasHandler, ?> getNodeShapeFactory() {
                return nodeShapeFactory;
            }

            @Override
            public Edge<View<?>, Node> getInEdge() {
                return edge;
            }

            @Override
            public Node<View<?>, Edge> getInEdgeSourceNode() {
                return sourceNode;
            }

            @Override
            public ShapeFactory<?, AbstractCanvasHandler, ?> getInEdgeShapeFactory() {
                return edgeShapeFactory;
            }


        };

    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean onDragProxyMove( final int x,
                                       final int y,
                                       final Element source,
                                       final Element newElement,
                                       final Node parent ) {

        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;
        final Node<View<?>, Edge> node = (Node<View<?>, Edge>) edge.getTargetNode();

        final NodeBuildRequest request = new NodeBuildRequestImpl( x, y , node, edge );

        final boolean accepts = nodeBuilderControl.allows( request );

        if ( accepts ) {

            if ( null != parent ) {

                return true;

            }

        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected BuildRequest createBuildRequest(final int x,
                                              final int y,
                                              final Element source,
                                              final Element newElement,
                                              final Node targetNode) {

        final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) newElement;
        final Node<View<?>, Edge> node = (Node<View<?>, Edge>) edge.getTargetNode();

        return new NodeBuildRequestImpl( x, y , node, edge, this.sourceMagnet, this.targetMagnet  );
    }

    @Override
    public void destroy() {

        super.destroy();

        this.nodeDragProxyFactory = null;
        this.nodeBuilderControl = null;

    }

    protected String getDefinitionId( final Object def ) {
        return definitionUtils.getDefinitionId( def );
    }

}
