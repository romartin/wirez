package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.IControlHandle;
import com.ait.lienzo.client.core.shape.wires.IControlHandleList;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.event.*;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.event.shared.HandlerRegistration;
import org.kie.workbench.common.stunner.client.lienzo.shape.view.AbstractShapeView;
import org.kie.workbench.common.stunner.client.lienzo.shape.view.ViewEventHandlerManager;
import org.kie.workbench.common.stunner.client.lienzo.util.LienzoShapeUtils;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.client.shape.view.HasControlPoints;
import org.kie.workbench.common.stunner.core.client.shape.view.HasEventHandlers;
import org.kie.workbench.common.stunner.core.client.shape.view.HasFillGradient;
import org.kie.workbench.common.stunner.core.client.shape.view.HasTitle;
import org.kie.workbench.common.stunner.core.client.shape.view.event.*;
import org.kie.workbench.common.stunner.shapes.client.util.BasicShapesUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicShapeView<T>
        extends AbstractShapeView<T>
        implements
            HasTitle<T>,
            HasControlPoints<T>,
            HasEventHandlers<T, Shape<?>>,
            HasFillGradient<T>,
            HasChildren<BasicShapeView<T>> {

    private static final ViewEventType[] SUPPORTED_EVENT_TYPES = new ViewEventType[]{
            ViewEventType.MOUSE_CLICK, ViewEventType.MOUSE_DBL_CLICK, ViewEventType.DRAG, ViewEventType.RESIZE,
            ViewEventType.TOUCH, ViewEventType.GESTURE
    };

    private ViewEventHandlerManager eventHandlerManager;
    private final List<BasicShapeView<T>> children = new ArrayList<>();
    private Text text;

    private WiresLayoutContainer.Layout textPosition;
    private Type fillGradientType = null;
    private String fillGradientStartColor = null;
    private String fillGradientEndColor = null;

    public BasicShapeView( final MultiPath path ) {
        super( path );
        this.textPosition = WiresLayoutContainer.Layout.BOTTOM;

        initialize();

    }

    private void initialize() {

        createEventHandlerManager( getGroup() );

        postInitialize();

        refresh();

    }

    protected void postInitialize() {

    }

    private void createEventHandlerManager( final Node<?> node ) {

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

    private Text buildText( String _text ) {
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
    public T showControlPoints( final ControlPointType type ) {
        IControlHandleList ctrls = loadControls( translate( type ) );
        if ( null != ctrls ) {
            ctrls.show();
        }
        return ( T ) this;
    }

    private IControlHandle.ControlHandleType translate( final ControlPointType type ) {
        if ( type.equals( ControlPointType.RESIZE ) ) {
            return IControlHandle.ControlHandleStandardType.RESIZE;
        }
        return IControlHandle.ControlHandleStandardType.MAGNET;
    }

    @Override
    public T hideControlPoints() {
        IControlHandleList ctrls = getControls();
        if ( null != ctrls ) {
            ctrls.hide();
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
                        registerDragHandler( ( DragHandler ) eventHandler );

                eventHandlerManager.addHandlersRegistration( type, registrations );

            } else if ( ViewEventType.RESIZE.equals( type ) ) {

                final HandlerRegistration[] registrations =
                        registerResizeHandler( ( ResizeHandler ) eventHandler );

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
    private HandlerRegistration[] registerDragHandler( final ViewHandler<DragEvent> eventHandler ) {

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

    private HandlerRegistration[] registerResizeHandler( final ViewHandler<ResizeEvent> eventHandler ) {

        final ResizeHandler resizeHandler = ( ResizeHandler ) eventHandler;

        setResizable( true );

        HandlerRegistration r0 = addWiresResizeStartHandler( new WiresResizeStartHandler() {
            @Override
            public void onShapeResizeStart( WiresResizeStartEvent wiresResizeStartEvent ) {
                final ResizeEvent event = buildResizeEvent( wiresResizeStartEvent );
                resizeHandler.start( event );
            }
        } );

        HandlerRegistration r1 = addWiresResizeStepHandler( new WiresResizeStepHandler() {
            @Override
            public void onShapeResizeStep( WiresResizeStepEvent wiresResizeStepEvent ) {
                final ResizeEvent event = buildResizeEvent( wiresResizeStepEvent );
                resizeHandler.handle( event );
            }
        } );

        HandlerRegistration r2 = addWiresResizeEndHandler( new WiresResizeEndHandler() {
            @Override
            public void onShapeResizeEnd( WiresResizeEndEvent wiresResizeEndEvent ) {
                final ResizeEvent event = buildResizeEvent( wiresResizeEndEvent );
                resizeHandler.end( event );
            }
        } );


        return new HandlerRegistration[] { r0, r1, r2};
    }


    private DragEvent buildDragEvent( final AbstractWiresDragEvent sourceDragEvent ) {
        final double x = sourceDragEvent.getX();
        final double y = sourceDragEvent.getY();
        final double cx = sourceDragEvent.getNodeDragEvent().getX();
        final double cy = sourceDragEvent.getNodeDragEvent().getY();
        final int dx = sourceDragEvent.getNodeDragEvent().getDragContext().getDx();
        final int dy = sourceDragEvent.getNodeDragEvent().getDragContext().getDy();
        return new DragEvent( x, y, cx, cy, dx, dy );
    }

    private ResizeEvent buildResizeEvent( final AbstractWiresResizeEvent sourceResizeEvent ) {
        final double x = sourceResizeEvent.getX();
        final double y = sourceResizeEvent.getY();
        final double cx = sourceResizeEvent.getNodeDragEvent().getX();
        final double cy = sourceResizeEvent.getNodeDragEvent().getY();
        final double w = sourceResizeEvent.getWidth();
        final double h = sourceResizeEvent.getHeight();
        return new ResizeEvent( x, y, cx, cy, w, h );
    }

}
