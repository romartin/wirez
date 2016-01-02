/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.control.toolbox;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.CanvasListener;
import org.wirez.core.client.canvas.DefaultCanvasListener;

public abstract class BaseToolbox<E extends ViewElement> implements Toolbox<E> {

    protected CanvasHandler canvasHandler;
    
    @Override
    public void initialize(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        this.canvasHandler.addListener(canvasListener);
        
    }

    protected final CanvasListener canvasListener = new DefaultCanvasListener(canvasHandler) {

        @Override
        public void onElementAdded(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onElementModified(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onElementDeleted(final Element element) {
            BaseToolbox.this.hide();
        }

        @Override
        public void onClear() {
            BaseToolbox.this.hide();
        }
        
    };
    
}
