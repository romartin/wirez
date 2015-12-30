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

package org.wirez.basicset.client.factory;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.shared.core.types.EventPropagationMode;
import org.wirez.basicset.api.Circle;
import org.wirez.basicset.api.Rectangle;
import org.wirez.basicset.client.CircleShape;
import org.wirez.basicset.client.RectangleShape;
import org.wirez.basicset.client.factory.control.CircleResizeControlFactory;
import org.wirez.basicset.client.glyph.CircleGlyph;
import org.wirez.basicset.client.glyph.RectangleGlyph;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.impl.BaseCanvas;
import org.wirez.core.client.factory.BaseShapeFactory;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CircleFactory extends BaseShapeFactory<Circle, CircleShape> {
    
    CircleResizeControlFactory circleResizeControlFactory;
    
    @Inject
    public CircleFactory(final DefaultShapeControlFactories defaultShapeControlFactories,
                         final CircleResizeControlFactory circleResizeControlFactory) {
        super(defaultShapeControlFactories);
        this.circleResizeControlFactory = circleResizeControlFactory;
    }

    @Override
    public boolean accepts(final Definition definition) {
        return definition instanceof Circle;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return CircleGlyph.INSTANCE;
    }

    @Override
    public String getDescription() {
        return "A circle";
    }

    @Override
    protected ShapeControlFactory<?, ?> getResizeControlFactory() {
        return circleResizeControlFactory;
    }
    
    @Override
    public CircleShape build(final Circle definition, final CanvasHandler canvasHandler) {

        final BaseCanvas baseWirezCanvas = (BaseCanvas) canvasHandler.getSettings().getCanvas();

        MultiPath path = new MultiPath().rect(0, 0, 50, 50).setStrokeAlpha(0);

        path.setDraggable(false);

        Group group = new Group();

        group.add(path);

        group.setDraggable(true);

        group.setEventPropagationMode(EventPropagationMode.FIRST_ANCESTOR);

        CircleShape circleShape = new CircleShape(path, group, baseWirezCanvas.getWiresManager());

        return circleShape;

    }

}
