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

import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.shape.Node;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.BaseShapeControl;
import org.wirez.core.client.control.toolbox.Toolbox;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ToolboxControl extends BaseToolboxControl<Shape, Element> implements IsWidget {

    final Toolbox<Element> toolbox;

    @Inject
    public ToolboxControl(DefaultCanvasCommands defaultCanvasCommands, Toolbox<Element> toolbox) {
        super(defaultCanvasCommands);
        this.toolbox = toolbox;
    }

    @Override
    public void enable(final Shape shape, final Element element) {
        
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
    public void disable(final Shape shape) {
        
        toolbox.hide();
        
    }

    @Override
    public Widget asWidget() {
        return toolbox.asWidget();
    }
}
