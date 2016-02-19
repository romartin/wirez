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
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.graph.processing.visitor.AbstractContentVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.listener.CanvasListener;
import org.wirez.core.client.canvas.settings.CanvasSettings;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractWiresCanvasHandler<S extends CanvasSettings, L extends CanvasListener> implements CanvasHandler<WiresCanvas, S, L> {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.canvas.impl.AbstractWiresCanvasHandler");

    protected ShapeManager shapeManager;
    protected CanvasCommandFactory commandFactory;
    protected WiresCanvas canvas;
    protected Diagram<?> diagram;
    protected S settings;
    protected Index<?, ?> graphIndex;
    protected Collection<L> listeners = new LinkedList<L>();

    @Inject
    public AbstractWiresCanvasHandler(final ShapeManager shapeManager,
                                      final CanvasCommandFactory commandFactory) {
        this.shapeManager = shapeManager;
        this.commandFactory = commandFactory;
    }

    @Override
    public AbstractWiresCanvasHandler initialize(WiresCanvas canvas, Diagram<?> diagram, S settings) {
        this.settings = settings;
        this.canvas = canvas;
        this.diagram = diagram;

        // Initialize the graph handler that provides processing and querying operations over the graph.
        
        this.graphIndex = getIndexBuilder().build(diagram.getGraph());
        
        doInitialize();

        return this;
    }
    
    protected void doInitialize() {
        doAfterInitialize();
    }
    
    protected void doAfterInitialize() {
        // Build the shapes that represents the graph on canvas.
        draw();
        // Draw it.
        canvas.draw();
    }

    @Override
    public WiresCanvas getCanvas() {
        return canvas;
    }

    @Override
    public Diagram<?> getDiagram() {
        return diagram;
    }

    protected void draw() {
        settings.getVisitor().visit(diagram.getGraph(), DRAW_VISITOR_CALLBACK, VisitorPolicy.VISIT_EDGE_AFTER_TARGET_NODE);
    }

    private final AbstractContentVisitorCallback DRAW_VISITOR_CALLBACK = new AbstractContentVisitorCallback() {

        @Override
        public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {
            final ShapeFactory factory = shapeManager.getFactory(node.getContent().getDefinition());

            // Add the node shape into the canvas.
            register(factory, node);
            applyElementMutation(node);
           
        }

        @Override
        public void visitEdgeWithViewContent(Edge<? extends ViewContent, ?> edge) {
            final ShapeFactory factory = shapeManager.getFactory(edge.getContent().getDefinition());

            // Add the edge shape into the canvas.
            register(factory, edge);
            applyElementMutation(edge);
            final String uuid = edge.getUUID();
            BaseConnector connector = (BaseConnector) getCanvas().getShape(uuid);
            connector.applyConnections(edge, AbstractWiresCanvasHandler.this);
        }

        @Override
        public void visitEdgeWithParentChildRelationContent(Edge<ParentChildRelationship, ?> edge) {
            final Node child = edge.getTargetNode();
            final Node parent = edge.getSourceNode();
            final Object content = child.getContent();
            if (content instanceof ViewContent) {
                final ViewContent viewContent = (ViewContent) content;
                final ShapeFactory factory = shapeManager.getFactory(viewContent.getDefinition());
                addChild(parent, child);
                applyElementMutation(child);
            }
        }

        @Override
        public void visitNode(final Node node) {

        }

        @Override
        public void visitEdge(final Edge edge) {

        }

    };
    
     /*
        ***************************************************************************************
        * Listeners handling
        ***************************************************************************************
     */

    @Override
    public AbstractWiresCanvasHandler addListener(final L listener) {
        assert listener != null;
        listeners.add( listener );
        return this;
    }

    public void fireCanvasInitialized() {
        for (final L listener : listeners) {
            listener.onInitializationComplete();
        }
    }

    public void fireCanvasClear() {
        for (final L listener : listeners) {
            listener.onClear();
        }
    }

    public void removeListener(final L listener) {
        listeners.remove(listener);
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

        // Set the same identifier as the graph element's one.
        shape.setId(candidate.getUUID());

        // Selection handling.
        if (canvas instanceof SelectionManager) {
            shape.getShape().addNodeMouseClickHandler(new NodeMouseClickHandler() {
                @Override
                public void onNodeMouseClick(final NodeMouseClickEvent nodeMouseClickEvent) {

                    final boolean isSelected = canvas.isSelected(shape);

                    if (!nodeMouseClickEvent.isShiftKeyDown()) {
                        canvas.clearSelection();
                    }

                    if (isSelected) {
                        log(Level.FINE, "Deselect [shape=" + shape.getId() + "]");
                        canvas.deselect(shape);
                    } else {
                        log(Level.FINE, "Select [shape=" + shape.getId() + "]");
                        canvas.select(shape);
                    }

                }
            });

        }

        doRegister(shape, candidate, factory);

        // Add the shapes on canvas and fire events.
        canvas.addShape(shape);
        canvas.draw();
        afterElementAdded(candidate);
    }
    
    protected void doRegister(final Shape shape, final Element element, final ShapeFactory factory) {
        
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
                    hasGraphElement.applyElementProperties(candidate, this, context);
                    afterElementUpdated(candidate, hasGraphElement);
                }

            }

        }
    }
    
    public void addChild(final Element parent, final Element child) {
        assert parent != null && child != null;

        final WiresShape parentShape = (WiresShape) canvas.getShape(parent.getUUID());
        final WiresShape childShape = (WiresShape) canvas.getShape(child.getUUID());
        parentShape.add(childShape);
    }
    

    public void updateElementPosition(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());

        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        final MutationContext context = new StaticMutationContext();
        shapeMutation.applyElementPosition(element, this, context);
        canvas.draw();
        afterElementUpdated(element, shapeMutation);
    }

    public void updateElementProperties(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());

        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        final MutationContext context = new StaticMutationContext();
        shapeMutation.applyElementProperties(element, this, context);
        canvas.draw();
        afterElementUpdated(element, shapeMutation);
    }

    public void deregister(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());
        // TODO: Delete connector connections to the node being deleted?
        canvas.deleteShape(shape);
        canvas.draw();
        afterElementDeleted(element);

    }
    
    public void clear() {
        canvas.clear();
        canvas.draw();
        afterClear();
    }
    
  
    protected void afterElementAdded(final Element element) {
    }

    protected void afterElementDeleted(final Element element) {
    }

    protected void afterElementUpdated(final Element element, final HasGraphElementMutation elementMutation) {
        elementMutation.afterMutations(canvas);
    }

    protected void afterClear() {
        fireCanvasClear();
    }
    
    public IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> getIndexBuilder() {
        return settings.getIndexBuilder();
    }
    
    public Index<?, ?> getGraphIndex() {
        return graphIndex;
    }

    public ShapeManager getShapeManager() {
        return shapeManager;
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
