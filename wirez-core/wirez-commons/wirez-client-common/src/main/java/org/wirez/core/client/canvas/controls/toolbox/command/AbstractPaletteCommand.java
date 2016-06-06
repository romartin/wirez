package org.wirez.core.client.canvas.controls.toolbox.command;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.util.CanvasHighlight;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.PaletteGridImpl;
import org.wirez.core.client.components.palette.glyph.SimpleGlyphPalette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPaletteCommand<I> extends AbstractToolboxCommand<I> {

    private static Logger LOGGER = Logger.getLogger(AbstractPaletteCommand.class.getName());
    private static final int PADDING = 10;
    private static final int ICON_SIZE = 25;
    
    private final I icon;

    protected ClientFactoryServices clientFactoryServices;
    protected CommonLookups commonLookups;
    protected ShapeManager shapeManager;
    protected GlyphTooltip glyphTooltip;
    protected SimpleGlyphPalette<?, ?> glyphMiniPalette;
    protected NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory;
    protected NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl;
    protected GraphBoundsIndexer graphBoundsIndexer;
    protected ShapeAnimation selectionAnimation;
    protected ShapeDeSelectionAnimation deSelectionAnimation;
    
    protected String[] definitionIds;
    protected ShapeFactory<?, ?, ?>[] factories;
    protected AbstractCanvasHandler canvasHandler;
    protected CanvasHighlight canvasHighlight;
    protected Node<? extends Definition<Object>, ? extends Edge> sourceNode;

    public AbstractPaletteCommand(final ClientFactoryServices clientFactoryServices,
                                  final CommonLookups commonLookups,
                                  final ShapeManager shapeManager,
                                  final GlyphTooltip glyphTooltip,
                                  final SimpleGlyphPalette<?, ?> glyphMiniPalette,
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
    
    protected abstract Set<String> getDefinitions();

    protected abstract void onItemSelected( final String definitionId,
                                            final ShapeFactory<?, ?, ?> factory,
                                            final int x,
                                            final int y );
    
    protected void initialize() {
        glyphMiniPalette.setItemOutCallback(AbstractPaletteCommand.this::onItemOut);
        glyphMiniPalette.setItemHoverCallback(AbstractPaletteCommand.this::onItemHover);
        glyphMiniPalette.setItemClickCallback(AbstractPaletteCommand.this::onItemClick);
        glyphMiniPalette.setItemMouseDownCallback(AbstractPaletteCommand.this::onItemMouseDown);
        glyphMiniPalette.setGrid(new PaletteGridImpl( ICON_SIZE, PADDING ) );
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

        this.canvasHandler = context.getCanvasHandler();
        final AbstractCanvas canvas = canvasHandler.getCanvas();
        final org.wirez.core.client.shape.Shape shape = canvas.getShape( element.getUUID() );
        this.sourceNode = (Node<? extends Definition<Object>, ? extends Edge>) element;
        
        final Set<String> allowedDefinitions = getDefinitions();
        
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
                final ShapeGlyph glyph = factory.glyph( allowedDefId, ICON_SIZE, ICON_SIZE );

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

    @SuppressWarnings("unchecked")
    void onItemHover(final int index,
                     final double x,
                     final double y) {

        final ShapeFactory<?, ?, ?> factory = factories[ index ];
        final String id = definitionIds[index];
        final ShapeGlyph glyph = factory.glyph ( id, 100, 100 );
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
        
    }

    
    @SuppressWarnings("unchecked")
    void onItemMouseDown(final int index,
                        final int x,
                        final int y) {

        final String defId = definitionIds[ index ];
        final ShapeFactory<?, ?, ?> factory = factories[ index ];

        onItemSelected( defId, factory, x, y );

    }

    @Override
    public void destroy() {
      
        this.definitionIds = null;
        this.factories = null;
        this.canvasHandler = null;
        this.canvasHighlight = null;
        this.sourceNode = null;

        this.glyphTooltip.remove();
        this.glyphMiniPalette.destroy();
        this.nodeDragProxyFactory.destroy();
        this.nodeBuilderControl.disable();
        this.graphBoundsIndexer.destroy();
        this.canvasHighlight.destroy();
        
        this.clientFactoryServices = null;
        this.commonLookups = null;
        this.shapeManager = null;
        this.glyphTooltip = null;
        this.glyphMiniPalette = null;
        this.nodeDragProxyFactory = null;
        this.nodeBuilderControl = null;
        this.graphBoundsIndexer = null;
        this.selectionAnimation = null;
        this.deSelectionAnimation = null;
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
