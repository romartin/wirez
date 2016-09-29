package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.toolbox.command.palette;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoPalette;
import org.kie.workbench.common.stunner.client.lienzo.util.SVGUtils;
import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.NodeBuilderControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.palette.NewPaletteNodeCommand;
import org.kie.workbench.common.stunner.core.client.components.drag.NodeDragProxy;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteView;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.kie.workbench.common.stunner.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;

/// Abstract for not being discovered by the CDI environment, for now...
public abstract class LienzoNewPaletteNodeToolboxCommand extends NewPaletteNodeCommand<Shape<?>> {
    
    public LienzoNewPaletteNodeToolboxCommand(final ClientFactoryServices clientFactoryServices,
                                              final CommonLookups commonLookups,
                                              final ShapeManager shapeManager,
                                              final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                              final LienzoGlyphsHoverPalette palette,
                                              final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                              final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                              final GraphBoundsIndexer graphBoundsIndexer ) {
        super(clientFactoryServices, commonLookups, shapeManager,
                definitionsPaletteBuilder, palette, nodeDragProxyFactory,
                nodeBuilderControl, graphBoundsIndexer, SVGUtils.createSVGIcon(SVGUtils.getAddIcon()) );
    }

    @PostConstruct
    public void init() {
        getLienzoPalette()
                .expand()
                .setIconSize( 15 )
                .setPadding( 5 )
                .setLayout( LienzoPalette.Layout.VERTICAL );

        ( (AbstractLienzoGlyphItemsPalette ) getLienzoPalette()).getDefinitionGlyphTooltip().setPrefix( "Create a new " );

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void attachPaletteView() {
        final LienzoLayer lienzoLayer = (LienzoLayer) canvasHandler.getCanvas().getLayer();
        getPaletteView().attach( lienzoLayer.getLienzoLayer() );
    }

    @Override
    protected PaletteView getPaletteView() {
        return getLienzoPalette().getView();
    }


    protected LienzoPalette getLienzoPalette() {
        return (LienzoPalette) palette;
    }
}
