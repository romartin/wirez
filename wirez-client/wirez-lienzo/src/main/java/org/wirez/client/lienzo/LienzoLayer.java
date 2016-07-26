package org.wirez.client.lienzo;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.shared.core.types.DataURLType;
import com.google.gwt.core.client.GWT;
import org.uberfire.mvp.Command;
import org.wirez.client.lienzo.canvas.util.LienzoImageDataUtils;
import org.wirez.client.lienzo.shape.view.ViewEventHandlerManager;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

import javax.enterprise.context.Dependent;

@Dependent
@Lienzo
public class LienzoLayer implements Layer<LienzoLayer, ShapeView<?>, Shape<?>> {

    private static final ViewEventType[] SUPPORTED_EVENT_TYPES = new ViewEventType[] {
            ViewEventType.MOUSE_CLICK, ViewEventType.MOUSE_DBL_CLICK, ViewEventType.MOUSE_MOVE
    };
    
    protected ViewEventHandlerManager eventHandlerManager;
    protected com.ait.lienzo.client.core.shape.Layer layer;
    
    public LienzoLayer() {
    }

    @Override
    public LienzoLayer initialize(final Object view) {
        this.layer = (com.ait.lienzo.client.core.shape.Layer) view;
        this.eventHandlerManager = new ViewEventHandlerManager( layer, SUPPORTED_EVENT_TYPES );
        return this;
    }

    @Override
    public LienzoLayer addShape(final ShapeView<?> shape) {
        GWT.log("Adding shape " + shape.toString());
        layer.add((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer removeShape(final ShapeView<?> shape) {
        GWT.log("Removing shape " + shape.toString());
        layer.remove((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer draw() {
        layer.batch();
        
        return this;
    }

    @Override
    public void clear() {
        layer.clear();
    }

    @Override
    public String toDataURL() {
        return layer.toDataURL(DataURLType.PNG);
    }

    @Override
    public String toDataURL( final int x,
                             final int y,
                             final int width,
                             final int height ) {

        return LienzoImageDataUtils.toImageData( getLienzoLayer(), x, y, width ,height );

    }

    @Override
    public void onAfterDraw(final Command callback) {
        layer.setOnLayerAfterDraw(layer1 -> callback.execute());
    }

    @Override
    public void destroy() {
        
        // Clear registered event handers.
        if ( null != eventHandlerManager ) {

            eventHandlerManager.destroy();
            eventHandlerManager = null;

        }
        
        // Remove the layer stuff.
        if ( null != layer ) {
            layer.removeAll();
            layer.removeFromParent();
            layer = null;
        }
        
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return eventHandlerManager.supports( type );
    }

    @Override
    public LienzoLayer addHandler(final ViewEventType type,
                                  final ViewHandler<? extends ViewEvent> eventHandler) {

        eventHandlerManager.addHandler( type, eventHandler );
        return this;
    }

    @Override
    public LienzoLayer removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {

        eventHandlerManager.removeHandler( eventHandler );
        return this;
    }

    @Override
    public LienzoLayer enableHandlers() {
        eventHandlerManager.enable();
        return this;
    }

    @Override
    public LienzoLayer disableHandlers() {
        eventHandlerManager.disable();
        return this;
    }

    @Override
    public Shape<?> getAttachableShape() {
        return null;
    }

    public com.ait.lienzo.client.core.shape.Layer getLienzoLayer() {
        return this.layer;
    }

}
