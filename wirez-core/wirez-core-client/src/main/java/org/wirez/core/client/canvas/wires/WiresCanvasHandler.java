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

package org.wirez.core.client.canvas.wires;

import com.ait.lienzo.client.core.shape.wires.*;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.Default;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasControlsHandler;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.view.ShapeView;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class WiresCanvasHandler<D extends Diagram, C extends WiresCanvas> extends AbstractCanvasControlsHandler<D, C> {

    private static Logger LOGGER = Logger.getLogger(WiresCanvasHandler.class.getName());

    ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor;
    ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor;
    
    @Inject
    public WiresCanvasHandler(final ClientDefinitionManager clientDefinitionManager,
                              final ClientFactoryServices clientFactoryServices,
                              final @Default  RuleManager ruleManager,
                              final GraphCommandManager graphCommandManager,
                              final GraphCommandFactory graphCommandFactory,
                              final GraphUtils graphUtils,
                              final IncrementalIndexBuilder indexBuilder,
                              final CanvasCommandFactory commandFactory,
                              final TreeWalkTraverseProcessor treeWalkTraverseProcessor, 
                              final ShapeManager shapeManager,
                              final CanvasCommandManager<AbstractCanvasHandler> commandManager,
                              final ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor,
                              final ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor) {

        super( clientDefinitionManager, clientFactoryServices, ruleManager, graphCommandManager,
                graphCommandFactory, graphUtils, indexBuilder, commandFactory, treeWalkTraverseProcessor, 
                shapeManager, commandManager );

        this.connectionAcceptor = connectionAcceptor;
        this.containmentAcceptor = containmentAcceptor;
    }

    @Override
    protected void afterLoadRules() {
        super.afterLoadRules();
        final WiresCanvas.View canvasView = canvas.getView();
        canvasView.setConnectionAcceptor(CONNECTION_ACCEPTOR);
        canvasView.setContainmentAcceptor(CONTAINMENT_ACCEPTOR);
    }
    
    public ConnectionAcceptor<WiresCanvasHandler> getConnectionAcceptor() {
        return connectionAcceptor;
    }

    public ContainmentAcceptor<WiresCanvasHandler> getContainmentAcceptor() {
        return containmentAcceptor;
    }

    private final IConnectionAcceptor CONNECTION_ACCEPTOR = new IConnectionAcceptor() {

        // Set the source Node for the connector.
        @Override
        public boolean acceptHead(WiresConnection head, WiresMagnet magnet) {
            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape sourceShape = (Shape) magnet.getMagnets().getWiresShape();
            final Node sourceNode = getGraphIndex().getNode(sourceShape.getId());
            final Edge edge = getGraphIndex().getEdge(connector.getUUID());
            final String sourceUUID = sourceNode != null ? sourceNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);

            final String message = "Executed SetConnectionSourceNodeCommand [source=" + sourceUUID + ", magnet=" + mIndex +  "]";
            log(Level.FINE, message);

            return getConnectionAcceptor().acceptSource(WiresCanvasHandler.this, sourceNode, edge, mIndex);
        }

        // Set the target Node for the connector.
        @Override
        public boolean acceptTail(WiresConnection tail, WiresMagnet magnet) {
            WiresConnection head = tail.getConnector().getHeadConnection();
            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape targetShape = (Shape) magnet.getMagnets().getWiresShape();
            final Node targetNode = getGraphIndex().getNode(targetShape.getId());
            final Edge edge = getGraphIndex().getEdge(connector.getUUID());
            final String targetUUID = targetNode != null ? targetNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);

            final String message = "Executed SetConnectionTargetNodeCommand [target=" + targetUUID + ", magnet=" + mIndex +  "]";
            log(Level.FINE, message);

            return getConnectionAcceptor().acceptTarget(WiresCanvasHandler.this, targetNode, edge, mIndex);
        }

        @Override
        public boolean headConnectionAllowed(WiresConnection head, WiresShape shape) {
            WiresConnection tail = head.getConnector().getTailConnection();

            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape outNode = (Shape) shape;

            final Node sourceNode = getGraphIndex().getNode(outNode.getId());
            final Edge<View<?>, Node> edge = getGraphIndex().getEdge(connector.getUUID()); 
            
            return getConnectionAcceptor().allowSource(WiresCanvasHandler.this, sourceNode, edge, 0);
        }

        @Override
        public boolean tailConnectionAllowed(WiresConnection tail, WiresShape shape) {
            final ShapeView connector = (ShapeView) tail.getConnector();
            final Shape inNode = (Shape) shape;

            final Node targetNode = getGraphIndex().getNode(inNode.getId());
            final Edge<View<?>, Node> edge = getGraphIndex().getEdge(connector.getUUID());
            
            return getConnectionAcceptor().allowTarget(WiresCanvasHandler.this, targetNode, edge, 0);
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

            final ShapeView parent = (ShapeView) wiresContainer;
            final ShapeView child = (ShapeView) wiresShape;

            final Node childNode = getGraphIndex().getNode(child.getUUID());
            final Node parentNode = getGraphIndex().getNode(parent.getUUID());

            return getContainmentAcceptor().allow(WiresCanvasHandler.this, parentNode, childNode);
        }

        @Override
        public boolean acceptContainment(final WiresContainer wiresContainer, final WiresShape wiresShape) {

            final ShapeView parent = (ShapeView) wiresContainer;
            final ShapeView child = (ShapeView) wiresShape;

            final Node childNode = getGraphIndex().getNode(child.getUUID());
            final Node parentNode = getGraphIndex().getNode(parent.getUUID());
            
           return getContainmentAcceptor().accept(WiresCanvasHandler.this, parentNode, childNode);
        }

    };

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
