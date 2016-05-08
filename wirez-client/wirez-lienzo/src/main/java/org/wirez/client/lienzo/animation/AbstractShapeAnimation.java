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

package org.wirez.client.lienzo.animation;

import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import org.wirez.core.client.shape.view.HasDecorators;

import java.util.Collection;

public abstract class AbstractShapeAnimation extends org.wirez.core.client.animation.AbstractShapeAnimation {
    
    protected com.ait.lienzo.client.core.animation.AnimationCallback animationCallback = new com.ait.lienzo.client.core.animation.AnimationCallback() {

        @Override
        public void onStart(IAnimation animation, IAnimationHandle handle) {
            super.onStart(animation, handle);
            AbstractShapeAnimation.this.onStart();
            if ( null != callback ) {
                callback.onStart();
            }
        }

        @Override
        public void onClose(IAnimation animation, IAnimationHandle handle) {
            super.onClose(animation, handle);
            AbstractShapeAnimation.this.onClose();
            if ( null != callback ) {
                callback.onComplete();
            }
        }
    };

    public Collection<com.ait.lienzo.client.core.shape.Shape> getDecorators() {
        if ( shape.getShapeView() instanceof HasDecorators) {
            return ( (HasDecorators) shape.getShapeView()).getDecorators();
        }
        return null;
    }
    
}
