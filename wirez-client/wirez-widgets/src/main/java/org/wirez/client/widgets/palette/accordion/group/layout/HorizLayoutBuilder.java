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
    private double height = 0;
    private double currentX = 0;
    private double currentY = 0;

    @Override
    public LayoutBuilder setSize(final double width, final double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public double[] add(final double gW, final double gH) {
        
        final double maxX = currentX + gW;
        
        if (currentY == 0 || ( gH > currentY) ) {
            currentY += gH;
        }
        
        if (maxX > width) {
            currentX = 0;
            currentY += gH;
            if (currentY > height) {
                throw new IndexOutOfBoundsException("No more glyphs can be added, not enough space.");
            }
        } else {
            currentX += gW;
        }

        return new double[] {currentX - ( gW / 2), currentY - ( gH / 2)};
    }

    @Override
    public LayoutBuilder clear() {
        currentX = 0;
        currentY = 0;
        return this;
    }
    
}
