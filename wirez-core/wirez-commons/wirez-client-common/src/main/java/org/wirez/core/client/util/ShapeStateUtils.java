package org.wirez.core.client.util;

import com.google.gwt.user.client.Timer;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.shape.EdgeShape;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasDecorators;
import org.wirez.core.client.shape.view.HasState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShapeStateUtils {

    private static final long ANIMATION_SELECTION_DURATION = 250;
    private static final long ANIMATION_HIGHLIGHT_DURATION = 500;

    AnimationFactory animationFactory;

    @Inject
    public ShapeStateUtils( final AnimationFactory animationFactory ) {
        this.animationFactory = animationFactory;
    }

    public void select( final Canvas canvas,
                        final Shape shape ) {

        select( canvas, shape, null );

    }

    public void select( final Canvas canvas,
                        final Shape shape,
                        final ShapeAnimation.AnimationCallback callback ) {

        if ( shape.getShapeView() instanceof HasState ) {

            final HasState canvasStateMutation = ( HasState ) shape.getShapeView();
            canvasStateMutation.applyState( ShapeState.SELECTED );

            if ( null != callback ) {

                callback.onComplete();

            }

            canvas.draw();

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            final ShapeAnimation animation =
                    animationFactory
                            .newShapeSelectAnimation();

            if ( null != callback ) {

                animation.setCallback( callback );

            }

            animation
                    .forShape( shape )
                    .forCanvas( canvas )
                    .setDuration( ANIMATION_SELECTION_DURATION )
                    .run();
        }

    }

    public void deselect( final Canvas canvas,
                          final Shape shape ) {

        deselect( canvas, shape, null );

    }

    public void deselect( final Canvas canvas,
                          final Shape shape,
                          final ShapeAnimation.AnimationCallback callback ) {

        final boolean isConnector = shape instanceof EdgeShape;

        if ( shape.getShapeView() instanceof HasState ) {

            final HasState canvasStateMutation = ( HasState ) shape.getShapeView();
            canvasStateMutation.applyState( ShapeState.DESELECTED );

            if ( null != callback ) {

                callback.onComplete();

            }

            canvas.draw();

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            final ShapeDeSelectionAnimation animation =
                    animationFactory
                            .newShapeDeselectAnimation();

            if ( null != callback ) {

                animation.setCallback( callback );

            }

            animation
                    .setStrokeWidth( isConnector ? 1 : 0 )
                    .setStrokeAlpha( isConnector ? 1 : 0 )
                    .setColor( "#000000" )
                    .forShape( shape )
                    .forCanvas( canvas )
                    .setDuration( ANIMATION_SELECTION_DURATION )
                    .run();

        }

    }

    public void invalid( final Canvas canvas,
                         final Shape shape ) {

        invalid( canvas, shape, null );

    }

    public void invalid( final Canvas canvas,
                         final Shape shape,
                         final ShapeAnimation.AnimationCallback callback ) {

        if ( shape.getShapeView() instanceof HasState ) {

            final HasState canvasStateMutation = ( HasState ) shape.getShapeView();
            canvasStateMutation.applyState( ShapeState.INVALID );

            if ( null != callback ) {

                callback.onComplete();

            }

            canvas.draw();

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            final ShapeAnimation animation =
                    animationFactory
                            .newShapeNotValidAnimation();

            if ( null != callback ) {

                animation.setCallback( callback );
            }

            animation
                    .forShape( shape )
                    .forCanvas( canvas )
                    .setDuration( ANIMATION_SELECTION_DURATION )
                    .run();
        }

    }

    public void highlight( final Canvas canvas,
                           final Shape shape ) {

        highlight( canvas, shape, null );

    }

    public void highlight( final Canvas canvas,
                           final Shape shape,
                           final ShapeAnimation.AnimationCallback callback ) {

        if ( shape.getShapeView() instanceof HasState ) {

            final HasState canvasStateMutation = ( HasState ) shape.getShapeView();

            Timer t = new Timer() {

                @Override
                public void run() {

                    canvasStateMutation.applyState( ShapeState.UNHIGHLIGHT );

                    if ( null != callback ) {

                        callback.onComplete();

                    }

                    canvas.draw();

                }

            };

            canvasStateMutation.applyState( ShapeState.HIGHLIGHT );

            t.schedule( ( int ) ANIMATION_HIGHLIGHT_DURATION );

        } else if ( shape.getShapeView() instanceof HasDecorators ) {

            final ShapeAnimation deselectAnimation =
                    animationFactory
                            .newShapeHighlightAnimation();

            if ( null != callback ) {

                deselectAnimation.setCallback( callback );
            }

            deselectAnimation
                    .forShape( shape )
                    .forCanvas( canvas )
                    .setDuration( ANIMATION_HIGHLIGHT_DURATION )
                    .run();

        }

    }
}
