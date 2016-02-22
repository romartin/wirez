package org.wirez.client.views;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.HasTitle;
import org.wirez.core.client.view.IsConnector;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.MouseClickEvent;
import org.wirez.core.client.view.event.ViewEvent;
import org.wirez.core.client.view.event.ViewEventType;
import org.wirez.core.client.view.event.ViewHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWiresConnectorView<T> extends WiresConnector
    implements 
        ShapeView<T>,
        IsConnector<T>,
        HasTitle<T>,
        HasEventHandlers<T> {

    protected final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    protected final Map<ViewEventType, HandlerRegistration> registrationMap = new HashMap<>();
    protected Text text;
    private Double strokeWidth;
    private String color;

    public AbstractWiresConnectorView(AbstractDirectionalMultiPointShape<?> line, Decorator<?> head, Decorator<?> tail, WiresManager manager) {
        super(line, head, tail, manager);
    }

    public AbstractWiresConnectorView(WiresMagnet headMagnet, WiresMagnet tailMagnet, AbstractDirectionalMultiPointShape<?> line, Decorator<?> head, Decorator<?> tail, WiresManager manager) {
        super(headMagnet, tailMagnet, line, head, tail, manager);
    }

    protected abstract HandlerRegistration doAddHandler(final ViewEventType type,
                                                        final ViewHandler<ViewEvent> eventHandler);

    public T connect(final ShapeView headShapeView, 
                           final int _headMagnetsIndex, 
                           final ShapeView tailShapeView, 
                           final int _tailMagnetsIndex,
                           final boolean tailArrow,
                           final boolean headArrow)
    {
        final WiresShape headWiresShape = (WiresShape) headShapeView;
        final WiresShape tailWiresShape = (WiresShape) tailShapeView;
        final MagnetManager.Magnets headMagnets = headWiresShape.getMagnets();
        final MagnetManager.Magnets tailMagnets = tailWiresShape.getMagnets();

        int headMagnetsIndex = _headMagnetsIndex;
        int tailMagnetsIndex = _tailMagnetsIndex;
        
        if (headMagnetsIndex < 0) {
            headMagnetsIndex = 0;
        }

        if (tailMagnetsIndex < 0) {
            tailMagnetsIndex = 0;
        }


        // Obtain the magnets.
        WiresMagnet m0_1 = headMagnets.getMagnet(headMagnetsIndex);
        WiresMagnet m1_1 = tailMagnets.getMagnet(tailMagnetsIndex);

        // Update the magnets.
        this.setHeadMagnet(m0_1);
        this.setTailMagnet(m1_1);

        double x0 = m0_1.getControl().getX();
        double y0 = m0_1.getControl().getY();
        double x1 = m1_1.getControl().getX();
        double y1 = m1_1.getControl().getY();

        // TODO: Update the connector decorator in order to modify head & tail decorators (connector direction)
        OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        /*this.setDecorator(
                line, 
                headArrow ? new SimpleArrow(20, 0.75) : null,
                tailArrow ? new SimpleArrow(20, 0.75) : null);*/

        return (T) this;
    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

    public T applySelectedState() {
        return applyActiveState(true);
    }

    public T applyHighlightState() {
        return applyActiveState(true);
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
    public T addHandler(final ViewEventType type,
                        final ViewHandler<ViewEvent> eventHandler) {

        final HandlerRegistration registration = doAddHandler(type, eventHandler);
        if ( null != registration ) {
            registrationMap.put(type, registration);
            registrationManager.register(registration);
        }
        return (T) this;
    }

    @Override
    public T removeHandler(final ViewHandler<ViewEvent> eventHandler) {
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
        return (T) this;
    }

    @Override
    public void removeFromParent() {
        getDecoratableLine().removeFromParent();
    }

    @Override
    public double getShapeX() {
        return getDecoratableLine().getX();
    }

    @Override
    public double getShapeY() {
        return getDecoratableLine().getY();
    }

    @Override
    public T setShapeX(final double x) {
        getDecoratableLine().setX(x);
        return (T) this;
    }

    @Override
    public T setShapeY(final double y) {
        getDecoratableLine().setY(y);
        return (T) this;
    }

    @Override
    public String getFillColor() {
        return getDecoratableLine().getFillColor();
    }

    @Override
    public T setFillColor(final String color) {
        getDecoratableLine().setFillColor(color);
        return (T) this;
    }

    @Override
    public double getFillAlpha() {
        return getDecoratableLine().getFillAlpha();
    }

    @Override
    public T setFillAlpha(final double alpha) {
        getDecoratableLine().setFillAlpha(alpha);
        return (T) this;
    }

    @Override
    public String getStrokeColor() {
        return getDecoratableLine().getStrokeColor();
    }

    @Override
    public T setStrokeColor(final String color) {
        getDecoratableLine().setStrokeColor(color);
        return (T) this;
    }

    @Override
    public double getStrokeAlpha() {
        return getDecoratableLine().getStrokeAlpha();
    }

    @Override
    public T setStrokeAlpha(final double alpha) {
        getDecoratableLine().setStrokeAlpha(alpha);
        return (T) this;
    }

    @Override
    public double getStrokeWidth() {
        return getDecoratableLine().getStrokeWidth();
    }

    @Override
    public T setStrokeWidth(final double width) {
        getDecoratableLine().setStrokeWidth(width);
        return (T) this;
    }
    
    @Override
    public T moveToTop() {
        getDecoratableLine().moveToTop();
        return (T) this;
    }

    @Override
    public T moveToBottom() {
        getDecoratableLine().moveToBottom();
        return (T) this;
    }

    @Override
    public T moveUp() {
        getDecoratableLine().moveUp();
        return (T) this;
    }

    @Override
    public T moveDown() {
        getDecoratableLine().moveDown();
        return (T) this;
    }
    
}
