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
package org.wirez.core.api.graph.commands;

import com.google.gwt.core.client.GWT;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.DefaultCommandResult;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultBound;
import org.wirez.core.api.graph.impl.DefaultBounds;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;

import java.util.ArrayList;

/**
 * A Command to update an element's bounds.
 */
public class UpdateElementPositionCommand extends AbstractCommand {

    private Element element;
    private Double x;
    private Double y;


    public UpdateElementPositionCommand(final GraphCommandFactory commandFactory,
                                        final Element element,
                                        final Double x,
                                        final Double y) {
        super(commandFactory);
        this.element = PortablePreconditions.checkNotNull( "element",
                element );;
        this.x = PortablePreconditions.checkNotNull( "x",
                x );
        this.y = PortablePreconditions.checkNotNull( "y",
                y );
    }
    
    @Override
    public CommandResult allow(final RuleManager ruleManager) {
        // TODO: Check bounds values.
        return new DefaultCommandResult(new ArrayList<RuleViolation>());
    }

    @Override
    public CommandResult execute(final RuleManager ruleManager) {
        final Bounds bounds = ((ViewContent) element.getContent()).getBounds();
        final Bounds.Bound ul = bounds.getUpperLeft();
        final Bounds.Bound lr = bounds.getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();

        GWT.log("Moving element bounds to [" + x + "," + y + "] [" + ( x + w ) + "," + ( y + h ) + "]");
        final DefaultBounds newBounds = new DefaultBounds(
                new DefaultBound(x + w, y + h),
                new DefaultBound(x, y)
        );
        ((ViewContent) element.getContent()).setBounds(newBounds);
        return new DefaultCommandResult(new ArrayList<RuleViolation>());
    }
    
    @Override
    public CommandResult undo(RuleManager ruleManager) {
        // TODO
        return new DefaultCommandResult(new ArrayList<RuleViolation>());
    }

    @Override
    public String toString() {
        return "UpdateElementPositionCommand [element=" + element.getUUID() + ", x=" + x + ", y=" + y + "]";
    }
    
}
