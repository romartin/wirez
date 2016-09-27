package org.wirez.core.client.canvas.controls.palette;

import com.google.gwt.core.client.GWT;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.wirez.core.client.canvas.controls.builder.BuilderControl;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequestImpl;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.components.palette.factory.PaletteFactory;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.wirez.core.client.components.palette.view.PaletteGrid;
import org.wirez.core.client.components.palette.view.PaletteView;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.event.MouseDoubleClickEvent;
import org.wirez.core.client.shape.view.event.MouseDoubleClickHandler;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

public abstract class AbstractCanvasPaletteControl
        extends AbstractCanvasHandlerControl
        implements CanvasPaletteControl<AbstractCanvasHandler> {

    protected PaletteFactory<DefinitionSetPalette, ? extends Palette<DefinitionSetPalette>> paletteFactory;
    protected ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl;
    protected ClientFactoryServices factoryServices;
    protected ShapeManager shapeManager;

    protected ViewHandler<?> layerClickHandler;
    protected Palette<DefinitionSetPalette> palette;
    protected boolean paletteVisible;

    public AbstractCanvasPaletteControl( final PaletteFactory<DefinitionSetPalette, ? extends Palette<DefinitionSetPalette>> paletteFactory,
                                         final ElementBuilderControl<AbstractCanvasHandler> elementBuilderControl,
                                         final ClientFactoryServices factoryServices,
                                         final ShapeManager shapeManager) {
        this.paletteFactory = paletteFactory;
        this.elementBuilderControl = elementBuilderControl;
        this.factoryServices = factoryServices;
        this.shapeManager = shapeManager;
        this.palette = null;
        this.paletteVisible = false;
    }

    protected abstract void attachPaletteView();

    protected abstract PaletteView getPaletteView();

    protected abstract PaletteGrid getGrid();

    @Override
    @SuppressWarnings( "unchecked" )
    public void enable( final AbstractCanvasHandler canvasHandler ) {
        super.enable( canvasHandler );

        elementBuilderControl.enable( canvasHandler );

        final Layer layer = canvasHandler.getCanvas().getLayer();

        final MouseDoubleClickHandler doubleClickHandler = new MouseDoubleClickHandler() {

            @Override
            public void handle( final MouseDoubleClickEvent event ) {

                if ( isPaletteVisible() ) {

                    hide();

                } else {

                    AbstractCanvasPaletteControl.this.show( event.getX(), event.getY() );

                }

            }

        };

        layer.addHandler( ViewEventType.MOUSE_DBL_CLICK, doubleClickHandler );

        this.layerClickHandler = doubleClickHandler;

    }

    @Override
    protected void doDisable() {

        if ( null != this.elementBuilderControl ) {

            this.elementBuilderControl.disable();
            this.elementBuilderControl = null;

        }

        hide();

        this.palette = null;

        if ( null != layerClickHandler ) {

            canvasHandler.getCanvas().getLayer().removeHandler( layerClickHandler );
            this.layerClickHandler = null;
        }

    }

    private void initializePalette() {

        if ( null == palette ) {

            final String ssid = canvasHandler.getDiagram().getSettings().getShapeSetId();

            this.palette = paletteFactory.newPalette( ssid, getGrid() );

            this.palette.onItemClick(AbstractCanvasPaletteControl.this::_onItemClick);

            this.palette.onClose( () -> {

                hide();

                return true;

            } );

            attachPaletteView();

        }

    }

    @Override
    @SuppressWarnings( "unchecked" )
    public CanvasPaletteControl<AbstractCanvasHandler> show( final double x,
                                                             final double y ) {

        this.paletteVisible = true;

        initializePalette();

        getPaletteView().setX( x );
        getPaletteView().setY( y );

        getPaletteView().show();

        return this;
    }

    @Override
    public CanvasPaletteControl<AbstractCanvasHandler> hide() {

        this.paletteVisible = false;

        if ( null != getPaletteView() ) {

            getPaletteView().hide();

        }

        return this;
    }

    private boolean _onItemClick(final String id,
                                 final double mouseX,
                                 final double mouseY,
                                 final double itemX,
                                 final double itemY) {

        factoryServices.newDefinition( id, new ServiceCallback<java.lang.Object>() {

            @Override
            public void onSuccess( final java.lang.Object def ) {

                final ShapeFactory factory = shapeManager.getFactory( id );

                final ElementBuildRequest<AbstractCanvasHandler> request =
                        new ElementBuildRequestImpl( itemX, itemY, def, factory );

                elementBuilderControl.build( request, new BuilderControl.BuildCallback() {

                    @Override
                    public void onSuccess( final String uuid ) {

                        onItemBuilt( uuid );

                    }

                    @Override
                    public void onError( final ClientRuntimeError error ) {

                        AbstractCanvasPaletteControl.this.onError( error );

                    }

                } );



            }

            @Override
            public void onError( final ClientRuntimeError error ) {
                AbstractCanvasPaletteControl.this.onError( error );
            }

        } );

        return true;

    }

    protected void onItemBuilt( final String uuid ) {

        hide();

    }

    private void onError( final ClientRuntimeError error ) {
        GWT.log( "ERROR: " + error.toString() );
    }

    private boolean isPaletteVisible() {
        return this.paletteVisible;
    }

}
