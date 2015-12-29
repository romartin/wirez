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

package org.wirez.client.widgets.event;

import org.uberfire.workbench.events.UberFireEvent;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.Shape;
import org.wirez.core.client.factory.ShapeFactory;

/**
 * <p>CDI event when a shape in the palette is selected.</p>
 *
 */
public class PaletteShapeSelectedEvent implements UberFireEvent {

    private ShapeFactory<? extends Definition, ? extends Shape> shapeFactory;

    public PaletteShapeSelectedEvent(final ShapeFactory<? extends Definition, ? extends Shape> shapeFactory) {
        this.shapeFactory = shapeFactory;
    }

    public ShapeFactory<? extends Definition, ? extends Shape> getShapeFactory() {
        return shapeFactory;
    }

    @Override
    public String toString() {
        return "PaletteShapeSelectedEvent [factory=" + shapeFactory.toString() + "]";
    }

}
