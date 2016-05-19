package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragHandler;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.lienzo.shape.view.AbstractShapeView;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.HasFillGradient;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;
import org.wirez.shapes.client.util.BasicShapesUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicShapeView<T> extends AbstractShapeView<T>
        implements 
        HasTitle<T>,
        HasEventHandlers<T, Shape<?>>,
        HasFillGradient<T>,
        HasChildren<BasicShapeView<T>> {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected Text text;
    protected WiresLayoutContainer.Layout textPosition;
    protected Type fillGradientType = null;
    protected String fillGradientStartColor = null;
    protected String fillGradientEndColor = null;
    
    public BasicShapeView(final MultiPath path,
                          final WiresManager manager) {
        super(path, manager);
        this.textPosition = WiresLayoutContainer.Layout.CENTER;
    }

    @Override
    public void addChild(final BasicShapeView<T> child, 
                         final Layout layout) {
        super.addChild( (IPrimitive<?>) child.getContainer(), BasicShapesUtils.getWiresLayout( layout ) );
    }

    @Override
    public void removeChild( final BasicShapeView<T> child ) {
        super.removeChild( (IPrimitive<?>) child.getContainer() );
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return ViewEventType.MOUSE_CLICK.equals( type ) || ViewEventType.DRAG.equals( type );
    }
    
    protected HandlerRegistration doAddHandler(final ViewEventType type,
                                                final ViewHandler<? extends ViewEvent> eventHandler) {

        if ( supports(type) ) {

            if ( ViewEventType.MOUSE_CLICK.equals(type) ) {
                return registerClickHandler(getShape(), (ViewHandler<ViewEvent>) eventHandler);
            }

            if ( ViewEventType.DRAG.equals(type) ) {
                return registerDragHandler(getShape(), (org.wirez.core.client.shape.view.event.DragHandler) eventHandler);
            }

            
        }

        return null;
        
    }

    @Override
    public Shape<?> getAttachableShape() {
        return getShape();
    }

    @Override
    public T setTitle(final String title) {
        
        if ( null == text) {
            text = buildText(title);
            this.addChild(text, getTextPosition());
        } else {
            text.setText(title);
        }
        text.moveToTop();

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

        // Center the text on the parent using the bb calculation.
        if ( null != text ) {
            final BoundingBox bb = text.getBoundingBox();
            final double bbw = bb.getWidth();
            final double bbh = bb.getHeight();
            this.moveChild(text, - ( bbw / 2 ), - ( bbh / 2 ) );
            text.moveToTop();
        }

        return (T) this;
    }

    protected Text buildText(String _text) {
        Text text = new Text(_text).setFontSize(14).setFillColor(ColorName.BLACK).setStrokeWidth(1);
        return text.moveToTop().setDraggable(false);
    }
    
    private WiresLayoutContainer.Layout getTextPosition() {
        return textPosition;
    }
    
    protected HandlerRegistration registerClickHandler( final Node node,
                                                        final ViewHandler<ViewEvent> eventHandler ) {
        return node.addNodeMouseClickHandler(nodeMouseClickEvent -> {
            final MouseClickEvent event = new MouseClickEvent(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
            eventHandler.handle( event );
        });
    }

    protected HandlerRegistration registerDragHandler( final Node node,
                                                       final ViewHandler<org.wirez.core.client.shape.view.event.DragEvent> eventHandler ) {
    
        return setDraggable(true).addWiresHandler(AbstractWiresEvent.DRAG, new DragHandler() {
            @Override
            public void onDragStart(final DragEvent dragEvent) {
                getDragHandler().start(buildEvent(dragEvent.getX(), dragEvent.getY()));
            }

            @Override
            public void onDragMove(final DragEvent dragEvent) {
                getDragHandler().handle(buildEvent(dragEvent.getX(), dragEvent.getY()));
            }

            @Override
            public void onDragEnd(final DragEvent dragEvent) {
                getDragHandler().end(buildEvent(dragEvent.getX(), dragEvent.getY()));
            }
            
            private org.wirez.core.client.shape.view.event.DragHandler getDragHandler() {
                return (org.wirez.core.client.shape.view.event.DragHandler) eventHandler;
            }
            
            private org.wirez.core.client.shape.view.event.DragEvent buildEvent(final double x, final double y) {
                return new org.wirez.core.client.shape.view.event.DragEvent(x, y);
            }
        });
    }
    
    @Override
    public T setFillGradient(final Type type, 
                             final String startColor, 
                             final String endColor) {
        
        this.fillGradientType  = type;
        this.fillGradientStartColor = startColor;
        this.fillGradientEndColor = endColor;
        
        if ( null != getShape() ) {
            final BoundingBox bb = getShape().getBoundingBox();
            final double width = bb.getWidth();
            final double height = bb.getHeight();
            updateFillGradient( width, height );
        }
        
        return (T) this;
    }
    
    protected T updateFillGradient( final double width,
                                    final double height ) {
        
        if ( this.fillGradientType != null 
                && this.fillGradientStartColor != null 
                && this.fillGradientEndColor != null ) {

            final LinearGradient gradient = LienzoShapeUtils.getLinearGradient( fillGradientStartColor,
                    fillGradientEndColor, width, height);
            
            getShape().setFillGradient(gradient);
            
        }
        
        return (T) this;   
    }

    @Override
    protected void doDestroy() {
        
        // Remove all registered handlers.
        registrationManager.removeHandler();
        registrationMap.clear();
        
    }

    @Override
    public void destroy() {
        super.destroy();

        // Nullify.
        this.text = null;
        this.textPosition = null;
        this.fillGradientEndColor = null;
        this.fillGradientStartColor = null;
        this.fillGradientType = null;
        
    }

    @Override
    public T addHandler(final ViewEventType type,
                        final ViewHandler<? extends ViewEvent> eventHandler) {

        final HandlerRegistration registration = doAddHandler(type, eventHandler);
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
    
}
