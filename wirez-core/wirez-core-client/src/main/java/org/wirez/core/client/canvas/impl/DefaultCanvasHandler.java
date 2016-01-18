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

import com.ait.lienzo.client.core.shape.wires.*;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.command.DefaultCommandManager;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.defaultset.ConnectionSourceMagnetBuilder;
import org.wirez.core.api.definition.property.defaultset.ConnectionTargetMagnetBuilder;
import org.wirez.core.api.event.NotificationEvent;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.commands.AddChildNodeCommand;
import org.wirez.core.api.graph.commands.SetConnectionSourceNodeCommand;
import org.wirez.core.api.graph.commands.SetConnectionTargetNodeCommand;
import org.wirez.core.api.graph.commands.UpdateElementPropertyValueCommand;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.processing.handler.DefaultGraphHandler;
import org.wirez.core.api.graph.processing.visitor.AbstractGraphVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.DefaultGraphVisitor;
import org.wirez.core.api.graph.processing.visitor.GraphVisitor;
import org.wirez.core.api.rule.DefaultRuleManager;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.util.Logger;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.CanvasSettings;
import org.wirez.core.client.canvas.command.BaseCanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.impl.CompositeElementCanvasCommand;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.impl.BaseShape;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;

// TODO: Implement SelectionManager<Element>
@Dependent
public class DefaultCanvasHandler extends BaseCanvasHandler {

    ShapeManager wirezClientManager;
    DefaultCanvasCommands defaultCanvasCommands;
    DefaultGraphHandler defaultGraphHandler;
    DefaultGraphVisitor defaultGraphVisitor;
    
    @Inject
    public DefaultCanvasHandler(final ShapeManager wirezClientManager,
                                final Event<NotificationEvent> notificationEvent, 
                                final DefaultCommandManager commandManager, 
                                final DefaultRuleManager ruleManager,
                                final DefaultCanvasCommands defaultCanvasCommands,
                                final DefaultGraphHandler defaultGraphHandler,
                                final DefaultGraphVisitor defaultGraphVisitor) {
        super(notificationEvent, commandManager, ruleManager);
        this.wirezClientManager = wirezClientManager;
        this.defaultCanvasCommands = defaultCanvasCommands;
        this.defaultGraphHandler = defaultGraphHandler;
        this.defaultGraphVisitor = defaultGraphVisitor;
    }

/*
        *********************************************************
        * Initialization & rules
        *********************************************************
     */
    
    @Override
    public CanvasHandler initialize(final CanvasSettings settings) {
        super.initialize(settings);

        // Initialize the graph handler that provides processing and querying operations over the graph.
        defaultGraphHandler.initialize((DefaultGraph) settings.getGraph());

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
        wiresManager.setContainmentAcceptor(CONTAINMENT_ACCEPTOR);
    }
    
    /*
        ***************************************************************************************
        * Graph view representation using the graph visitor processor.
        ***************************************************************************************
     */

    private final AbstractGraphVisitorCallback DRAW_VISITOR_CALLBACK = new AbstractGraphVisitorCallback() {

        @Override
        public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {
            final ShapeFactory factory = wirezClientManager.getFactory(node.getContent().getDefinition());
            defaultCanvasCommands.ADD_NODE(node, factory)
                    .setCanvas(DefaultCanvasHandler.this)
                    .apply();
        }

        @Override
        public void visitEdgeWithViewContent(Edge<? extends ViewContent, ?> edge) {
            final ShapeFactory factory = wirezClientManager.getFactory(edge.getContent().getDefinition());
            defaultCanvasCommands.ADD_EDGE( edge, factory )
                    .setCanvas(DefaultCanvasHandler.this)
                    .apply();
        }

        @Override
        public void visitEdgeWithParentChildRelationContent(Edge<ParentChildRelationship, ?> edge) {
            final Node child = edge.getTargetNode();
            final Node parent = edge.getSourceNode();
            final Object content = child.getContent();
            if (content instanceof ViewContent) {
                final ViewContent viewContent = (ViewContent) content;
                final ShapeFactory factory = wirezClientManager.getFactory(viewContent.getDefinition());
                defaultCanvasCommands.ADD_CHILD( parent, child, factory )
                        .setCanvas(DefaultCanvasHandler.this)
                        .apply();
            }
        }

        @Override
        public void visitNode(final Node node) {
            
        }

        @Override
        public void visitEdge(final Edge edge) {
            
        }

    };
    
