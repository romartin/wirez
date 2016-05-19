package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.lienzo.shape.view.AbstractConnectorView;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.shape.view.HasCanvasState;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicConnectorView<T> extends AbstractConnectorView<T>
    implements 
        HasTitle<T>,
        HasEventHandlers<T, Shape<?>>,
        HasCanvasState {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected Text text;
    protected WiresLayoutContainer.Layout textPosition;
    private Double strokeWidth;
    private String color;

    public BasicConnectorView( final AbstractDirectionalMultiPointShape<?> line, 
                               final Decorator<?> head, 
                               final Decorator<?> tail, 
                               final WiresManager manager) {
        super(line, head, tail, manager);
    }

    public BasicConnectorView( final WiresMagnet headMagnet, 
                               final WiresMagnet tailMagnet, 
                               final AbstractDirectionalMultiPointShape<?> line, 
                               final Decorator<?> head, Decorator<?> tail, 
                               final WiresManager manager) {
        super(headMagnet, tailMagnet, line, head, tail, manager);
    }

    protected abstract HandlerRegistration doAddHandler(final ViewEventType type,
                                                        final ViewHandler<ViewEvent> eventHandler);

    protected void init() {
        super.init();
        this.textPosition = WiresLayoutContainer.Layout.CENTER;
    }
    
    
    @Override
    public void applyState(final ShapeState shapeState) {

        if ( ShapeState.SELECTED.equals(shapeState) ) {
            applySelectedState();
        } else if ( ShapeState.HIGHLIGHT.equals(shapeState) ) {
            applyHighlightState();
        } else if ( ShapeState.DESELECTED.equals(shapeState) ) {
            applyUnSelectedState();
        } else if ( ShapeState.UNHIGHLIGHT.equals(shapeState) ) {
            applyUnHighlightState();
        }

    }
    
    public T applySelectedState() {
        return applyActiveState(true);
    }

    public T applyHighlightState() {
        return applyActiveState(false);
    }

    public T applyUnSelectedState() {
        return applyDeActiveState();
    }

    public T applyUnHighlightState() {
        return applyDeActiveState();
    }

    protected T applyActiveState(final boolean isSelectedState) {
        if ( null == this.strokeWidth) {
            this.strokeWidth = getDecoratableLine().getStrokeWidth();
        }
        
        if ( null == this.color) {
            this.color = getDecoratableLine().getStrokeColor();
        }

        getDecoratableLine().setStrokeWidth(5);
        getDecoratableLine().setStrokeColor(isSelectedState ? ColorName.RED : ColorName.BLUE);
        
        return (T) this;
    }

    protected T applyDeActiveState() {
        if ( null != this.strokeWidth ) {
            getDecoratableLine().setStrokeWidth(5);
            this.strokeWidth = null;
        }

        if ( null != this.color ) {
            getDecoratableLine().setStrokeColor(color);
            this.color = null;
        }
        
        return (T) this;
    }

    @Override
    public Shape<?> getAttachableShape() {
        return getDecoratableLine();
    }

    @Override
    public T addHandler(final ViewEventType type,
                        final ViewHandler<? extends ViewEvent> eventHandler) {

        final HandlerRegistration registration = doAddHandler(type, (ViewHandler<ViewEvent>) eventHandler);
        if ( null != registration ) {
            registrationMap.put(type, registration);
            registrationManager.register(registration);
        }
        return (T) this;
    }

    @Override
    public T removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {
        final ViewEventType type = eventHandler.getType();
        if ( registrationMap.containsKey( type ) ) {
            final HandlerRegistration registration = registrationMap.get( type );
            registrationManager.isRegistered(registration);
        }
        return (T) this;
    }

    protected HandlerRegistration registerClickHandler(final Node node,
                                                       final ViewHandler<ViewEvent> eventHandler) {
        return node.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {
                final MouseClickEvent event = new MouseClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
                eventHandler.handle( event );
            }
        });
    }

    @Override
    public T setTitle(final String title) {
        if ( null != text ) {
            text.removeFromParent();
        }

        if ( null != title ) {
            // TODO
        }

        return (T) this;
    }

    @Override
    public T setTitlePosition(final Position position) {
        if ( Position.BOTTOM.equals(position) ) {
            this.textPosition = LayoutContainer.Layout.BOTTOM;
        } else if ( Position.TOP.equals(position) ) {
            this.textPosition = LayoutContainer.Layout.TOP;
        } else if ( Position.LEFT.equals(position) ) {
            this.textPosition = LayoutContainer.Layout.LEFT;
        } else if ( Position.RIGHT.equals(position) ) {
            this.textPosition = LayoutContainer.Layout.RIGHT;
        } else if ( Position.CENTER.equals(position) ) {
            this.textPosition = LayoutContainer.Layout.CENTER;
        }
        return (T) this;
    }

    @Override
    public T setTitleStrokeColor(final String color) {
        text.setStrokeColor(color);
        return (T) this;
    }

    @Override
    public T setTitleFontFamily(final String fontFamily) {
        text.setFontFamily(fontFamily);
        return (T) this;
    }

    @Override
    public T setTitleFontSize(final double fontSize) {
        text.setFontSize(fontSize);
        return (T) this;
    }

    @Override
    public T setTitleStrokeWidth(final double strokeWidth) {
        text.setStrokeWidth(strokeWidth);
        return (T) this;
    }

    @Override
    public T moveTitleToTop() {
        text.moveToTop();
        return (T) this;
    }

    @Override
    public T refreshTitle() {
        return (T) this;
    }


    @Override
    protected void doDestroy() {

        // Clear registered event handlers.
        registrationManager.removeHandler();
        registrationMap.clear();

    }

    @Override
    public void destroy() {
        super.destroy();

        // Nullify.
         this.text = null;
        this.textPosition = null;
        this.strokeWidth = null;
        this.color = null;
    }
    
}
