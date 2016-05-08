package org.wirez.core.client.shape;

import org.wirez.core.client.shape.view.animation.AnimationTweener;

public interface MutationContext {
    
    enum Type {
        STATIC, ANIMATION;
    }
    
    Type getType();
    
    
    MutationContext STATIC = new StaticContext();
    MutationContext ANIMATION_LINEAL = new AnimationContext( AnimationTweener.LINEAR, 1000 );
    MutationContext ANIMATION_EASE_IN = new AnimationContext( AnimationTweener.EASE_IN, 1000 );
    MutationContext ANIMATION_EASE_OUT = new AnimationContext( AnimationTweener.EASE_OUT, 1000 );
    MutationContext ANIMATION_EASE_IN_OUT = new AnimationContext( AnimationTweener.EASE_IN_OUT, 1000 );
    MutationContext ANIMATION_ELASTIC = new AnimationContext( AnimationTweener.ELASTIC, 1000 );
    MutationContext ANIMATION_BOUNCE = new AnimationContext( AnimationTweener.BOUNCE, 1000 );

    class StaticContext implements MutationContext {

        @Override
        public Type getType() {
            return Type.STATIC;
        }
        
    }
    
    class AnimationContext implements MutationContext {

        private final AnimationTweener tweener;
        private final double duration;

        public AnimationContext(final AnimationTweener tweener, 
                               final double duration) {
            this.tweener = tweener;
            this.duration = duration;
        }

        @Override
        public Type getType() {
            return Type.ANIMATION;
        }

        public AnimationTweener getTweener() {
            return tweener;
        }

        public double getDuration() {
            return duration;
        }
        
    }
    
}
