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

import com.ait.lienzo.client.core.animation.AnimationProperties;
import org.wirez.core.client.Shape;

import java.util.Collection;

import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.*;

public class ShapeHighlightAnimation extends ShapeSelectionAnimation {

    private double[] _strokeWidth;
    private String[] _color;
    
    public ShapeHighlightAnimation(final Shape shape) {
        super(shape);
    }

    @Override
    public void run() {
        final Collection<com.ait.lienzo.client.core.shape.Shape> decorators = getDecorators();
        if ( null != decorators && !decorators.isEmpty() ) {
            _strokeWidth = new double[decorators.size()];
            _color = new String[decorators.size()];
            int x = 0;
            for( final com.ait.lienzo.client.core.shape.Shape decorator : decorators ) {
                _strokeWidth[x] = decorator.getStrokeWidth();
                _color[x] = decorator.getStrokeColor();
                decorator.setStrokeWidth(getStrokeWidth()).setStrokeColor(getColor()).setStrokeAlpha(0);
                decorator.moveToTop();
                decorator.animate(getAnimationTweener(), AnimationProperties.toPropertyList(STROKE_ALPHA(1)), 
                        getDuration(), animationCallback);
                x++;
            }
        }
    }

    @Override
    protected void onClose() {
        super.onClose();
        final Collection<com.ait.lienzo.client.core.shape.Shape> decorators = getDecorators();
        if ( null != decorators && !decorators.isEmpty() ) {
            int x = 0;
            for( final com.ait.lienzo.client.core.shape.Shape decorator : decorators ) {
                decorator.setStrokeAlpha(1).moveToTop();
                decorator.animate(getAnimationTweener(), AnimationProperties.toPropertyList(STROKE_WIDTH(_strokeWidth[x]), STROKE_COLOR(_color[x])),
                        getDuration());
                x++;
            }
        }
    }
}
