package org.wirez.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.shape.IDrawable;
import org.uberfire.mvp.Command;

import java.util.List;

public class AnimatedWiresUtils {
    
    public static boolean animate( final IDrawable<?> drawable,
                                     final org.wirez.core.client.shape.view.animation.AnimationTweener tweener,
                                     final double duration,
                                     final List<com.ait.lienzo.client.core.animation.AnimationProperty> propertyList,
                                     final List<Command> animationCloseCallbacks ) {

        // Container property animations.
        if ( !propertyList.isEmpty() ) {

            final AnimationProperties properties = new AnimationProperties();
            for ( final com.ait.lienzo.client.core.animation.AnimationProperty p : propertyList ) {
                properties.push( p );
            }

            drawable.animate( getLienzoTweener(tweener), properties, duration, new AnimationCallback() {
                @Override
                public void onClose(final IAnimation animation,
                                    final IAnimationHandle handle) {
                    super.onClose(animation, handle);

                    for ( final Command callback : animationCloseCallbacks ) {
                        callback.execute();
                    }
                }
            });

            return true;
        }

        return false;
    }

    private static com.ait.lienzo.client.core.animation.AnimationTweener getLienzoTweener( final org.wirez.core.client.shape.view.animation.AnimationTweener tweener ) {

        switch (tweener) {
            case LINEAR:
                return com.ait.lienzo.client.core.animation.AnimationTweener.LINEAR;
            case EASE_IN:
                return com.ait.lienzo.client.core.animation.AnimationTweener.EASE_IN;
            case EASE_OUT:
                return com.ait.lienzo.client.core.animation.AnimationTweener.EASE_OUT;
            case EASE_IN_OUT:
                return com.ait.lienzo.client.core.animation.AnimationTweener.EASE_IN_OUT;
            case ELASTIC:
                return com.ait.lienzo.client.core.animation.AnimationTweener.ELASTIC;
            case BOUNCE:
                return com.ait.lienzo.client.core.animation.AnimationTweener.BOUNCE;
        }

        throw new UnsupportedOperationException( " Animation tweener not supported for this shape [tweener=" + tweener.name() + "]" );
    }
    
}
