package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.*;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.util.Geometry;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import org.wirez.lienzo.primitive.PrimitiveDragProxy;
import org.wirez.lienzo.toolbox.builder.Button;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEvent;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEventHandler;

import java.util.List;

public class ToolboxButton {
    private final WiresShape primitive;
    private final Shape<?> shape;
    public static final double BUTTON_SIZE = 16;
    private final Layer layer;
    private final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();
    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler dragEndHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;
    
    private final MultiPath decorator = new MultiPath().rect(0, 0, BUTTON_SIZE, BUTTON_SIZE)
            .setFillColor(ColorName.LIGHTGREY)
            .setFillAlpha(0.1)
            .setStrokeWidth(0)
            .setDraggable(false);

    public ToolboxButton(final Layer layer, 
                         final Shape<?> shape, 
                         final List<Button.WhenReady> callbacks,
                         final ToolboxButtonEventHandler clickHandler,
                         final ToolboxButtonEventHandler dragEndHandler,
                         final ToolboxButtonEventHandler mouseEnterHandler,
                         final ToolboxButtonEventHandler mouseExitHandler) {
        this.layer = layer;
        this.clickHandler = clickHandler;
        this.dragEndHandler = dragEndHandler;
        this.mouseEnterHandler = mouseEnterHandler;
        this.mouseExitHandler = mouseExitHandler;
        this.shape = shape;
        this.primitive = build(shape, BUTTON_SIZE, BUTTON_SIZE);
        
        for (Button.WhenReady callback : callbacks) {
            callback.whenReady(this);
        }
    }

    public WiresShape getShape() {
        return primitive;
    }

    public MultiPath getDecorator() {
        return decorator;
    }
    
    public void remove() {
        handlerRegistrationManager.removeHandler();
        primitive.removeFromParent();
    }

    private WiresShape build(final Shape<?> shape,
                             final double width,
                             final double height) {
        WiresManager manager = WiresManager.get(layer);
        WiresShape wiresShape = manager.createShape(decorator).setDraggable( false );

        Geometry.setScaleToFit(shape, width, height);
        wiresShape.getContainer().add( shape.setDraggable( false ) );
        decorator.moveToTop();

        wiresShape.getPath().addNodeMouseEnterHandler(event -> {

            onButtonMouseEnter();

            if ( null != mouseEnterHandler ) {
                mouseEnterHandler.fire( buildEvent( event.getX(), event.getY() ) );
            }

        });


        wiresShape.getPath().addNodeMouseExitHandler(event -> {

            onButtonMouseExit();

            if ( null != mouseExitHandler) {
                mouseExitHandler.fire( buildEvent( event.getX(), event.getY() ) );
            }

        });

        if ( null != clickHandler ) {

            wiresShape.getGroup().addNodeMouseClickHandler(event -> 
                    clickHandler.fire( buildEvent( event.getX(), event.getY() ) ));
            
        }
        
        if ( null != dragEndHandler ) {

            wiresShape.getGroup().addNodeMouseDownHandler(event -> 
                    new PrimitiveDragProxy(layer, shape.copy(), event.getX(), event.getY(), 200, new PrimitiveDragProxy.Callback() {
                
                @Override
                public void onStart(final int x, final int y) {
                    
                }

                @Override
                public void onMove(final int x, final int y) {
                }

                @Override
                public void onComplete(final int x, final int y) {
                    dragEndHandler.fire( buildEvent( x, y ) );
                }
                        
            }));
            
        }
        
        return wiresShape;
    }
    
    private void onButtonMouseEnter() {
        doButtonAnimate( ColorName.ORANGERED.getColorString() );
    }

    private void onButtonMouseExit() {
        doButtonAnimate( ColorName.BLACK.getColorString() );
    }
    
    private void doButtonAnimate( final String sc ) {
        shape.animate(AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList(
                        AnimationProperty.Properties.STROKE_COLOR(sc)),
                200, new AnimationCallback());
    }
    
    private ToolboxButtonEvent buildEvent( final int x, final int y ) {
        
        return new ToolboxButtonEvent() {

            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
        
    }

}
