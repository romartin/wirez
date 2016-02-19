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

package org.wirez.core.client.canvas;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.client.canvas.listener.CanvasListener;
import org.wirez.core.client.canvas.settings.CanvasSettings;

public interface CanvasHandler<C extends Canvas, S extends CanvasSettings, L extends CanvasListener> {

    /**
     * Load a given graph and displays it into the canvas..
     */
    CanvasHandler<C, S, L> initialize(C canvas, Diagram<?> diagram, S settings);

    /**
     * Listens to events from elements in the canvas.
     */
    CanvasHandler<C, S, L> addListener(L listener);

    /**
     * The managed canvas instance.
     */
    C getCanvas();

    /**
     * The managed diagram instance.
     */
    Diagram<?> getDiagram();
    
}
