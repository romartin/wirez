package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.resize;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEndHandler;
import com.google.gwt.core.client.GWT;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.resize.ResizeControl;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.Session;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

// TODO: Handler registrations, update model, resize toolbox, etc

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

        if ( null != shape && ( shape.getShapeView()  instanceof WiresShape ) ) {

            register( element, shape );

        }

    }

    private void register( final Element element,
                           final Shape<?> shape ) {

        final WiresShape wiresShape = ( WiresShape ) shape.getShapeView();

        // Enable resize controls on chick + shift down.
        wiresShape
                .setResizable( true )
                .getGroup()
                .addNodeMouseClickHandler( new NodeMouseClickHandler() {
                    @Override
                    public void onNodeMouseClick( final NodeMouseClickEvent event ) {

                        final IControlHandleList controlHandles = wiresShape.loadControls( IControlHandle.ControlHandleStandardType.RESIZE );

                        if ( null != controlHandles ) {

                            if ( event.isShiftKeyDown() && !controlHandles.isVisible() ) {
                                controlHandles.show();
                            } else {
                                controlHandles.hide();
                            }

                        }

                    }

                } );

        // Update the model when resize event obesrved.
        wiresShape.addWiresResizeEndHandler( new WiresResizeEndHandler() {
            @Override
            public void onShapeResizeEnd( WiresResizeEndEvent event ) {
                GWT.log("Shape resized TO {" + event.getWidth() + ", " + event.getHeight() + "]" );
                /* TODO
                CommandResult<CanvasViolation> result = canvasCommandManager.execute( canvasHandler, canvasCommandFactory.UPDATE_PROPERTY(element,  );
                if ( CommandUtils.isError( result) ) {
                    // TODO: DragContext#reset
                }*/
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
