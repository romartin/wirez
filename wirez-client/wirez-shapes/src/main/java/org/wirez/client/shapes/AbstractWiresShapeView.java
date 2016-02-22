package org.wirez.client.shapes;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Node;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Text;
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
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.util.ShapeUtils;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.HasFillGradient;
import org.wirez.core.client.view.HasTitle;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.MouseClickEvent;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractWiresShapeView<T> extends WiresShape 
        implements 
        ShapeView<T>,
        HasTitle<T>,
        HasEventHandlers<T>,
        HasFillGradient<T>,
        HasDecorators {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected final Collection<Shape> decorators= new LinkedList<>();
    protected Text text;
    protected String uuid;
    
    public AbstractWiresShapeView(final MultiPath path, 
                                  final WiresManager manager) {
        super(path, new WiresLayoutContainer(), manager);
        this.decorators.add(getDecorator());
        
    }

    protected abstract HandlerRegistration doAddHandler(final ViewEventType type,
                                                        final ViewHandler<? extends ViewEvent> eventHandler);

    protected abstract Shape getDecorator();

    @Override
    public Collection<Shape> getDecorators() {
        return decorators;
    }

    @Override
    public T setUUID(final String uuid) {
        this.uuid = uuid;
        return (T) this;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public T setTitle(final String title) {
        
        if ( null != text ) {
            text.removeFromParent();
        }

        if ( null != title ) {
            text = buildText("");
            this.addChild(text, getTextPosition());
            text.moveToTop();
        }
        
        return (T) this;
    }

    @Override
    public T setTitleStrokeColor(final String color) {
        text.setStrokeColor(color);
        return (T) this;
    }

    @Override
    public T setFontFamily(final String fontFamily) {
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

    protected Text buildText(String _text) {
        Text text = new Text(_text).setFontSize(14).setFillColor(ColorName.BLACK).setStrokeWidth(1);
        return text.moveToTop();
    }
    
    protected WiresLayoutContainer.Layout getTextPosition() {
        return WiresLayoutContainer.Layout.CENTER;
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

    protected HandlerRegistration registerDragHandler(final Node node,
                                                       final ViewHandler<org.wirez.core.client.view.event.DragEvent> eventHandler) {
    
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
            
            private org.wirez.core.client.view.event.DragHandler getDragHandler() {
                return (org.wirez.core.client.view.event.DragHandler) eventHandler;
            }
            
            private org.wirez.core.client.view.event.DragEvent buildEvent(final double x, final double y) {
                return new org.wirez.core.client.view.event.DragEvent(x, y);
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
        final BoundingBox bb = getPath().getBoundingBox();
        final double width = bb.getWidth();
        final double height = bb.getHeight();
        final LinearGradient gradient = ShapeUtils.getLinearGradient(startColor, endColor, width, height);
        getPath().setFillGradient(gradient);
        return (T) this;
    }

    @Override
    public String getFillColor() {
        return getPath().getFillColor();
    }

    @Override
    public T setFillColor(final String color) {
        getPath().setFillColor(color);
        return (T) this;
    }

    @Override
    public double getFillAlpha() {
        return getPath().getFillAlpha();
    }

    @Override
    public T setFillAlpha(final double alpha) {
        getPath().setFillAlpha(alpha);
        return (T) this;
    }

    @Override
    public String getStrokeColor() {
        return getPath().getStrokeColor();
    }

    @Override
    public T setStrokeColor(final String color) {
        getPath().setStrokeColor(color);
        return (T) this;
    }

    @Override
    public double getStrokeAlpha() {
        return getPath().getStrokeAlpha();
    }

    @Override
    public T setStrokeAlpha(final double alpha) {
        getPath().setStrokeAlpha(alpha);
        return (T) this;
    }

    @Override
    public double getStrokeWidth() {
        return getPath().getStrokeWidth();
    }

    @Override
    public T setStrokeWidth(final double width) {
        getPath().setStrokeWidth(width);
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
