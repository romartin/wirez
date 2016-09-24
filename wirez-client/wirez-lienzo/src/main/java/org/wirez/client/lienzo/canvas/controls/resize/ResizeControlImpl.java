package org.wirez.client.lienzo.canvas.controls.resize;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeHandler;
import com.google.gwt.core.client.GWT;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.wirez.core.client.canvas.controls.resize.ResizeControl;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

// TODO: Handler registrations, etc

@Dependent
public class ResizeControlImpl extends AbstractCanvasHandlerControl implements ResizeControl<AbstractCanvasHandler, Element> {

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;

    @Inject
    public ResizeControlImpl(final CanvasCommandFactory canvasCommandFactory,
                           final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @Override
    public void register( final Element element ) {

        final Shape<?> shape = canvasHandler.getCanvas().getShape( element.getUUID() );

        if ( null != shape && ( shape  instanceof WiresShape ) ) {

            register( element, shape );

        }

    }

    private void register( final Element element,
                           final Shape<?> shape ) {

        final WiresShape wiresShape = ( WiresShape ) shape.getShapeView();

        GWT.log( "Adding resize control for UUID=" + element.getUUID() );

        // Enable resize controls on chick + shift down.
        wiresShape
                .setResizable( true )
                .getGroup()
                .addNodeMouseClickHandler( new NodeMouseClickHandler() {
                    @Override
                    public void onNodeMouseClick( final NodeMouseClickEvent event ) {

                        GWT.log( "Click on resize control for UUID=" + element.getUUID() );

                        final IControlHandleList controlHandles = wiresShape.getControls( IControlHandle.ControlHandleStandardType.RESIZE );

                        if ( null != controlHandles ) {

                            if ( event.isShiftKeyDown() ) {
                                controlHandles.show();
                            } else {
                                controlHandles.hide();
                            }

                        }

                    }

                } );

        // Update the model when resize event obesrved.
        wiresShape.addWiresResizeHandler( new WiresResizeHandler() {
            @Override
            public void onShapeResized( final WiresResizeEvent resizeEvent ) {
                GWT.log("Shape resized TO {" + resizeEvent.getWidth() + ", " + resizeEvent.getHeight() + "]" );
            }
        } );

    }

    @Override
    public void deregister( final Element element ) {
        // TODO
    }

    @Override
    protected void doDisable() {
        // TODO
    }

}
