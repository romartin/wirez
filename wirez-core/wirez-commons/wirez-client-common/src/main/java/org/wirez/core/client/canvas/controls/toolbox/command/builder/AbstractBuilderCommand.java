package org.wirez.core.client.canvas.controls.toolbox.command.builder;

import org.uberfire.mvp.Command;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.BuildRequest;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.DragProxyFactory;
import org.wirez.core.client.components.drag.NodeDragProxyCallback;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.util.UUID;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBuilderCommand<I> extends AbstractToolboxCommand<I> {

    private static Logger LOGGER = Logger.getLogger(AbstractBuilderCommand.class.getName());

    protected ClientDefinitionManager clientDefinitionManager;
    protected ClientFactoryServices clientFactoryServices;
    protected GraphBoundsIndexer graphBoundsIndexer;
    protected ShapeAnimation selectionAnimation;
    protected ShapeDeSelectionAnimation deSelectionAnimation;

    protected CanvasHighlight canvasHighlight;

    protected AbstractBuilderCommand() {
        this( null, null, null, null, null );
    }

    public AbstractBuilderCommand(final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryServices clientFactoryServices,
                                  final GraphBoundsIndexer graphBoundsIndexer,
                                  final ShapeAnimation selectionAnimation,
                                  final ShapeDeSelectionAnimation deSelectionAnimation) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.graphBoundsIndexer = graphBoundsIndexer;
        this.selectionAnimation = selectionAnimation;
        this.deSelectionAnimation = deSelectionAnimation;
    }

    protected abstract String getDefinitionIdentifier( Context<AbstractCanvasHandler> context );

    protected abstract DragProxyFactory getDragProxyFactory();

    protected abstract DragProxyCallback getDragProxyCallback( Element element, Element newElement );

    protected abstract BuilderControl getBuilderControl();

    protected abstract Object createtBuilderControlItem( Context<AbstractCanvasHandler> context, Element source, Element newElement );

    protected abstract boolean onDragProxyMove( int x, int y, Element source, Element newElement, Node targetNode );

    protected abstract BuildRequest createBuildRequest( int x, int y, Element source, Element newElement, Node targetNode );

    protected void onDefinitionInstanceBuilt( final Context<AbstractCanvasHandler> context,
                                              final Element source,
                                              final Element newElement,
                                              final Command callback ) {

        callback.execute();

    }

    @Override
    @SuppressWarnings("unchecked")
    public void click(final Context<AbstractCanvasHandler> context,
                        final Element element) {

        final AbstractCanvasHandler canvasHandler = context.getCanvasHandler();
        final double x = context.getX();
        final double y = context.getY();

        graphBoundsIndexer.setRootUUID( canvasHandler.getDiagram().getSettings().getCanvasRootUUID() );

        clientFactoryServices.newElement( UUID.uuid(), getDefinitionIdentifier( context ), new ServiceCallback<Element>() {
            
            @Override
            public void onSuccess( final Element item ) {

                onDefinitionInstanceBuilt( context, element, item, () -> {

                    getBuilderControl().enable( canvasHandler );

                    canvasHighlight = new CanvasHighlight( canvasHandler, selectionAnimation, deSelectionAnimation  );

                    graphBoundsIndexer.build( canvasHandler.getDiagram().getGraph() );

                    DragProxyCallback proxyCallback = getDragProxyCallback( element, item );

                    getDragProxyFactory()
                            .proxyFor( canvasHandler )
                            .newInstance(
                                    createtBuilderControlItem( context, element, item ),
                                    (int) x,
                                    (int) y,
                                    proxyCallback
                            );

                });

            }

            @Override
            public void onError(final ClientRuntimeError error) {
                LOGGER.log(Level.SEVERE, error.toString() );
            }
            
        });
        
    }

    @SuppressWarnings( "unchecked" )
    protected void onStart( final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

    }

    @SuppressWarnings( "unchecked" )
    protected void onMove( final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

        // TODO: Two expensive calls to bounds indexer, this one and the one inside connectorDragProxyFactory.

        final Node targetNode = graphBoundsIndexer.getAt(x1, y1);

        final boolean accepts = onDragProxyMove(x1, y1, element, item, targetNode );

        if ( accepts ) {

            canvasHighlight.highLight( targetNode );

        } else {

            canvasHighlight.unhighLight();

        }

    }

    @SuppressWarnings( "unchecked" )
    protected void onComplete( final Element element,
                         final Element item,
                         final int x1,
                         final int y1 ) {

        final Node targetNode = graphBoundsIndexer.getAt(x1, y1);

        if ( null != targetNode ) {

            final BuildRequest buildRequest = createBuildRequest(x1, y1, element, item, targetNode );

            getBuilderControl().build( buildRequest );


        }

        canvasHighlight.unhighLight();

    }

    @Override
    public void destroy() {
      
        this.getDragProxyFactory().destroy();
        this.getBuilderControl().disable();
        this.graphBoundsIndexer.destroy();
        this.canvasHighlight.destroy();
        
        this.clientDefinitionManager = null;
        this.clientFactoryServices = null;
        this.graphBoundsIndexer = null;
        this.selectionAnimation = null;
        this.deSelectionAnimation = null;
        this.canvasHighlight = null;
        
    }
    
}
