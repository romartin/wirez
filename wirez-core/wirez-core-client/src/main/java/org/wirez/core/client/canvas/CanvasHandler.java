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

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;

public interface CanvasHandler {

    /**
     * Initialize a graphical shape canvas.
     */
    CanvasHandler initialize(CanvasSettings settings, Canvas canvas);

    /**
     * Listen to events from elements in the canvas.
     */
    CanvasHandler addListener(CanvasListener listener);

    /**
     * Return the canvas' unique identifier. 
     */
    String getUUID();

    /**
     * Return the working copy of the graph. 
     */
    Graph getGraph();
    
    /**
     * Get the shape canvas.
     */
    Canvas getCanvas();
    
    /**
     * Listen to events from elements in the canvas.
     */
    interface CanvasListener {

        void onElementAdded(Element element);
        
        void onElementModified(Element element);

        void onElementDeleted(Element element);
        
        void onClear();
        
    }
    
}
