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
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

// TODO: Implement SelectionManager<Element>
@Dependent
public class DefaultCanvasHandler extends BaseCanvasHandler {

    WirezClientManager wirezClientManager;
    DefaultCanvasCommands defaultCanvasCommands;
    CanvasSettings settings;
    
    @Inject
    public DefaultCanvasHandler(final WirezClientManager wirezClientManager,
                                final Event<NotificationEvent> notificationEvent, 
                                final DefaultCommandManager commandManager, 
                                final DefaultRuleManager ruleManager,
                                final DefaultCanvasCommands defaultCanvasCommands) {
        super(notificationEvent, commandManager, ruleManager);
        this.wirezClientManager = wirezClientManager;
        this.defaultCanvasCommands = defaultCanvasCommands;
    }

/*
        *********************************************************
        * Initialization & rules
        *********************************************************
     */
    
    @Override
    public CanvasHandler initialize(final CanvasSettings settings) {
        super.initialize(settings);

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

