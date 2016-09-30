package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.palette;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoDefinitionSetPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.factory.LienzoDefinitionSetPaletteFactory;
import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoPalette;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.ElementBuilderControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.impl.Element;
import org.kie.workbench.common.stunner.core.client.canvas.controls.palette.AbstractCanvasPaletteControl;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGrid;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGridImpl;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteView;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoCanvasPaletteControl extends AbstractCanvasPaletteControl {

    private static final int ICON_SIZE = 25;
    private static final int PADDING = 5;

    Event<CanvasElementSelectedEvent> elementSelectedEvent;

    @Inject
    public LienzoCanvasPaletteControl( final LienzoDefinitionSetPaletteFactory paletteFactory,
                                       final @Element  ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl,
                                       final ClientFactoryServices factoryServices,
                                       final ShapeManager shapeManager,
                                       final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        super( paletteFactory, elementBuilderControl, factoryServices, shapeManager );
        this.elementSelectedEvent = elementSelectedEvent;
    }

    private LienzoDefinitionSetPalette getLienzoPalette() {
        return null != this.palette ? ( LienzoDefinitionSetPalette ) this.palette : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void attachPaletteView() {

        getLienzoPalette().setExpandable( false );
        getLienzoPalette().setLayout( LienzoPalette.Layout.VERTICAL );

        final LienzoLayer lienzoLayer = (LienzoLayer) canvasHandler.getCanvas().getLayer();
        getPaletteView().attach( lienzoLayer.getLienzoLayer() );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    protected PaletteView getPaletteView() {
        return null != getLienzoPalette() ? getLienzoPalette().getView() : null;
    }

    @Override
    protected PaletteGrid getGrid() {
        return new PaletteGridImpl( ICON_SIZE, PADDING );
    }

    @Override
    protected void onItemBuilt( final String uuid ) {
        super.onItemBuilt( uuid );

        elementSelectedEvent.fire( new CanvasElementSelectedEvent( canvasHandler, uuid ) );
    }
}
