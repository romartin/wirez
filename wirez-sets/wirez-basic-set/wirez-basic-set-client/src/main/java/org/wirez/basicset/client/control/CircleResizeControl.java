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

package org.wirez.basicset.client.control;

import org.wirez.basicset.api.property.RadiusBuilder;
import org.wirez.basicset.client.CircleShape;
import org.wirez.core.api.graph.commands.UpdateElementPropertyValueCommand;
import org.wirez.core.api.graph.commands.UpdateElementSizeCommand;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.client.canvas.command.impl.CompositeElementCanvasCommand;
import org.wirez.core.client.control.DefaultResizeControl;

public class CircleResizeControl extends DefaultResizeControl<CircleShape, ViewNode> {

    @Override
    protected void doResizeStep(CircleShape shape, ViewNode element, double width, double height) {
        double radius = getRadius(width, height);
        shape.setRadius(radius);
    }
    
    @Override
    protected void doResizeEnd(CircleShape shape, ViewNode element, double width, double height) {
        double radius = getRadius(width, height);
        commandManager.execute(
                new CompositeElementCanvasCommand(element)
                        .add(new UpdateElementSizeCommand(element, width, height))
                        .add(new UpdateElementPropertyValueCommand(element, new RadiusBuilder().build() , (int) radius ))
        );
        
    }
    
    protected double getRadius(double width, double height) {
        final double radiusW = width / 2;
        final double radiusH = height / 2;
        return  (radiusH >= radiusW ? radiusW : radiusH);
    }
    
}
