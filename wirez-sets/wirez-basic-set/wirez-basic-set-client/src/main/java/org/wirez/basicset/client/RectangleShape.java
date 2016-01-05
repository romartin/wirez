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
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.client.mutation.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

public class RectangleShape extends BaseBasicShape<org.wirez.basicset.api.Rectangle> {


    public RectangleShape(MultiPath path, Group group, WiresManager manager) {
        super(path, group, manager);
    }

    @Override
    public Collection<Shape> getDecorators() {
        return new ArrayList<Shape>() {{
            add( getPath() );
        }};
    }

    @Override
    public Shape getShape() {
        return getPath();
    }

    @Override
    protected void _applyShapeSize(Shape shape, double w, double h, MutationContext mutationContext) {
        getPath().clear().rect(getPath().getX(), getPath().getY(), w , h).close();
    }

    @Override
    public String toString() {
        return "RectangleShape{}";
    }
}
