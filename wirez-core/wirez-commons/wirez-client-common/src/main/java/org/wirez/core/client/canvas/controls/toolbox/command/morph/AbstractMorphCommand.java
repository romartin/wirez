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
import org.wirez.core.definition.adapter.MorphAdapter;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMorphCommand<I> extends AbstractPaletteCommand<I> {

    DefinitionUtils definitionUtils;
    CanvasCommandFactory commandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;

    protected final Map<String, MorphDefinition> morphDefinitions = new HashMap<>();

    public AbstractMorphCommand(final DefinitionUtils definitionUtils,
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

        this.definitionUtils = definitionUtils;
        this.commandFactory = commandFactory;
        this.canvasCommandManager = canvasCommandManager;
        
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Set<String> getDefinitions() {

        this.morphDefinitions.clear();

        final Object source = this.sourceNode.getContent().getDefinition();

        final String id = getDefinitionManager().getDefinitionAdapter( source.getClass() ).getId( source );

        final MorphAdapter<Object> morphAdapter =  getDefinitionManager().getMorphAdapter( source.getClass() );

        final Iterable<MorphDefinition> morphDefinitions = morphAdapter.getMorphDefinitions( source );

        if ( null != morphDefinitions && morphDefinitions.iterator().hasNext() ) {

            for ( final MorphDefinition morphDefinition : morphDefinitions ) {

                final Iterable<String> morphTargets = morphAdapter.getTargets( source, morphDefinition );

                if ( null != morphTargets && morphTargets.iterator().hasNext() ) {

                    for ( final String morphTarget : morphTargets ) {

                        if ( !id.equals( morphTarget) ) {

                            this.morphDefinitions.put( morphTarget, morphDefinition );


                        }
                    }

                }

            }

        }


        if ( !this.morphDefinitions.isEmpty() ) {

            return new HashSet<>( this.morphDefinitions.keySet() );

        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onItemSelected( final String definitionId, 
                                   final ShapeFactory<?, ?, ?> factory, 
                                   final int x, 
                                   final int y ) {

        final MorphDefinition morphDefinition = morphDefinitions.get( definitionId );

        canvasCommandManager.execute( canvasHandler, 
                commandFactory.MORPH_NODE( (Node) sourceNode, morphDefinition, definitionId, factory ) );

        this.morphDefinitions.clear();
        
    }

    protected DefinitionManager getDefinitionManager() {
        return definitionUtils.getDefinitionManager();
    }

    @Override
    public void destroy() {
        super.destroy();
        this.definitionUtils = null;
        this.commandFactory = null;
        this.canvasCommandManager = null;
    }
    
}
