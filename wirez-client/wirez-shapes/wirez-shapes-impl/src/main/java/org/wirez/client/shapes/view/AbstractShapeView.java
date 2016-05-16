package org.wirez.client.shapes.view;

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragEvent;
import com.ait.lienzo.client.core.shape.wires.event.DragHandler;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.client.shapes.HasChildren;
import org.wirez.client.shapes.util.BasicShapesUtils;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.HasFillGradient;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.event.MouseClickEvent;
import org.wirez.core.client.shape.view.event.ViewEvent;
import org.wirez.core.client.shape.view.event.ViewEventType;
import org.wirez.core.client.shape.view.event.ViewHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShapeView<T> extends WiresShape 
        implements 
        ShapeView<T>,
        HasTitle<T>,
        HasEventHandlers<T, Shape<?>>,
        HasFillGradient<T>,
        HasChildren<AbstractShapeView<T>> {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected Text text;
    protected WiresLayoutContainer.Layout textPosition;
    protected String uuid;
    protected Type fillGradientType = null;
    protected String fillGradientStartColor = null;
    protected String fillGradientEndColor = null;
    private int zindex;
    
    public AbstractShapeView(final MultiPath path,
                             final WiresManager manager) {
        super(path, new WiresLayoutContainer(), manager);
        this.textPosition = WiresLayoutContainer.Layout.CENTER;
    }
    
    protected Shape<?> getShape() {
        return getPath();
    }
    
    @Override
    public T setUUID(final String uuid) {
        this.uuid = uuid;
        this.getGroup().setUserData( UUID_PREFFIX + uuid );
        return (T) this;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void addChild(final AbstractShapeView<T> child, 
                         final Layout layout) {
        super.addChild( (IPrimitive<?>) child.getContainer(), BasicShapesUtils.getWiresLayout( layout ) );
    }

    @Override
    public void removeChild( final AbstractShapeView<T> child ) {
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
    public T setZIndex(final int zindez) {
        this.zindex = zindez;
        return (T) this;
    }

    @Override
    public int getZIndex() {
        return zindex;
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
        final BoundingBox bb = text.getBoundingBox();
        final double bbw = bb.getWidth();
        final double bbh = bb.getHeight();
        this.moveChild(text, - ( bbw / 2 ), - ( bbh / 2 ) );
        text.moveToTop();

        return (T) this;
    }

    @Override
    public T setDragEnabled(boolean isDraggable) {
        this.setDraggable(isDraggable);
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
    public double getShapeX() {
        return getContainer().getAttributes().getX();
    }

    @Override
    public double getShapeY() {
        return getContainer().getAttributes().getY();
    }

    @Override
    public T setShapeX(final double x) {
        getContainer().getAttributes().setX(x);
        return (T) this;
    }

    @Override
    public T setShapeY(final double y) {
        getContainer().getAttributes().setY(y);
        return (T) this;
    }

    @Override
    public T setFillGradient(final Type type, 
                             final String startColor, 
                             final String endColor) {
        
        this.fillGradientType  = type;
        this.fillGradientStartColor = startColor;
        this.fillGradientEndColor = endColor;
        
        final BoundingBox bb = getShape().getBoundingBox();
        final double width = bb.getWidth();
        final double height = bb.getHeight();
        
        updateFillGradient( width, height );
        
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
    public String getFillColor() {
        return getShape().getFillColor();
    }

    @Override
    public T setFillColor(final String color) {
        getShape().setFillColor(color);
        this.fillGradientType  = null;
        this.fillGradientStartColor = null;
        this.fillGradientEndColor = null;
        return (T) this;
    }

    @Override
    public double getFillAlpha() {
        return getShape().getFillAlpha();
    }

    @Override
    public T setFillAlpha(final double alpha) {
        getShape().setFillAlpha(alpha);
        return (T) this;
    }

    @Override
    public String getStrokeColor() {
        return getShape().getStrokeColor();
    }

    @Override
    public T setStrokeColor(final String color) {
        getShape().setStrokeColor(color);
        return (T) this;
    }

    @Override
    public double getStrokeAlpha() {
        return getShape().getStrokeAlpha();
    }

    @Override
    public T setStrokeAlpha(final double alpha) {
        getShape().setStrokeAlpha(alpha);
        return (T) this;
    }

    @Override
    public double getStrokeWidth() {
        return getShape().getStrokeWidth();
    }

    @Override
    public T setStrokeWidth(final double width) {
        getShape().setStrokeWidth(width);
        return (T) this;
    }

    public Text getText() {
        return text;
    }

    @Override
    public T moveToTop() {
        getContainer().moveToTop();
        return (T) this;
    }

    @Override
    public T moveToBottom() {
        getContainer().moveToBottom();
        return (T) this;
    }

    @Override
    public T moveUp() {
        getContainer().moveUp();
        return (T) this;
    }

    @Override
    public T moveDown() {
        getContainer().moveDown();
        return (T) this;
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
