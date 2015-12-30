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
import org.wirez.core.client.canvas.impl.BaseCanvasHandler;

public class DefaultCanvasListener implements CanvasListener {
    
    CanvasHandler canvasHandler;

    public DefaultCanvasListener(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    @Override
    public void onElementAdded(final Element element) {
        assert canvasHandler != null;
    }

    @Override
    public void onElementModified(final Element element) {
        assert canvasHandler != null;
    }

    @Override
    public void onElementDeleted(final Element element) {
        assert canvasHandler != null;
    }

    @Override
    public void onClear() {
        assert canvasHandler != null;
    }

    public void removeListener() {
        assert canvasHandler != null;
        ((BaseCanvasHandler)canvasHandler).removeListener(this);
    }
}
