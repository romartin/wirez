package org.wirez.shapes.client.view;

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.event.AbstractWiresDragEvent;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.client.lienzo.shape.view.AbstractShapeView;
import org.wirez.client.lienzo.shape.view.ViewEventHandlerManager;
import org.wirez.client.lienzo.util.LienzoShapeUtils;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.core.client.shape.view.HasEventHandlers;
import org.wirez.core.client.shape.view.HasFillGradient;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.view.event.*;
import org.wirez.shapes.client.util.BasicShapesUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicShapeView<T> extends AbstractShapeView<T>
        implements
        HasTitle<T>,
        HasEventHandlers<T, Shape<?>>,
        HasFillGradient<T>,
        HasChildren<BasicShapeView<T>> {

    private static final ViewEventType[] SUPPORTED_EVENT_TYPES = new ViewEventType[]{
            ViewEventType.MOUSE_CLICK, ViewEventType.MOUSE_DBL_CLICK, ViewEventType.DRAG,
            ViewEventType.TOUCH, ViewEventType.GESTURE
    };

    protected ViewEventHandlerManager eventHandlerManager;
    protected final List<BasicShapeView<T>> children = new ArrayList<>();
    protected Text text;
    protected Shape<?> decorator = null;

    protected WiresLayoutContainer.Layout textPosition;
    protected Type fillGradientType = null;
    protected String fillGradientStartColor = null;
    protected String fillGradientEndColor = null;

    public BasicShapeView( final MultiPath path ) {
        super( path );
        this.textPosition = WiresLayoutContainer.Layout.BOTTOM;

        initialize();

    }

    private void initialize() {

        createEventHandlerManager( getShape() );

        refreshDecorators();

        postInitialize();

        refresh();

    }

    protected void postInitialize() {

    }

    protected Shape<?> createDecorator() {
        return null;
    }

    public void refreshDecorators() {

        if ( null != decorator ) {

            decorator.removeFromParent();

        }

        decorator = createDecorator();

        if ( null != decorator ) {

            decorator
                    .setStrokeWidth(0)
                    .setFillAlpha(0)
                    .setStrokeAlpha(0);

        }

    }

    public Shape getDecorator() {
        return decorator;
    }

    protected void createEventHandlerManager( final Node<?> node ) {

        if ( null != node ) {

            this.eventHandlerManager = new ViewEventHandlerManager( node, SUPPORTED_EVENT_TYPES );

        }

    }

    @Override
    public void addChild( final BasicShapeView<T> child,
                          final Layout layout ) {

        children.add( child );

        super.addChild( ( IPrimitive<?> ) child.getContainer(), BasicShapesUtils.getWiresLayout( layout ) );

    }

    @Override
    public void removeChild( final BasicShapeView<T> child ) {

        children.remove( child );

        super.removeChild( ( IPrimitive<?> ) child.getContainer() );

    }

    @Override
    public Iterable<BasicShapeView<T>> getChildren() {
        return children;
    }

    @Override
    public boolean supports( final ViewEventType type ) {
        return eventHandlerManager.supports( type );
    }

    @Override
    public Shape<?> getAttachableShape() {
        return getShape();
    }

    public void updatePath( final double width,
                               final double height ) {

        final double x = getPath().getX();
        final double y = getPath().getY();
        getPath().clear().rect(x, y, width, height);
        refresh();
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitle( final String title ) {

        if ( null == text ) {
            text = buildText( title );
            this.addChild( text, getTextPosition() );
        } else {
            text.setText( title );
        }
        text.moveToTop();

        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitlePosition( final Position position ) {
        if ( Position.BOTTOM.equals( position ) ) {
            this.textPosition = LayoutContainer.Layout.BOTTOM;
        } else if ( Position.TOP.equals( position ) ) {
            this.textPosition = LayoutContainer.Layout.TOP;
        } else if ( Position.LEFT.equals( position ) ) {
            this.textPosition = LayoutContainer.Layout.LEFT;
        } else if ( Position.RIGHT.equals( position ) ) {
            this.textPosition = LayoutContainer.Layout.RIGHT;
        } else if ( Position.CENTER.equals( position ) ) {
            this.textPosition = LayoutContainer.Layout.CENTER;
        }
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitleStrokeColor( final String color ) {
        text.setStrokeColor( color );
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitleFontFamily( final String fontFamily ) {
        text.setFontFamily( fontFamily );
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitleFontSize( final double fontSize ) {
        text.setFontSize( fontSize );
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitleAlpha( final double alpha ) {
        text.setAlpha( alpha );
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setTitleStrokeWidth( final double strokeWidth ) {
        text.setStrokeWidth( strokeWidth );
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T moveTitleToTop() {
        text.moveToTop();
        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T refreshTitle() {

        if ( null != text ) {
            text.moveToTop();
        }

        return ( T ) this;
    }

    protected Text buildText( String _text ) {
        Text text = new Text( _text ).setFontSize( 14 ).setFillColor( ColorName.BLACK ).setStrokeWidth( 1 );
        return text.moveToTop().setDraggable( false ).setAlpha( 0 );
    }

    public Text getText() {
        return text;
    }

    private WiresLayoutContainer.Layout getTextPosition() {
        return textPosition;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T setFillGradient( final Type type,
                              final String startColor,
                              final String endColor ) {

        this.fillGradientType = type;
        this.fillGradientStartColor = startColor;
        this.fillGradientEndColor = endColor;

        if ( null != getShape() ) {
            final BoundingBox bb = getShape().getBoundingBox();
            final double width = bb.getWidth();
            final double height = bb.getHeight();
            updateFillGradient( width, height );
        }

        return ( T ) this;
    }

    @SuppressWarnings( "unchecked" )
    public T updateFillGradient( final double width,
                                    final double height ) {

        if ( this.fillGradientType != null
                && this.fillGradientStartColor != null
                && this.fillGradientEndColor != null ) {

            final LinearGradient gradient = LienzoShapeUtils.getLinearGradient( fillGradientStartColor,
                    fillGradientEndColor, width, height );

            getShape().setFillGradient( gradient );

        }

        return ( T ) this;
    }

    @Override
    protected void doDestroy() {

        if ( null != eventHandlerManager ) {

            // Remove all registered handlers.
            eventHandlerManager.destroy();
            eventHandlerManager = null;

        }

        if ( null != decorator ) {
            decorator.removeFromParent();
            decorator = null;
        }

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
    @SuppressWarnings( "unchecked" )
    public T addHandler( final ViewEventType type,
                         final ViewHandler<? extends ViewEvent> eventHandler ) {


        if ( supports( type ) ) {

            if ( ViewEventType.DRAG.equals( type ) ) {

                final HandlerRegistration[] registrations =
                        registerDragHandler( ( org.wirez.core.client.shape.view.event.DragHandler ) eventHandler );

                eventHandlerManager.addHandlersRegistration( type, registrations );

            } else {

                eventHandlerManager.addHandler( type, eventHandler );

            }

        }

        return ( T ) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public T removeHandler( final ViewHandler<? extends ViewEvent> eventHandler ) {

        eventHandlerManager.removeHandler( eventHandler );

        return ( T ) this;

    }

    @Override
    public T enableHandlers() {
        eventHandlerManager.enable();
        return ( T ) this;
    }

    @Override
    public T disableHandlers() {
        eventHandlerManager.disable();
        return ( T ) this;
    }

    // TODO: listen for WiresMoveEvent's as well?
    protected HandlerRegistration[] registerDragHandler( final ViewHandler<org.wirez.core.client.shape.view.event.DragEvent> eventHandler ) {

        final DragHandler dragHandler = ( DragHandler ) eventHandler;

        setDraggable( true );

        HandlerRegistration dragStartReg = addWiresDragStartHandler( wiresDragStartEvent -> {
            final DragEvent e = buildDragEvent( wiresDragStartEvent );
            dragHandler.start( e );
        } );

        HandlerRegistration dragMoveReg = addWiresDragMoveHandler( wiresDragMoveEvent -> {
            final DragEvent e = buildDragEvent( wiresDragMoveEvent );
            dragHandler.handle( e );
        } );


        HandlerRegistration dragEndReg = addWiresDragEndHandler( wiresDragEndEvent -> {
            final DragEvent e = buildDragEvent( wiresDragEndEvent );
            dragHandler.end( e );
        } );

       return new HandlerRegistration[] { dragStartReg, dragMoveReg, dragEndReg };
    }

    // TODO: Client & absolute coords.
    private DragEvent buildDragEvent( final AbstractWiresDragEvent sourceDragEvent ) {
        final double x = sourceDragEvent.getX();
        final double y = sourceDragEvent.getY();
        return new DragEvent( x, y, x, y, x, y );
    }

}
