package org.wirez.core.client.canvas.controls.toolbox.command.morph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.AbstractPaletteCommand;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.glyph.SimpleGlyphPalette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.definition.util.MorphingUtils;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractMorphCommand<I> extends AbstractPaletteCommand<I> {

    DefinitionManager definitionManager;
    MorphingUtils morphingUtils;
    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    public AbstractMorphCommand(final DefinitionManager definitionManager,
                                final MorphingUtils morphingUtils,
                                final CanvasCommandFactory commandFactory,
                                final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                final ClientFactoryServices clientFactoryServices,
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
        
        super( clientFactoryServices, commonLookups, shapeManager, glyphTooltip, glyphMiniPalette, 
                nodeDragProxyFactory, nodeBuilderControl, graphBoundsIndexer, 
                selectionAnimation, deSelectionAnimation, icon );

        this.definitionManager = definitionManager;
        this.morphingUtils = morphingUtils;
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Set<String> getDefinitions() {

        final Object source = this.sourceNode.getContent().getDefinition();

        final String id = definitionManager.getDefinitionAdapter( source.getClass() ).getId( source );

        final Iterable<String> morphTargets = morphingUtils.getMorphTargets( source );
        
        if ( null != morphTargets && morphTargets.iterator().hasNext() ) {
            
            final Set<String> result = new LinkedHashSet<>();
            
            for ( final String morphTarget : morphTargets ) {

                if ( !id.equals( morphTarget) ) {

                    result.add( morphTarget );

                }
            }
            
            return result;
            
        }
        
        return null;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onItemSelected( final String definitionId, 
                                   final ShapeFactory<?, ?, ?> factory, 
                                   final int x, 
                                   final int y ) {

        canvasCommandManager.execute( canvasHandler, 
                commandFactory.MORPH_NODE( (Node) sourceNode, definitionId ) );
        
    }

    @Override
    public void destroy() {
        super.destroy();
        this.morphingUtils = null;
        this.commandFactory = null;
        this.canvasCommandManager = null;
    }
    
}
