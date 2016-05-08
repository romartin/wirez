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

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.Shape;

public abstract class AbstractShapeAnimation implements ShapeAnimation {

    public static final long ANIMATION_DURATION = 500;
    
    protected Shape shape;
    protected Canvas canvas;
    protected AnimationCallback callback;
    protected long duration = ANIMATION_DURATION;

    @Override
    public ShapeAnimation forShape( final Shape<?> shape ) {
        this.shape = shape;
        return this;
    }

    @Override
    public AbstractShapeAnimation forCanvas(final Canvas canvas) {
        this.canvas = canvas;
        return this;
    }

    @Override
    public AbstractShapeAnimation setCallback(final AnimationCallback callback) {
        this.callback = callback;
        return this;
    }

    public AnimationCallback getCallback() {
        return callback;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public AbstractShapeAnimation setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    protected void onStart() {

        if (shape instanceof Lifecycle) {
            
            ((Lifecycle) shape).beforeDraw();
            
        }

    }
    
    protected void onClose() {

        if (shape instanceof Lifecycle) {
            
            ((Lifecycle) shape).afterDraw();
            
        }
        
    }
    
    
}
