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
import com.ait.lienzo.client.core.shape.wires.*;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.command.DefaultCommandManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.DefaultEdge;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.DefaultNode;
import org.wirez.core.api.graph.processing.GraphVisitor;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.WirezClientManager;
import org.wirez.core.client.canvas.*;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.mutation.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

// TODO: Implement SelectionManager<Element>
@ApplicationScoped
public class DefaultCanvasHandler implements CanvasHandler, CanvasCommandManager {

    Event<NotificationEvent> notificationEvent;
    WirezClientManager wirezClientManager;
    DefaultCanvasCommands defaultCanvasCommands;
    DefaultCommandManager commandManager;
    DefaultRuleManager ruleManager;
    CanvasSettings settings;
    Canvas canvas;
    DefaultGraph<? extends Definition, DefaultNode, DefaultEdge> graph;
    Collection<CanvasListener> listeners = new LinkedList<CanvasListener>();
    
    @Inject
    public DefaultCanvasHandler(final WirezClientManager wirezClientManager,
                                final Event<NotificationEvent> notificationEvent, 
                                final DefaultCommandManager commandManager, 
                                final DefaultRuleManager ruleManager,
                                final DefaultCanvasCommands defaultCanvasCommands) {
        this.notificationEvent = notificationEvent;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
        this.wirezClientManager = wirezClientManager;
        this.defaultCanvasCommands = defaultCanvasCommands;
    }

    @Override
    public Graph<? extends Definition, ? extends Node> getGraph() {
        return graph;
    }

    @Override
    public CanvasSettings getSettings() {
        return settings;
    }

/*
        *********************************************************
        * Initialization & rules
        *********************************************************
     */
    
    
    @Override
    public CanvasHandler initialize(CanvasSettings settings) {
        this.settings = settings;
        this.canvas = settings.getCanvas();
        this.graph = (DefaultGraph<? extends Definition, DefaultNode, DefaultEdge>) settings.getGraph();

        // Load the rules to apply for this graph.
        loadRules(settings.getDefinitionSet());

        // Build the shapes that represents the graph on canvas.
        drawGraph();

        // Draw it.
        canvas.draw();
        
        return this;
    }

    private void loadRules(final DefinitionSet definitionSet) {
        Collection<Rule> rules = definitionSet.getRules();
        ruleManager.clearRules();
        if (rules != null) {
            for (final Rule rule : rules) {
                ruleManager.addRule(rule);
            }
        }

        final WiresManager wiresManager = getBaseCanvas().getWiresManager();
        wiresManager.setConnectionAcceptor(CONNECTION_ACCEPTOR);
        wiresManager.setContainmentAcceptor(CONTAINTMENT_ACCEPTOR);
    }
    
    /*
        ***************************************************************************************
        * Graph management
        ***************************************************************************************
     */

    private void drawGraph() {
        new GraphVisitor(graph, new GraphVisitor.Visitor() {
            @Override
            public void visitGraph(final DefaultGraph graph) {

            }

            @Override
            public void visitNode(final DefaultNode node) {
                // Apply add node on this canvas, but do not execute it as node it's already present in the graph.
                final ShapeFactory factory = wirezClientManager.getFactory(node.getDefinition());
                defaultCanvasCommands.ADD_NODE(node, factory)
                        .setCanvas(DefaultCanvasHandler.this)
                        .apply();
            }

            @Override
            public void visitEdge(final DefaultEdge edge) {
                // TODO
                /*final ShapeFactory factory = wirezClientManager.getFactory(edge.getDefinition());
                defaultCanvasCommands.ADD_EDGE( edge, factory )
                        .setCanvas(DefaultCanvasHandler.this)
                        .apply();*/
            }

            @Override
            public void visitUnconnectedEdge(DefaultEdge edge) {
                // TODO
                /*final ShapeFactory factory = wirezClientManager.getFactory(edge.getDefinition());
                defaultCanvasCommands.ADD_EDGE(edge, factory).setCanvas(DefaultCanvasHandler.this).apply();*/
            }

            @Override
            public void end() {

            }

            // Shapes for edges usually requires both nodes created, so let's build the shape after node's shapes exists (EDGE_LAST visitor policy).
        }, GraphVisitor.VisitorPolicy.EDGE_LAST).run();
    }
    
