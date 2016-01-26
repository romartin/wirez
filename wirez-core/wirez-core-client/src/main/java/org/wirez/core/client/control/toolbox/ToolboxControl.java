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

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.lienzo.toolbox.HoverToolbox;
import org.wirez.lienzo.toolbox.HoverToolboxButton;

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

        if (shape instanceof WiresShape) {

            WiresShape wiresShape = (WiresShape) shape;

            SVGPath icon = new SVGPath("M501.467 408.938l-230.276-197.38c10.724-20.149 16.809-43.141 16.809-67.558 0-79.529-64.471-144-144-144-14.547 0-28.586 2.166-41.823 6.177l83.195 83.195c12.445 12.445 12.445 32.81 0 45.255l-50.745 50.745c-12.445 12.445-32.81 12.445-45.255 0l-83.195-83.195c-4.011 13.237-6.177 27.276-6.177 41.823 0 79.529 64.471 144 144 144 24.417 0 47.409-6.085 67.558-16.81l197.38 230.276c11.454 13.362 31.008 14.113 43.452 1.669l50.746-50.746c12.444-12.444 11.693-31.997-1.669-43.451z");
            icon.setFillColor("#000000");
            icon.setStrokeWidth(1);

            HoverToolbox.toolboxFor(wiresShape)
                    .on(Direction.NORTH_EAST)
                    .towards(Direction.SOUTH)
                    .add(new HoverToolboxButton(icon.copy()))
                    .add(new HoverToolboxButton(icon.copy(), new NodeMouseClickHandler() {
                        @Override
                        public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
                            GWT.log("clicked!");
                        }
                    }))
                    .add(new HoverToolboxButton(icon.copy()))
                    .add(new HoverToolboxButton(icon.copy()))
                    .register();
            
        }

        
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
