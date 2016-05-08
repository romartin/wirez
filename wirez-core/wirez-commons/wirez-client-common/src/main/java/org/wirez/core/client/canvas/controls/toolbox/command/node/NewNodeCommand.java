package org.wirez.core.client.canvas.controls.toolbox.command.node;

import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.definition.Definition;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.api.lookup.util.CommonLookups;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.NodeBuildRequestImpl;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.DragProxyCallback;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NewNodeCommand<I> extends AbstractToolboxCommand<I> {

    private static Logger LOGGER = Logger.getLogger(NewNodeCommand.class.getName());
    private static final int PADDING = 10;
    private static final int ICON_SIZE = 25;
    
    private final I icon;

    ClientFactoryServices clientFactoryServices;
    CommonLookups commonLookups;
    ShapeManager shapeManager;
    GlyphTooltip glyphTooltip;
    Palette<?, ShapeGlyph<?>> glyphMiniPalette;
    NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory;
    NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl;
    GraphBoundsIndexer graphBoundsIndexer;
    ShapeAnimation selectionAnimation;
    ShapeDeSelectionAnimation deSelectionAnimation;
    
    private String[] definitionIds;
    private ShapeFactory<?, ?, ?>[] factories;
    private AbstractCanvasHandler canvasHandler;
    private CanvasHighlight canvasHighlight;
    private Node<? extends Definition<Object>, ? extends Edge> sourceNode;

    public NewNodeCommand(final ClientFactoryServices clientFactoryServices,
                          final CommonLookups commonLookups,
                          final ShapeManager shapeManager,
                          final GlyphTooltip glyphTooltip,
                          final Palette<?, ShapeGlyph<?>> glyphMiniPalette,
                          final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                          final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                          final GraphBoundsIndexer graphBoundsIndexer,
                          final ShapeAnimation selectionAnimation,
                          final ShapeDeSelectionAnimation deSelectionAnimation,
                          final I icon) {
        this.clientFactoryServices = clientFactoryServices;
        this.commonLookups = commonLookups;
        this.shapeManager = shapeManager;
        this.glyphTooltip = glyphTooltip;
        this.glyphMiniPalette = glyphMiniPalette;
        this.nodeDragProxyFactory = nodeDragProxyFactory;
        this.nodeBuilderControl = nodeBuilderControl;
        this.graphBoundsIndexer = graphBoundsIndexer;
        this.selectionAnimation = selectionAnimation;
        this.deSelectionAnimation = deSelectionAnimation;
        this.icon = icon;
    }
    
    protected abstract String getDefinitionSetIdentifier();

    protected abstract String getEdgeIdentifier();
    
    protected void initialize() {
        glyphMiniPalette.setItemOutCallback(NewNodeCommand.this::onItemOut);
        glyphMiniPalette.setItemHoverCallback(NewNodeCommand.this::onItemHover);
        glyphMiniPalette.setItemClickCallback(NewNodeCommand.this::onItemClick);
        glyphMiniPalette.setItemMouseDownCallback(NewNodeCommand.this::onItemMouseDown);
        glyphMiniPalette.setPadding( PADDING );
        glyphMiniPalette.setIconSize( ICON_SIZE );
    }

    @Override
    public I getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return "Creates a new node";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void mouseEnter(final Context<AbstractCanvasHandler> context, 
                        final Element element) {

        if ( null == getEdgeIdentifier() ) {
            throw new NullPointerException(" New node command requires an edge identifier.");
        }
        
        this.canvasHandler = context.getCanvasHandler();
        final AbstractCanvas canvas = canvasHandler.getCanvas();
        final org.wirez.core.client.shape.Shape shape = canvas.getShape( element.getUUID() );
        this.sourceNode = (Node<? extends Definition<Object>, ? extends Edge>) element;
        
        
        final Set<String> allowedDefinitions = commonLookups.getAllowedDefinitions( "org.wirez.bpmn.api.BPMNDefinitionSet", 
                canvasHandler.getDiagram().getGraph(), this.sourceNode,  "org.wirez.bpmn.api.SequenceFlow", 0, 10);
        
        log( Level.FINE, "Allowed Definitions -> " + allowedDefinitions );
        
        if ( null != allowedDefinitions && !allowedDefinitions.isEmpty() ) {

            this.definitionIds = new String[ allowedDefinitions.size() ];
            this.factories = new ShapeFactory<?, ? ,?>[ allowedDefinitions.size() ];
            
            final int cx = (int) context.getX();
            final int cy = (int) context.getY();
            final List<ShapeGlyph> glyphs = new LinkedList<>();
            int counter = 0;
            for ( final String allowedDefId : allowedDefinitions ) {
                final ShapeFactory<?, ? ,?> factory = shapeManager.getFactory( allowedDefId );
                final ShapeGlyph glyph = factory.getGlyphFactory( allowedDefId ).build( allowedDefId, 50, 50);

                this.definitionIds[counter] = allowedDefId;
                this.factories[counter] = factory;
                glyphs.add( glyph );
                counter++;
            }
            
            glyphMiniPalette.setX( cx );
            glyphMiniPalette.setY( cy );
            glyphMiniPalette.show( canvas.getLayer(), glyphs.toArray( new ShapeGlyph[ glyphs.size() ] ) );

        }
        
    }
    
    public void clear() {
        this.definitionIds = null;
        this.factories = null;
        glyphMiniPalette.clear();
    }

    void onItemHover(final int index,
                     final double x,
                     final double y) {

        final ShapeFactory<?, ?, ?> factory = factories[ index ];
        final String id = definitionIds[index];
        final ShapeGlyph glyph = factory.getGlyphFactory( id ).build ( id, 100, 100 );
        final double px = x + PADDING + ICON_SIZE;
        // TODO: Avoid y value hardcoding - use canvas widget absolute top as relative?
        final double py = y - 75;
        glyphTooltip.show( glyph, factory.getDescription( id ), px, py );
        
    }

    void onItemOut(final int index) {

        glyphTooltip.hide();
        
    }
    
    void onItemClick(final int index,
                         final int x,
                         final int y) {

        // TODO
        
        GWT.log("Click - [index=" + index + ", x=" + x + ", y=" + y + "]");
        
    }
    
    void onItemMouseDown(final int index,
                        final int x,
                        final int y) {

        GWT.log("MouseDown - [index=" + index + ", x=" + x + ", y=" + y + "]");

        final String defId = definitionIds[ index ];
        final ShapeFactory<?, ?, ?> factory = factories[ index ];


        clientFactoryServices.newElement( UUID.uuid(), getEdgeIdentifier(), new ServiceCallback<Element>() {

            @Override
            public void onSuccess(final Element edgeItem) {
                
                final Edge<View<?>, Node> edge = (Edge<View<?>, Node>) edgeItem;

                // Manually set the source node as the drag proxy will need it.
                edge.setSourceNode( sourceNode );
                
                clientFactoryServices.newElement( UUID.uuid(), defId, new ServiceCallback<Element>() {
                    
                    @Override
                    public void onSuccess(final Element nodeItem) {

                        final Node<View<?>, Edge> node = (Node<View<?>, Edge>) nodeItem;
                        final ShapeFactory<?, AbstractCanvasHandler, ?> nodeShapeFactory = shapeManager.getFactory( defId );
                        final ShapeFactory<?, AbstractCanvasHandler, ?> edgeShapeFactory = shapeManager.getFactory( getEdgeIdentifier() );
                        
                        
                        final NodeDragProxyFactory.Item<AbstractCanvasHandler> item = new NodeDragProxyFactory.Item<AbstractCanvasHandler>() {
                            @Override
                            public Node<View<?>, Edge> getNode() {
                                return node;
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
                                return edge.getSourceNode();
                            }

                            @Override
                            public ShapeFactory<?, AbstractCanvasHandler, ?> getInEdgeShapeFactory() {
                                return edgeShapeFactory;
                            }

                           
                        };
                        
                        nodeBuilderControl.enable( canvasHandler );
                        
                        canvasHighlight = new CanvasHighlight( canvasHandler,
                                        selectionAnimation, deSelectionAnimation );
                        
                        graphBoundsIndexer.build( canvasHandler.getDiagram().getGraph() );

                        nodeDragProxyFactory
                                .proxyFor( canvasHandler )
                                .newInstance(item, x, y, new DragProxyCallback() {
                                    
                                    @Override
                                    public void onStart(final int x, 
                                                        final int y) {
                                        
                                    }

                                    @Override
                                    public void onMove(final int x, 
                                                       final int y) {

                                        final NodeBuildRequest request = new NodeBuildRequestImpl( x, y , node, edge );
                                        final boolean accepts = nodeBuilderControl.allows( request );
                                        
                                        if ( accepts ) {

                                            final Node parent = graphBoundsIndexer.getAt( x, y );
                                            
                                            if ( null != parent ) {
                                                canvasHighlight.highLight( parent );
                                            }
                                            
                                        } else {
                                            
                                            canvasHighlight.unhighLight();
                                            
                                        }
                                        
                                    }

                                    @Override
                                    public void onComplete(final int x, 
                                                           final int y) {

                                        final NodeBuildRequest request = new NodeBuildRequestImpl( x, y , node, edge );
                                        nodeBuilderControl.build( request );

                                        nodeBuilderControl.disable();

                                        canvasHighlight.unhighLight();
                                        
                                    }
                                });

                    }

                    @Override
                    public void onError(final ClientRuntimeError error) {
                        log( Level.SEVERE, error.toString() );
                    }
                });
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                log( Level.SEVERE, error.toString() );
            }
        });

    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
