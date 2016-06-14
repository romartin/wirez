package org.wirez.client.lienzo.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.glyph.SimpleGlyphPalette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MorphToolboxCommand extends org.wirez.core.client.canvas.controls.toolbox.command.morph.AbstractMorphCommand<Shape<?>> {
    
    @Inject
    public MorphToolboxCommand(final DefinitionUtils definitionUtils,
                               final CanvasCommandFactory commandFactory,
                               final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                               final ClientFactoryServices clientFactoryServices,
                               final CommonLookups commonLookups,
                               final ShapeManager shapeManager,
                               final GlyphTooltip glyphTooltip,
                               final SimpleGlyphPalette<?, ?> glyphMiniPalette,
                               final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                               final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                               final GraphBoundsIndexer graphBoundsIndexer,
                               final @Select ShapeAnimation selectionAnimation,
                               final @Deselect ShapeDeSelectionAnimation deSelectionAnimation) {
        
        super( definitionUtils, commandFactory, canvasCommandManager, clientFactoryServices,
                commonLookups,shapeManager, glyphTooltip, glyphMiniPalette, nodeDragProxyFactory,
                nodeBuilderControl, graphBoundsIndexer, selectionAnimation, deSelectionAnimation,
                SVGUtils.createSVGIcon(SVGUtils.getSightIcon()) );
        
    }

    @PostConstruct
    public void init() {
        super.initialize();
    }
    
}
