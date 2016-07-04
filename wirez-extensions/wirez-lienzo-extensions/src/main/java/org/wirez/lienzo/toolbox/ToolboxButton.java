package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.wirez.lienzo.primitive.PrimitiveDragProxy;
import org.wirez.lienzo.toolbox.builder.Button;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEvent;
import org.wirez.lienzo.toolbox.event.ToolboxButtonEventHandler;

import java.util.List;

public class ToolboxButton {
    private final WiresShape primitive;

    public static final double ANIMATION_DURATION = 200;
    private final Layer layer;
    private final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();
    private ToolboxButtonEventHandler clickHandler;
    private ToolboxButtonEventHandler dragEndHandler;
    private ToolboxButtonEventHandler mouseEnterHandler;
    private ToolboxButtonEventHandler mouseExitHandler;
    private double iScaleX;
    private double iScaleY;
    private String iFillColor;
    private String iStrokeColor;
    private boolean isHovering;
    private boolean isHoverComplete;
    private boolean isOutRequested;
    private HoverAnimation animation;

    private MultiPath decorator;

    public enum HoverAnimation {

        ELASTIC, HOVER_COLOR;

    }

    public ToolboxButton(final Layer layer,
                         final IPrimitive<?> shape,
                         final List<Button.WhenReady> callbacks,
                         final ToolboxButtonEventHandler clickHandler,
                         final ToolboxButtonEventHandler dragEndHandler,
                         final ToolboxButtonEventHandler mouseEnterHandler,
                         final ToolboxButtonEventHandler mouseExitHandler,
                         final HoverAnimation animation) {
        this.layer = layer;
        this.clickHandler = clickHandler;
        this.dragEndHandler = dragEndHandler;
        this.mouseEnterHandler = mouseEnterHandler;
        this.mouseExitHandler = mouseExitHandler;
        this.primitive = build( shape );
        this.isHovering = false;
        this.isHoverComplete = false;
        this.isOutRequested = false;
        this.animation = animation;

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
        decorator.removeFromParent();
        primitive.removeFromParent();
        layer.batch();
    }

    private WiresShape build( final IPrimitive<?> shape ) {

        final BoundingBox bb = shape.getBoundingBox();

        decorator = new MultiPath().rect( 0.5, 0.5, bb.getWidth() + 1 , bb.getHeight() + 1 )
                .setFillAlpha( 0.01 )
                .setStrokeWidth( 0 )
                .setStrokeAlpha( 0 )
                .setDraggable( false );

        final Point2D scale = shape.getScale();

        this.iFillColor = shape.getAttributes().getFillColor();
        this.iStrokeColor = shape.getAttributes().getStrokeColor();
        this.iFillColor = null != this.iFillColor ? this.iFillColor : "#000000";
        this.iStrokeColor = null != this.iStrokeColor ? this.iStrokeColor : "#000000";

        if ( null != scale ) {

            this.iScaleX = scale.getX();
            this.iScaleY = scale.getY();

            decorator.setScale( scale );

        } else {

            this.iScaleX = 1;
            this.iScaleY = 1;

        }

        WiresManager manager = WiresManager.get( layer );
        // TODO: DO not add into Align&Distro index neither set the Docking acceptor.
        WiresShape wiresShape = manager.createShape( decorator ).setDraggable( false );

        wiresShape.getContainer().add( shape.setDraggable( false ) );
        decorator.moveToTop();

        handlerRegistrationManager.register(

            wiresShape.getPath().addNodeMouseEnterHandler(event -> {

                onButtonMouseEnter( shape );

                if ( null != mouseEnterHandler ) {

                    mouseEnterHandler.fire( buildEvent( event.getX(), event.getY(), event.getHumanInputEvent().getClientX(), event.getHumanInputEvent().getClientY() ) );
                }

            })

        );


        handlerRegistrationManager.register(

            wiresShape.getPath().addNodeMouseExitHandler(event -> {

                onButtonMouseExit( shape );

                if ( null != mouseExitHandler) {
                    mouseExitHandler.fire(
                            buildEvent(
                                event.getX(),
                                event.getY(),
                                event.getHumanInputEvent().getClientX(),
                                event.getHumanInputEvent().getClientY()
                            )
                    );
                }

            })

        );

        if ( null != clickHandler ) {

            handlerRegistrationManager.register(

                    wiresShape.getGroup().addNodeMouseClickHandler(event ->
                        clickHandler.fire(
                                buildEvent(
                                        event.getX(),
                                        event.getY(),
                                        event.getHumanInputEvent().getClientX(),
                                        event.getHumanInputEvent().getClientY() ) )
                    )

            );

        }
        
        if ( null != dragEndHandler ) {

            handlerRegistrationManager.register(

                wiresShape.getGroup().addNodeMouseDownHandler(event ->
                    new PrimitiveDragProxy( layer,
                            shape.copy(),
                            event.getHumanInputEvent().getClientX(),
                            event.getHumanInputEvent().getClientY(),
                            200,
                            new PrimitiveDragProxy.Callback() {

                    @Override
                    public void onStart(final int x, final int y) {

                    }

                    @Override
                    public void onMove(final int x, final int y) {
                    }

                    @Override
                    public void onComplete(final int x, final int y) {
                        dragEndHandler.fire( buildEvent( x, y, x , y ) );
                    }

                }))

            );
            
        }
        
        return wiresShape;
    }
    
