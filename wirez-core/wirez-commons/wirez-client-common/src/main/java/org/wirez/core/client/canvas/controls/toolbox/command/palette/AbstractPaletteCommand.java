package org.wirez.core.client.canvas.controls.toolbox.command.palette;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.Context;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPalette;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.wirez.core.client.components.palette.view.PaletteView;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPaletteCommand<I> extends AbstractToolboxCommand<I> {

    private static Logger LOGGER = Logger.getLogger(AbstractPaletteCommand.class.getName());

    private final I icon;

    protected ClientFactoryServices clientFactoryServices;
    protected CommonLookups commonLookups;
    protected ShapeManager shapeManager;
    protected DefinitionsPaletteBuilder definitionsPaletteBuilder;
    protected Palette<HasPaletteItems<? extends GlyphPaletteItem>> palette;
    protected NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory;
    protected NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl;
    protected GraphBoundsIndexer graphBoundsIndexer;
    protected ShapeAnimation selectionAnimation;
    protected ShapeDeSelectionAnimation deSelectionAnimation;
    
    protected AbstractCanvasHandler canvasHandler;
    protected CanvasHighlight canvasHighlight;
    protected Node<? extends Definition<Object>, ? extends Edge> sourceNode;
    protected boolean paletteVisible;

    protected AbstractPaletteCommand() {
        this( null, null, null, null, null, null, null, null, null, null, null );
    }

    public AbstractPaletteCommand(final ClientFactoryServices clientFactoryServices,
                                  final CommonLookups commonLookups,
                                  final ShapeManager shapeManager,
                                  final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                  final Palette<HasPaletteItems<? extends GlyphPaletteItem>> palette,
                                  final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                                  final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                  final GraphBoundsIndexer graphBoundsIndexer,
                                  final ShapeAnimation selectionAnimation,
                                  final ShapeDeSelectionAnimation deSelectionAnimation,
                                  final I icon) {
        this.clientFactoryServices = clientFactoryServices;
        this.commonLookups = commonLookups;
        this.shapeManager = shapeManager;
        this.definitionsPaletteBuilder = definitionsPaletteBuilder;
        this.palette = palette;
        this.nodeDragProxyFactory = nodeDragProxyFactory;
        this.nodeBuilderControl = nodeBuilderControl;
        this.graphBoundsIndexer = graphBoundsIndexer;
        this.selectionAnimation = selectionAnimation;
        this.deSelectionAnimation = deSelectionAnimation;
        this.icon = icon;
        this.paletteVisible = false;
    }
    
    protected abstract Set<String> getDefinitions();

    protected abstract void onItemSelected( final String definitionId,
                                            final ShapeFactory<?, ?, ?> factory,
                                            final double x,
                                            final double y );
    
    @Override
    public I getIcon( final double width, final double height ) {
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

        showPalette( context, element );

    }

    @Override
    public void click( final Context<AbstractCanvasHandler> context,
                       final Element element ) {

        super.click( context, element );

        if ( paletteVisible ) {

            clear();

        } else {

            showPalette( context, element );

        }

    }

    @SuppressWarnings("unchecked")
    protected void showPalette(final Context<AbstractCanvasHandler> context,
                               final Element element) {

        this.paletteVisible = true;
        this.canvasHandler = context.getCanvasHandler();
        this.sourceNode = (Node<? extends Definition<Object>, ? extends Edge>) element;
        this.graphBoundsIndexer.setRootUUID( canvasHandler.getDiagram().getSettings().getCanvasRootUUID() );

        final Set<String> allowedDefinitions = getDefinitions();
        
        log( Level.FINE, "Allowed Definitions -> " + allowedDefinitions );
        
        if ( null != allowedDefinitions && !allowedDefinitions.isEmpty() ) {

            definitionsPaletteBuilder
                    .build( allowedDefinitions, new PaletteDefinitionBuilder.Callback<DefinitionsPalette, ClientRuntimeError>() {

                        @Override
                        public void onSuccess( final DefinitionsPalette paletteDefinition ) {

                            initializeView( paletteDefinition, context );

                        }

                        @Override
                        public void onError(final ClientRuntimeError error) {

                            log( Level.SEVERE, error.toString() );

                        }

                    });

        }
        
    }

    protected abstract void attachPaletteView();

    @SuppressWarnings("unchecked")
    private void initializeView( final DefinitionsPalette paletteDefinition,
                                 Context<AbstractCanvasHandler> context ) {

        palette
                .bind( paletteDefinition )
                .onItemClick(AbstractPaletteCommand.this::_onItemClick)
                .onItemMouseDown(AbstractPaletteCommand.this::_onItemMouseDown);

        getPaletteView().setX( context.getX() );
        getPaletteView().setY( context.getY() );

        attachPaletteView();

        getPaletteView().show();

    }
    
    public void clear() {
        this.paletteVisible = false;
        getPaletteView().clear();
    }

    private boolean _onItemClick(final int index,
                                 final double mouseX,
                                 final double mouseY,
                                 final double itemX,
                                 final double itemY) {

        // TODO

        return true;

    }


    @SuppressWarnings("unchecked")
    private boolean _onItemMouseDown(final int index,
                                     final double mouseX,
                                     final double mouseY,
                                     final double itemX,
                                     final double itemY) {

        final GlyphPaletteItem items = palette.getDefinition().getItems().get( index );

        final String defId = items.getDefinitionId();
        final ShapeFactory<?, ?, ?> factory = shapeManager.getFactory( defId );

        onItemSelected( defId, factory, mouseX, mouseY );

        return true;
    }

    @Override
    public void destroy() {
      
        this.canvasHandler = null;
        this.canvasHighlight = null;
        this.sourceNode = null;

        this.palette.destroy();
        this.nodeDragProxyFactory.destroy();
        this.nodeBuilderControl.disable();
        this.graphBoundsIndexer.destroy();
        this.canvasHighlight.destroy();
        
        this.clientFactoryServices = null;
        this.commonLookups = null;
        this.shapeManager = null;
        this.definitionsPaletteBuilder= null;
        this.palette = null;
        this.nodeDragProxyFactory = null;
        this.nodeBuilderControl = null;
        this.graphBoundsIndexer = null;
        this.selectionAnimation = null;
        this.deSelectionAnimation = null;
        
    }

    protected abstract PaletteView getPaletteView();

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