    private void drawGraph() {
        defaultGraphVisitor.visit(graph, DRAW_VISITOR_CALLBACK, GraphVisitor.GraphVisitorPolicy.EDGE_LAST);
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
            final BaseConnector connector = (BaseConnector) head.getConnector();
            final BaseShape sourceShape = (BaseShape) magnet.getMagnets().getWiresShape();
            final Node sourceNode = (Node) defaultGraphHandler.getNode(sourceShape.getId());
            final Edge edge = (Edge) defaultGraphHandler.getEdge(connector.getId());
            final String sourceUUID = sourceNode != null ? sourceNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);

            final String message = "Executed SetConnectionSourceNodeCommand [source=" + sourceUUID + ", magnet=" + mIndex +  "]";
            Logger.log(message);

            CommandResults results = execute(new CompositeElementCanvasCommand(edge)
                .add(new UpdateElementPropertyValueCommand(edge, ConnectionSourceMagnetBuilder.PROPERTY_ID, mIndex))
                .add(new SetConnectionSourceNodeCommand(sourceNode, edge))
            );

            final boolean isAccept = isAccept(results);
            return isAccept;
        }

        // Set the target Node for the connector.
        @Override
        public boolean acceptTail(WiresConnection tail, WiresMagnet magnet) {
            WiresConnection head = tail.getConnector().getHeadConnection();
            final BaseConnector connector = (BaseConnector) head.getConnector();
            final BaseShape targetShape = (BaseShape) magnet.getMagnets().getWiresShape();
            final Node targetNode = (Node) defaultGraphHandler.getNode(targetShape.getId());
            final Edge edge = (Edge) defaultGraphHandler.getEdge(connector.getId());
            final String targetUUID = targetNode != null ? targetNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);
            
            final String message = "Executed SetConnectionTargetNodeCommand [target=" + targetUUID + ", magnet=" + mIndex +  "]";
            Logger.log(message);

            CommandResults results = execute(new CompositeElementCanvasCommand(edge)
                .add(new UpdateElementPropertyValueCommand(edge, ConnectionTargetMagnetBuilder.PROPERTY_ID, mIndex))
                .add(new SetConnectionTargetNodeCommand(targetNode, edge))
            );
            
            final boolean isAccept = isAccept(results);
            GWT.log("ConnectionAccepted=" + isAccept);
            return isAccept;
        }

        @Override
        public boolean headConnectionAllowed(WiresConnection head, WiresShape shape) {
            WiresConnection tail = head.getConnector().getTailConnection();
            WiresMagnet m = tail.getMagnet();

            final BaseConnector connector = (BaseConnector) tail.getConnector();
            final BaseShape outNode = (BaseShape) shape;
            final BaseShape inNode = tail.getMagnet() != null ? (BaseShape) tail.getMagnet().getMagnets().getWiresShape() : null;

            final boolean isAllowed = allow(new BaseCanvasCommand() {
                @Override
                protected Command getCommand() {
                    return new SetConnectionSourceNodeCommand(defaultGraphHandler.getNode(outNode.getId()), defaultGraphHandler.getEdge(connector.getId()));
                }

                @Override
                public CanvasCommand apply() {
                    // Do nothing, lienzo wires do it for us.
                    return this;
                }

                @Override
                public String toString() {
                    return getCommand().toString();
                }

            });
            final String outUUID = outNode != null ? outNode.getId() : null;
            final String inUUID = inNode != null ? inNode.getId() : null;
            final String message = "HeadConnectionAllowed  [out=" + outUUID + "] [in=" + inUUID + "] [isAllowed=" + isAllowed + "]";
            Logger.log(message);
            return isAllowed;
        }

        @Override
        public boolean tailConnectionAllowed(WiresConnection tail, WiresShape shape) {
            WiresConnection head = tail.getConnector().getHeadConnection();

            final BaseConnector connector = (BaseConnector) tail.getConnector();
            final BaseShape inNode = (BaseShape) shape;
            final BaseShape outNode = head.getMagnet() != null ? (BaseShape) head.getMagnet().getMagnets().getWiresShape() : null;

            final boolean isAllowed = allow(new BaseCanvasCommand() {
                @Override
                protected Command getCommand() {
                    return new SetConnectionTargetNodeCommand(defaultGraphHandler.getNode(inNode.getId()), defaultGraphHandler.getEdge(connector.getId()));
                }

                @Override
                public CanvasCommand apply() {
                    // Do nothing, lienzo wires do it for us.
                    return this;
                }

                @Override
                public String toString() {
                    return getCommand().toString();
                }

            });
            final String outUUID = outNode != null ? outNode.getId() : null;
            final String inUUID = inNode != null ? inNode.getId() : null;
            final String message = "TailConnectionAllowed  [out=" + outUUID + "] [in=" + inUUID + "] [isAllowed=" + isAllowed + "]";
            Logger.log(message);
            GWT.log("ConnectionAllowed=" + isAllowed);

            return isAllowed;
        }
        
        private int getMagnetIndex(final WiresMagnet magnet) {
            if ( null != magnet ) {
                MagnetManager.Magnets magnets = magnet.getMagnets();
                for (int x = 0; x < magnets.size(); x++) {
                    WiresMagnet _m = magnets.getMagnet(x);
                    if ( _m.equals(magnet) ) {
                        return x;
                    }
                }
            }
            return -1;
        }

    };

    private final IContainmentAcceptor CONTAINMENT_ACCEPTOR = new IContainmentAcceptor() {
        @Override
        public boolean containmentAllowed(final WiresContainer wiresContainer, final WiresShape wiresShape) {

            final BaseShape parent = (BaseShape) wiresContainer;
            final BaseShape child = (BaseShape) wiresShape;
            final Node parentNode = defaultGraphHandler.getNode(parent.getId());
            final Node childNode = defaultGraphHandler.getNode(child.getId());

            final boolean isAllowed = allow(new BaseCanvasCommand() {
                @Override
                protected Command getCommand() {
                    return  new AddChildNodeCommand(graph, parentNode, childNode);
                }

                @Override
                public CanvasCommand apply() {
                    // Do nothing, lienzo wires do it for us.
                    return this;
                }

                @Override
                public String toString() {
                    return getCommand().toString();
                }

            });
            final String parentUUID = parent != null ? parent.getId() : null;
            final String childUUID = child != null ? child.getId() : null;
            Logger.log("containmentAllowed  [parent=" + parentUUID + "] [child=" + childUUID + "] [isAllowed=" + isAllowed + "]");
            Logger.log("containmentAllowed#Result=" + isAllowed);
            return isAllowed;
            
        }

        @Override
        public boolean acceptContainment(final WiresContainer wiresContainer, final WiresShape wiresShape) {

            final BaseShape parent = (BaseShape) wiresContainer;
            final BaseShape child = (BaseShape) wiresShape;
            final Node parentNode = defaultGraphHandler.getNode(parent.getId());
            final Node childNode = defaultGraphHandler.getNode(child.getId());

            final String message = "Executed AddChildNodeCommand [parent=" + parentNode.getUUID() + ", child=" + childNode.getUUID() + "]";
            GWT.log(message);
            
            CommandResults results = execute(new BaseCanvasCommand() {
                @Override
                protected Command getCommand() {
                    return new AddChildNodeCommand(graph, parentNode, childNode);
                }

                @Override
                public CanvasCommand apply() {
                    // Do nothing, lienzo wires do it for us.
                    return this;
                }

                @Override
                public String toString() {
                    return getCommand().toString();
                }

            });

            final boolean isAccept = isAccept(results);
            Logger.log("acceptContainment#Result=" + isAccept);
            return isAccept;

        }
    };
    
    private boolean isAccept(final CommandResults results) {
        Logger.logCommandResults(results.results());
        final boolean hasCommandErrors = results.results(CommandResult.Type.ERROR) != null
                && results.results(CommandResult.Type.ERROR).iterator().hasNext();
        return !hasCommandErrors;
    }
}

