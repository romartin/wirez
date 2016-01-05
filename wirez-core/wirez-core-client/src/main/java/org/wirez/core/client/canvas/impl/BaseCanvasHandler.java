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

package org.wirez.core.client.canvas.impl;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.command.DefaultCommandManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.CanvasListener;
import org.wirez.core.client.canvas.CanvasSettings;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.control.*;
import org.wirez.core.client.control.toolbox.HasToolboxControl;
import org.wirez.core.client.event.ShapeStateModifiedEvent;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.factory.control.HasShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.mutation.*;
import org.wirez.core.client.notification.CanvasCommandAllowedNotification;
import org.wirez.core.client.notification.CanvasCommandExecutionNotification;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

// TODO: Implement SelectionManager<Element>
public abstract class BaseCanvasHandler implements CanvasHandler, CanvasCommandManager {

    protected Event<NotificationEvent> notificationEvent;
    protected DefaultCommandManager commandManager;
    protected DefaultRuleManager ruleManager;
    protected CanvasSettings settings;
    protected Canvas canvas;
    protected DefaultGraph<?, ? extends Node, ? extends Edge> graph;
    protected Collection<CanvasListener> listeners = new LinkedList<CanvasListener>();

    @Inject
    public BaseCanvasHandler(final Event<NotificationEvent> notificationEvent,
                             final DefaultCommandManager commandManager,
                             final DefaultRuleManager ruleManager) {
        this.notificationEvent = notificationEvent;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
    }

    @Override
    public CanvasHandler initialize(final CanvasSettings settings) {
        this.settings = settings;
        this.canvas = settings.getCanvas();
        this.graph = (DefaultGraph<? extends Definition, ? extends Node, ? extends Edge>) settings.getGraph();
        return this;
    }

    @Override
    public DefaultGraph<?, ? extends Node, ? extends Edge> getGraph() {
        return graph;
    }

    @Override
    public CanvasSettings getSettings() {
        return settings;
    }

    /*
        ***************************************************************************************
        * Shape/element handling
        ***************************************************************************************
     */

    public void register(final ShapeFactory factory, final Element candidate) {
        assert factory != null && candidate != null;
        
        final Object content = candidate.getContent();
        assert content instanceof ViewContent;
        
        final Definition wirez = ( (ViewContent) candidate.getContent()).getDefinition();
        final Shape shape = factory.build(wirez, this);

        shape.setId(candidate.getUUID());


        // Selection handling.
        if (canvas instanceof SelectionManager) {
            final SelectionManager<Shape> selectionManager = (SelectionManager<Shape>) canvas;
            shape.getShapeNode().addNodeMouseClickHandler(new NodeMouseClickHandler() {
                @Override
                public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {

                    if (!nodeMouseClickEvent.isShiftKeyDown()) {
                        selectionManager.clearSelection();
                    }

                    final boolean isSelected = selectionManager.isSelected(shape);
                    if (isSelected) {
                        GWT.log("Deselect [shape=" + shape.getId() + "]");
                        selectionManager.deselect(shape);
                    } else {
                        GWT.log("Select [shape=" + shape.getId() + "]");
                        selectionManager.select(shape);
                    }

                }
            });

        }

        // Shape controls.
        if (factory instanceof HasShapeControlFactories) {

            final Collection<ShapeControlFactory<?, ?>> factories = ((HasShapeControlFactories) factory).getFactories();
            for (ShapeControlFactory controlFactory : factories) {
                ShapeControl control = controlFactory.build(shape);

                // Some controls needs to add elements on the DOM.
                if (control instanceof IsWidget) {
                    final IsWidget controlWidget = (IsWidget) control;
                    canvas.addControl(controlWidget);
                }
                
                // DRAG control.
                if (control instanceof DefaultDragControl && shape instanceof HasDragControl) {
                    final HasDragControl hasDragControl = (HasDragControl) shape;
                    hasDragControl.setDragControl((DefaultDragControl) control);
                    ((DefaultDragControl) control).setCanvasHandler(this);
                    control.enable(shape, candidate);
                }

                // RESIZE control.
                if (control instanceof DefaultResizeControl && shape instanceof HasResizeControl) {
                    final HasResizeControl hasResizeControl = (HasResizeControl) shape;
                    hasResizeControl.setResizeControl((DefaultResizeControl) control);
                    ((DefaultResizeControl) control).setCanvasHandler(this);
                    control.enable(shape, candidate);
                }
                
                // Toolbox.
                if (control instanceof ToolboxControl && shape instanceof HasToolboxControl) {
                    final HasToolboxControl hasToolboxControl = (HasToolboxControl) shape;
                    hasToolboxControl.setToolboxControl((ToolboxControl) control);
                    ((ToolboxControl) control).setCanvasHandler(this);
                    control.enable(shape, candidate);
                }
            }

        }

        // Add the shapes on canvas and fire events.
        canvas.addShape(shape);
        canvas.draw();
        fireElementAdded(candidate);
    }

