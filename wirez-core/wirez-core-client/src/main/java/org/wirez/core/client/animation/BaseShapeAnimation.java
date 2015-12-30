/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.client.animation;

import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import org.wirez.core.client.Shape;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.impl.BaseShape;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseShapeAnimation implements ShapeAnimation {

    public static final long ANIMATION_DURATION = 500;
    
    final Shape shape;
    AnimationCallback callback;
    long duration;

    public BaseShapeAnimation(final Shape shape ) {
        this.shape = shape;
        this.duration = ANIMATION_DURATION;
    }

    public AnimationCallback getCallback() {
        return callback;
    }

    @Override
    public BaseShapeAnimation setCallback(final AnimationCallback callback) {
        this.callback = callback;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public BaseShapeAnimation setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    protected void onClose() {

    }
    
    protected com.ait.lienzo.client.core.animation.AnimationCallback animationCallback = new com.ait.lienzo.client.core.animation.AnimationCallback() {
        @Override
        public void onClose(IAnimation animation, IAnimationHandle handle) {
            super.onClose(animation, handle);
            BaseShapeAnimation.this.onClose();
            if ( null != callback ) {
                callback.onComplete();
            }
        }
    };

    public Collection<com.ait.lienzo.client.core.shape.Shape> getDecorators() {
        if ( shape instanceof BaseShape) {
            return ( (BaseShape) shape).getDecorators();
        } else if ( shape instanceof BaseConnector) {
            Collection<com.ait.lienzo.client.core.shape.Shape> decorators = new ArrayList<com.ait.lienzo.client.core.shape.Shape>(1);
            decorators.add( ( (BaseConnector) shape).getShape() );
            return decorators;
        }
        return null;
    }
    
}
