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

package org.wirez.basicset.client;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresManager;

import java.util.ArrayList;
import java.util.Collection;

public class RectangleShape extends BaseBasicShape<org.wirez.basicset.api.Rectangle> {

    private static final double WIDTH = 100;
    private static final double HEIGHT = 100;
    
    protected Rectangle rectangle;
    protected Rectangle decorator;

    public RectangleShape(MultiPath path, Group group, WiresManager manager) {
        super(path, group, manager);
        init();
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( decorator );
        }};
    }

    @Override
    public Shape getShape() {
        return rectangle;
    }

    protected void init() {
        rectangle = new Rectangle(WIDTH, HEIGHT).setX(0).setY(0).setStrokeWidth(0);
        decorator = new Rectangle(WIDTH, HEIGHT).setX(0).setY(0).setStrokeWidth(0).setStrokeAlpha(0).setFillAlpha(0);
        getGroup().add(decorator);
        getGroup().add(rectangle);
    }

    @Override
    public String toString() {
        return "RectangleShape{}";
    }
}
