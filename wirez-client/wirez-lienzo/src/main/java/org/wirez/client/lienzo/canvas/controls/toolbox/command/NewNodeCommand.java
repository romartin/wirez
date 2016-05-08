package org.wirez.client.lienzo.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.api.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.api.lookup.util.CommonLookups;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.view.ShapeGlyph;

public abstract class NewNodeCommand extends org.wirez.core.client.canvas.controls.toolbox.command.node.NewNodeCommand<Shape<?>> {
    
    public NewNodeCommand( final ClientFactoryServices clientFactoryServices, 
                           final CommonLookups commonLookups, 
                           final ShapeManager shapeManager, 
                           final GlyphTooltip glyphTooltip, 
                           final Palette<?, ShapeGlyph<?>> glyphMiniPalette, 
                           final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory, 
                           final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl, 
                           final GraphBoundsIndexer graphBoundsIndexer,
                           final ShapeAnimation selectionAnimation,
                           final ShapeDeSelectionAnimation deSelectionAnimation) {
        super(clientFactoryServices, commonLookups, shapeManager, glyphTooltip, 
                glyphMiniPalette, nodeDragProxyFactory, 
                nodeBuilderControl, graphBoundsIndexer,
                selectionAnimation, deSelectionAnimation,
                SVGUtils.createSVGIcon(SVGUtils.getAddIcon()) );
    }
    
}
