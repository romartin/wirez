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

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.util.ShapeUtils;

import javax.inject.Inject;

public abstract class BaseShapeControl<S extends Shape, E extends Element> implements ShapeControl<AbstractCanvasHandler<?, ?, ?>, S, E> {

    protected CanvasCommandFactory commandFactory;

    protected AbstractCanvasHandler<?, ?, ?> canvasHandler;

    @Inject
    public BaseShapeControl(final CanvasCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void enable(final AbstractCanvasHandler<?, ?, ?> canvasHandler, 
                       final S shape, final E element) {
        this.canvasHandler = canvasHandler;
        doEnable(shape, element);
    }

    @Override
    public void disable(final AbstractCanvasHandler<?, ?, ?> canvasHandler, 
                        final S shape) {
        doDisable(shape);
    }

    protected void doEnable(final S shape, final E element) {
    }

    protected void doDisable(final S shape) {
    }

    protected double[] getContainerXY(final Shape shape) {
        return ShapeUtils.getContainerXY(shape);
    }
    
    protected CommandResult<CanvasViolation> execute (final Command<AbstractCanvasHandler, CanvasViolation> command) {
        return canvasHandler.execute( command );
    }
    
}
