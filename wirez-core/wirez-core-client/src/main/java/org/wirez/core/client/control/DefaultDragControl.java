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

package org.wirez.core.client.control;

import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandUtils;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.event.ViewEventType;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultDragControl extends BaseDragControl<Shape, Element>  {

    private org.wirez.core.client.view.event.DragHandler handler;
    
    @Inject
    public DefaultDragControl(final CanvasCommandFactory canvasCommandFactory) {
        super( canvasCommandFactory );
    }

    @Override
    public void doEnable(final Shape shape, final Element element) {
        
        if ( shape.getShapeView() instanceof HasEventHandlers ) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            
            this.handler = new org.wirez.core.client.view.event.DragHandler() {

                @Override
                public void handle(final org.wirez.core.client.view.event.DragEvent event) {

                }

                @Override
                public void start(final org.wirez.core.client.view.event.DragEvent event) {

                }

                @Override
                public void end(final org.wirez.core.client.view.event.DragEvent event) {
                    final double[] xy = getContainerXY(shape);
                    CommandResult<CanvasViolation> result = execute( commandFactory.UPDATE_POSITION(element, xy[0], xy[1]) );
                    if (CommandUtils.isError( result) ) {
                        // TODO: DragContext#reset
                    }
                }
            };
            
            hasEventHandlers.addHandler(ViewEventType.DRAG, handler);
            
        }
        
    }

    protected double[] getContainerXY(final Shape shape) {
        return new double[] { shape.getShapeView().getShapeX(),
                shape.getShapeView().getShapeY()};
    }
    
    @Override
    public void doDisable(final Shape shape) {

        if ( null != this.handler ) {
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shape.getShapeView();
            hasEventHandlers.removeHandler(this.handler);
        }
        
    }

}
