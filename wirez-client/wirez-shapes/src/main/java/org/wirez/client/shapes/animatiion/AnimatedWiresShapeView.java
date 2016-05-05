package org.wirez.client.shapes.animatiion;

import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.uberfire.mvp.Command;
import org.wirez.client.shapes.AbstractWiresShapeView;
import org.wirez.core.client.shape.view.animation.HasAnimations;

import java.util.LinkedList;
import java.util.List;

import static org.wirez.core.client.shape.view.animation.AnimationProperties.*;

public abstract class AnimatedWiresShapeView<T> extends AbstractWiresShapeView<T> implements HasAnimations<T> {

    public AnimatedWiresShapeView(final MultiPath path, 
                                  final WiresManager manager) {
        super(path, manager);
    }

    protected final List<AnimationProperty> shapeAnimationProperties = new LinkedList<>();
    protected final List<AnimationProperty> decoratorAnimationProperties = new LinkedList<>();
    protected final List<Command> animationCloseCallbacks = new LinkedList<>();

    @Override
    public T addAnimationProperties(final org.wirez.core.client.shape.view.animation.AnimationProperty<?>... properties) {
        
        if ( null != properties && properties.length > 0 ) {

            Double x = null;
            Double y = null;
            Double w = null;
            Double h = null;
            for (final org.wirez.core.client.shape.view.animation.AnimationProperty<?> property : properties ) {
                
                if ( X.class.equals( property.getClass()) ) {

                    x = (Double) property.getValue();

                } else if ( Y.class.equals( property.getClass()) ) {

                    y = (Double) property.getValue() ;

                } else if ( WIDTH.class.equals( property.getClass()) ) {

                    w = (Double) property.getValue() ;

                } else if ( HEIGHT.class.equals( property.getClass()) ) {

                    h = (Double) property.getValue() ;

                } else if ( RADIUS.class.equals( property.getClass()) ) {

                    animateRadius( (Double) property.getValue() );

                } else if ( FILL_COLOR.class.equals( property.getClass()) ) {

                    animateFillColor( (String) property.getValue() );

                } else if ( STROKE_COLOR.class.equals( property.getClass()) ) {

                    animateStrokeColor( (String) property.getValue() );

                } else if ( STROKE_WIDTH.class.equals( property.getClass()) ) {

                    animateStrokeWidth( (Double) property.getValue() );

                }
            }
        
            // Position.
            animatePosition( x  , y );
       
            // Size.
            animateSize( w  , h );

        }
        
        return (T) this;
    }

    protected void animatePosition( final Double x, 
                                    final Double y ) {
        if ( null != x ) {
            shapeAnimationProperties.add( AnimationProperty.Properties.X( x ) );
        }
        if ( null != y ) {
            shapeAnimationProperties.add( AnimationProperty.Properties.Y( y ) );
        }
    }

    protected void animateSize( final Double w,
                                final Double h ) {
        if ( null != w && null != h ) {
            updatePath( w, h);
            doMoveChildren( w, h );
            shapeAnimationProperties.add( AnimationProperty.Properties.WIDTH( w ) );
            decoratorAnimationProperties.add( AnimationProperty.Properties.WIDTH( w ) );
            shapeAnimationProperties.add( AnimationProperty.Properties.HEIGHT( h ) );
            decoratorAnimationProperties.add( AnimationProperty.Properties.HEIGHT( h ) );
        }
    }

    protected void animateRadius( final Double value ) {
        if ( null != value ) {
            final double size = value * 2;
            updatePath( size, size );
            doMoveChildren( size, size );
            shapeAnimationProperties.add( AnimationProperty.Properties.RADIUS( value ) );
            decoratorAnimationProperties.add( AnimationProperty.Properties.RADIUS( value ) );
        }
    }

    protected void animateFillColor( final String value ) {
        shapeAnimationProperties.add( AnimationProperty.Properties.FILL_COLOR( value ) );
    }

    protected void animateStrokeColor( final String value ) {
        shapeAnimationProperties.add( AnimationProperty.Properties.STROKE_COLOR( value ) );
    }

    protected void animateStrokeWidth( final Double value ) {
        shapeAnimationProperties.add( AnimationProperty.Properties.STROKE_WIDTH( value ) );
    }
    
    public T animate(final org.wirez.core.client.shape.view.animation.AnimationTweener tweener, final double duration ) {

        // Shape property animations.
        AnimatedWiresUtils.animate( getShape(), tweener, duration, shapeAnimationProperties, animationCloseCallbacks );
        shapeAnimationProperties.clear();

        // Decorator property animations.
        AnimatedWiresUtils.animate( getDecorator(), tweener, duration, decoratorAnimationProperties, animationCloseCallbacks );
        decoratorAnimationProperties.clear();

        // Clear current animation close callbacks.
        animationCloseCallbacks.clear();
        
        return (T) this;
    }
    
    protected T addCloseCallback( final Command callback ) {
        this.animationCloseCallbacks.add( callback );
        return (T) this;
    }
    
}
