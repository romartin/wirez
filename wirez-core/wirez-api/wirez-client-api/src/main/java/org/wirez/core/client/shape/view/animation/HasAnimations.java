package org.wirez.core.client.shape.view.animation;

public interface HasAnimations<T> {

    T addAnimationProperties(org.wirez.core.client.shape.view.animation.AnimationProperty<?>... properties);
    
    T animate(org.wirez.core.client.shape.view.animation.AnimationTweener tweener, double duration);
    
}