    /*
        ***************************************************************************************
        * Shape/element handling
        ***************************************************************************************
     */

    public CanvasHandler register(final ShapeFactory factory, final Element candidate) {
        assert factory != null && candidate != null;

        final Definition wirez = candidate.getDefinition();
        final Shape shape = factory.build(wirez, this);

        shape.setId(candidate.getUUID());


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

        // TODO: Shape controls
        /*if (factory instanceof HasShapeControlFactories) {

            final Collection<ShapeControlFactory<?, ?>> factories = ((HasShapeControlFactories) factory).getFactories();
            for (ShapeControlFactory controlFactory : factories) {
                ShapeControl control = controlFactory.build(shape);

                // DRAG handling..
                if (control instanceof DefaultDragControl && shape instanceof HasDragControl) {
                    final HasDragControl hasDragControl = (HasDragControl) shape;
                    hasDragControl.setDragControl((DefaultDragControl) control);
                    ((DefaultDragControl) control).setCommandManager(this);
                    control.enable(shape, candidate);
                }

                // RESIZE handling.
                if (control instanceof DefaultResizeControl && shape instanceof HasResizeControl) {
                    final HasResizeControl hasResizeControl = (HasResizeControl) shape;
                    hasResizeControl.setResizeControl((DefaultResizeControl) control);
                    ((DefaultResizeControl) control).setCommandManager(this);
                    control.enable(shape, candidate);
                }
            }

        }*/

        // TODO: Contextual menu.
        /*if (canvas instanceof HasContextualMenu) {
            final ContextualMenu<Element> contextualMenu = ((HasContextualMenu) canvas).getContextualMenu();
            getContainer().addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
                @Override
                public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent nodeMouseDoubleClickEvent) {
                    final double mx = nodeMouseDoubleClickEvent.getX();
                    final double my = nodeMouseDoubleClickEvent.getY();
                    GWT.log("Double click for " + candidate.getId() + " at [mx=" + mx + ", my=" + my + "]");
                    contextualMenu.show(candidate, null, mx, my);
                }
            });

        }*/

        // Add the shapes on canvas and fire events.
        canvas.addShape(shape);
        canvas.draw();
        fireElementAdded(candidate);
        
        return this;
    }

    public CanvasHandler deregister(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());
        // TODO: Delete connector connections to the node being deleted?
        canvas.deleteShape(shape);
        canvas.draw();
        fireElementDeleted(element);
        
        return this;
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
        return commandManager.allow(ruleManager, command);
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
    
    /*
        ***************************************************************************************
        * Rules for connections and containtment acceptors.
        ***************************************************************************************
     */

    private final IConnectionAcceptor CONNECTION_ACCEPTOR = new IConnectionAcceptor() {

        // Set the source Node for the connector.
        @Override
        public boolean acceptHead(WiresConnection head, WiresMagnet magnet) {
            return true;
        }

        // Set the target Node for the connector.
        @Override
        public boolean acceptTail(WiresConnection tail, WiresMagnet magnet) {
            return true;
        }

        @Override
        public boolean headConnectionAllowed(WiresConnection head, WiresShape shape) {
            return true;
        }

        @Override
        public boolean tailConnectionAllowed(WiresConnection tail, WiresShape shape) {
            return true;
        }

    };

    // TODO
    private final IContainmentAcceptor CONTAINTMENT_ACCEPTOR = new IContainmentAcceptor() {
        @Override
        public boolean containmentAllowed(WiresContainer wiresContainer, WiresShape wiresShape) {
            return false;
        }

        @Override
        public boolean acceptContainment(WiresContainer wiresContainer, WiresShape wiresShape) {
            return false;
        }
    };
}

