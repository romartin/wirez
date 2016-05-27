package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.uberfire.mvp.Command;
import org.wirez.core.client.shape.view.animation.AnimationProperties;
import org.wirez.core.client.shape.view.animation.AnimationTweener;
import org.wirez.core.client.shape.view.animation.HasAnimations;
import org.wirez.shapes.client.view.BasicConnectorView;

import java.util.LinkedList;
import java.util.List;

public abstract class AnimatedWiresConnectorView<T> extends BasicConnectorView<T> implements HasAnimations<T> {

    protected final List<AnimationProperty> decoratableLineAnimationProperties = new LinkedList<>();
    protected final List<Command> animationCloseCallbacks = new LinkedList<>();
    
    public AnimatedWiresConnectorView(final AbstractDirectionalMultiPointShape<?> line,
                                      final MultiPathDecorator headDecorator,
                                      final MultiPathDecorator tailDecorator, 
                                      final WiresManager manager) {
        super(line, headDecorator, tailDecorator, manager);
    }

    public AnimatedWiresConnectorView(final WiresMagnet headMagnet, 
                                      final WiresMagnet tailMagnet, 
                                      final AbstractDirectionalMultiPointShape<?> line,
                                      final MultiPathDecorator headDecorator,
                                      final MultiPathDecorator tailDecorator, 
                                      final WiresManager manager) {
        super(headMagnet, tailMagnet, line, headDecorator, tailDecorator, manager);
    }

    @Override
    public T addAnimationProperties(final org.wirez.core.client.shape.view.animation.AnimationProperty<?>... properties) {
        
        
        if ( null != properties && properties.length > 0 ) {

            for (final org.wirez.core.client.shape.view.animation.AnimationProperty<?> property : properties ) {

                if ( AnimationProperties.FILL_COLOR.class.equals( property.getClass()) ) {

                    animateFillColor( (String) property.getValue() );

                } else if ( AnimationProperties.STROKE_COLOR.class.equals( property.getClass()) ) {

                    animateStrokeColor( (String) property.getValue() );

                } else if ( AnimationProperties.STROKE_WIDTH.class.equals( property.getClass()) ) {

                    animateStrokeWidth( (Double) property.getValue() );

                }
            }

        }
        
        return (T) this;
    }

    protected void animateFillColor( final String value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.FILL_COLOR( value ) );
    }

    protected void animateStrokeColor( final String value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.STROKE_COLOR( value ) );
    }

    protected void animateStrokeWidth( final Double value ) {
        decoratableLineAnimationProperties.add( AnimationProperty.Properties.STROKE_WIDTH( value ) );
    }

    @Override
    public T animate(final AnimationTweener tweener, 
                     final double duration) {

        // Shape property animations.
        AnimatedWiresUtils.animate( getGroup(), tweener, duration, decoratableLineAnimationProperties, animationCloseCallbacks );
        
        // Clear current animation properties and close callbacks.
        decoratableLineAnimationProperties.clear();
        animationCloseCallbacks.clear();

        return (T) this;
    }

    @Override
    protected void doDestroy() {
        decoratableLineAnimationProperties.clear();
        animationCloseCallbacks.clear();
    }
    
}
