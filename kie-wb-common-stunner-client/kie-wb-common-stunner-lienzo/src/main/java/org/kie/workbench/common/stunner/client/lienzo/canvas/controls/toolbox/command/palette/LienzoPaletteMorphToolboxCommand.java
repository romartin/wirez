package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.toolbox.command.palette;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.kie.workbench.common.stunner.client.lienzo.util.SVGUtils;
import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoPalette;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.NodeBuilderControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.palette.AbstractPaletteMorphCommand;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.Session;
import org.kie.workbench.common.stunner.core.client.components.drag.NodeDragProxy;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteView;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.kie.workbench.common.stunner.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoPaletteMorphToolboxCommand extends AbstractPaletteMorphCommand<Shape<?>> {
    
    @Inject
    public LienzoPaletteMorphToolboxCommand(final DefinitionUtils definitionUtils,
                                            final CanvasCommandFactory commandFactory,
                                            final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                            final ClientFactoryServices clientFactoryServices,
                                            final CommonLookups commonLookups,
                                            final ShapeManager shapeManager,
                                            final DefinitionsPaletteBuilder definitionsPaletteBuilder,
                                            final LienzoGlyphsHoverPalette palette,
                                            final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                            final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                            final GraphBoundsIndexer graphBoundsIndexer,
                                            final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        
        super( definitionUtils, commandFactory, canvasCommandManager, clientFactoryServices,
                commonLookups,shapeManager, definitionsPaletteBuilder, palette, nodeDragProxyFactory,
                nodeBuilderControl, graphBoundsIndexer,  SVGUtils.createSVGIcon( SVGUtils.getGearIcon() ),
                elementSelectedEvent );
        
    }

    @PostConstruct
    public void init() {
        getLienzoPalette()
                .collapse()
                .setExpandable( false )
                .setIconSize( 18 )
                .setPadding( 10 )
                .setLayout(LienzoPalette.Layout.HORIZONTAL );

        ( (AbstractLienzoGlyphItemsPalette ) getLienzoPalette()).getDefinitionGlyphTooltip().setPrefix( "Convert to " );

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
