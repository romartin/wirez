package org.wirez.core.client.shape.view.animation;

public interface HasAnimations<T> {

    T addAnimationProperties( AnimationProperty<?>... properties );
    
    T animate( AnimationTweener tweener, double duration );
    
}
