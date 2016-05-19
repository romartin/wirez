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

package org.wirez.core.client.canvas.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.event.registration.AbstractCanvasShapeEvent;
import org.wirez.core.client.shape.Shape;

/**
 * CDI event when the shape's state for an element in the graph has been modified. 
 */
@NonPortable
public final class ShapeStateModifiedEvent extends AbstractCanvasShapeEvent {
    
    private ShapeState state;

    public ShapeStateModifiedEvent(final Canvas canvas,
                                   final Shape shape,
                                   final ShapeState state) {
        super( canvas, shape );
        this.state = state;
    }

    public ShapeState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "CanvasShapeStateModifiedEvent [shape=" + shape.getUUID() + "] [state=" + state.name() + "]";
    }
}