    public void applyElementMutation(final Element candidate) {
        final Shape shape = canvas.getShape(candidate.getUUID());
        if (shape instanceof HasMutation) {

            final HasMutation hasMutation = (HasMutation) shape;

            if (hasMutation.accepts(MutationType.STATIC)) {

                MutationContext context = new StaticMutationContext();

                if (shape instanceof HasGraphElementMutation) {
                    final HasGraphElementMutation hasGraphElement = (HasGraphElementMutation) shape;
                    hasGraphElement.applyElementPosition(candidate, this, context);
                    hasGraphElement.applyElementSize(candidate, this, context);
                    hasGraphElement.applyElementProperties(candidate, this, context);
                }

            }

        }
    }
    
    public void addChild(final Element parent, final Element child) {
        assert parent != null && child != null;

        final BaseShape parentShape = (BaseShape) canvas.getShape(parent.getUUID());
        final BaseShape childShape = (BaseShape) canvas.getShape(child.getUUID());
        parentShape.add(childShape);
        
    }

    public void updateElementSize(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());

        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        final MutationContext context = new StaticMutationContext();
        shapeMutation.applyElementSize(element, this, context);
        canvas.draw();
        fireElementUpdated(element);
    }

    public void updateElementPosition(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());

        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        final MutationContext context = new StaticMutationContext();
        shapeMutation.applyElementPosition(element, this, context);
        canvas.draw();
        fireElementUpdated(element);
    }

    public void updateElementProperties(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());

        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        final MutationContext context = new StaticMutationContext();
        shapeMutation.applyElementProperties(element, this, context);
        canvas.draw();
        fireElementUpdated(element);
    }

    public void deregister(final Element element) {

        final Shape shape = canvas.getShape(element.getUUID());
        // TODO: Delete connector connections to the node being deleted?
        canvas.deleteShape(shape);
        canvas.draw();
        fireElementDeleted(element);

    }
    
    public void clear() {
        canvas.clear();
        canvas.draw();
        fireCanvasClear();
    }
    
    /*
        ***************************************************************************************
        * Listeners handling
        ***************************************************************************************
     */

    @Override
    public CanvasHandler addListener(final CanvasListener listener) {
        assert listener != null;
        listeners.add(listener);
        return this;
    }

    public void fireElementAdded(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementAdded(element);
        }
    }

    public void fireElementDeleted(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementDeleted(element);
        }
    }

    public void fireElementUpdated(final Element element) {
        for (final CanvasListener listener : listeners) {
            listener.onElementModified(element);
        }
    }

    public void fireCanvasClear() {
        for (final CanvasListener listener : listeners) {
            listener.onClear();
        }
    }
    
    public void removeListener(final CanvasListener listener) {
        listeners.remove(listener);
    }
    
    /*
        ***************************************************************************************
        * Command handling
        ***************************************************************************************
     */

    @Override
    public boolean allow(final CanvasCommand command) {
        return this.allow(ruleManager, command);
    }

    @Override
    public CommandResults execute(final CanvasCommand... commands) {
        return this.execute(ruleManager, commands);
    }

    @Override
    public CommandResults undo() {
        return this.undo(ruleManager);
    }

    @Override
    public boolean allow(final RuleManager ruleManager,
                         final CanvasCommand command) {
        command.setCanvas(this);
        boolean isAllowed = commandManager.allow(ruleManager, command);
        final CanvasCommandAllowedNotification notification = new CanvasCommandAllowedNotification(getSettings().getTitle(), command, isAllowed);
        notificationEvent.fire(new NotificationEvent(notification));
        return isAllowed;
    }

    @Override
    public CommandResults execute(final RuleManager ruleManager,
                                  final CanvasCommand... commands) {

        // TODO: Join results.
        CommandResults results = null;
        for (final CanvasCommand command : commands) {
            command.setCanvas(this);
            results = commandManager.execute(ruleManager, command);
            // TODO: Check errors.
            command.apply();
            final CanvasCommandExecutionNotification notification = new CanvasCommandExecutionNotification(getSettings().getTitle(), command, results);
            notificationEvent.fire(new NotificationEvent(notification));
        }

        return results;
    }

    @Override
    public CommandResults undo(final RuleManager ruleManager) {
        return commandManager.undo(ruleManager);
    }

    protected BaseCanvas getBaseCanvas() {
        return (BaseCanvas) canvas;
    }

    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final Canvas.ShapeState state = event.getState();
        final Shape shape = event.getShape();
        if ( null == shape ) {
            fireCanvasClear();
        }
    }
}
