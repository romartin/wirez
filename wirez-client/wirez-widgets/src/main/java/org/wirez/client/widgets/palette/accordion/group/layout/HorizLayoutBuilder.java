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

package org.wirez.client.widgets.palette.accordion.group.layout;

public class HorizLayoutBuilder implements LayoutBuilder {

    private double width = 0;
    private double currentX = 0;
    private double currentY = 0;
    private double margin = 0;

    @Override
    public LayoutBuilder setWidth(final double width) {
        this.width = width;
        return this;
    }

    @Override
    public LayoutBuilder setHeight(final double height) {
        throw new UnsupportedOperationException("Height cannot be constrained if using the palette HorizLayoutBuilder.");
    }

    @Override
    public LayoutBuilder setItemMargin(final double margin) {
        this.margin = margin;
        this.currentX = margin;
        this.currentY = margin;
        return this;
    }

    @Override
    public double[] add(final double _gW, final double _gH) {
        final double gW = _gW + margin; 
        final double gH = _gH + margin;
        final double maxX = currentX + gW;
        
        if (currentY == 0 || ( gH > currentY) ) {
            currentY += gH;
        }
        
        if (maxX > width) {
            currentX = 0;
            currentY += gH;
        } else {
            currentX += gW;
        }

        return new double[] {currentX - ( gW / 2), currentY - gH };
    }

    @Override
    public double[] build() {
        return new double[] { width, currentY };
    }

    @Override
    public LayoutBuilder clear() {
        currentX = 0;
        currentY = 0;
        return this;
    }
    
}
