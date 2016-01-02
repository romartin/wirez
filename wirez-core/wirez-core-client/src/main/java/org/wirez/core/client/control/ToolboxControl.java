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

package org.wirez.core.client.control;

import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.shape.Node;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.client.Shape;
import org.wirez.core.client.control.toolbox.Toolbox;

public class ToolboxControl<S extends Shape, E extends ViewElement> extends BaseShapeControl<S, E> implements IsWidget {

    final Toolbox<E> toolbox;

    public ToolboxControl(Toolbox<E> toolbox) {
        this.toolbox = toolbox;
    }

    @Override
    public void enable(final S shape, final E element) {
        
        toolbox.initialize(canvasHandler);
        
        Node shapeNode = shape.getShapeNode();
        shapeNode.addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
            @Override
            public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent nodeMouseDoubleClickEvent) {
                final double[] xy = getContainerXY(shape);
                GWT.log("ToolboxControl - Showing toolbox");
                toolbox.show(element, xy[0], xy[1]);
            }
        });
        
    }
    
    @Override
    public void disable(final S shape) {
        
        toolbox.hide();
        
    }

    @Override
    public Widget asWidget() {
        return toolbox.asWidget();
    }
}