    private void onButtonMouseEnter( final IPrimitive<?> shape ) {

        if ( !isHoverComplete && !isHovering) {

            this.isHovering = true;

            doButtonAnimate( shape, iScaleX * 2, iScaleY * 2, 2, ColorName.BLUE.getColorString(),  ColorName.BLUE.getColorString(), true );

        }

    }

    private void onButtonMouseExit( final IPrimitive<?> shape ) {

        if ( isHoverComplete ) {

            doButtonAnimate( shape, iScaleX, iScaleY, 1, iFillColor, iStrokeColor, false );

        } else {

            isOutRequested = true;

        }

    }
    
    private void doButtonAnimate( final IPrimitive<?> shape,
                                  final double scaleX,
                                  final double scaleY,
                                  final double decoratorScale,
                                  final String fillColor,
                                  final String strokeColor,
                                  final boolean isHover ) {

        if ( HoverAnimation.ELASTIC.equals( animation ) ) {

            animateElastic( shape, scaleX, scaleY, decoratorScale, isHover );

        } else {

            animateHoverColor( shape, fillColor, strokeColor, isHover );

        }

    }
    
    private ToolboxButtonEvent buildEvent( final int x, final int y, final int clientX, final int clientY ) {
        
        return new ToolboxButtonEvent() {

            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }

            @Override
            public int getClientX() {
                return clientX;
            }

            @Override
            public int getClientY() {
                return clientY;
            }
        };
        
    }

    private void animateElastic( final IPrimitive<?> shape,
                                 final double scaleX,
                                 final double scaleY,
                                 final double decoratorScale,
                                 final boolean isHover ) {

        shape.animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList(
                        AnimationProperty.Properties.SCALE( scaleX, scaleY )
                ),
                ANIMATION_DURATION
        );

        decorator.animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList(
                        AnimationProperty.Properties.SCALE( decoratorScale )
                ),
                ANIMATION_DURATION,
                new HoverAnimationCallback( isHover, shape )
        );

    }

    private void animateHoverColor( final IPrimitive<?> shape,
                                    final String fillColor,
                                    final String strokeColor,
                                 final boolean isHover ) {

        shape.animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList(
                        AnimationProperty.Properties.FILL_COLOR( fillColor ),
                        AnimationProperty.Properties.STROKE_COLOR( strokeColor )
                ),
                ANIMATION_DURATION,
                new HoverAnimationCallback( isHover, shape )
        );


    }

    private final class HoverAnimationCallback extends AnimationCallback {

        private final boolean isHoverComplete;
        private final IPrimitive<?> shape;

        private HoverAnimationCallback( final boolean isHoverComplete,
                                        final IPrimitive<?> shape ) {
            this.isHoverComplete = isHoverComplete;
            this.shape = shape;
        }

        @Override
        public void onClose(final IAnimation animation, final IAnimationHandle handle) {

            super.onClose( animation, handle );

            ToolboxButton.this.isHovering = false;
            ToolboxButton.this.isHoverComplete = isHoverComplete;

            if ( ToolboxButton.this.isOutRequested ) {

                onButtonMouseExit( shape );
                ToolboxButton.this.isOutRequested = false;

            }

        }

    }

}
