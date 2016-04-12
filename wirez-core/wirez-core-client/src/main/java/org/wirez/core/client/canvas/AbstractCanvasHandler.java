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

package org.wirez.core.client.canvas;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.batch.BatchCommandResult;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.graph.processing.traverse.tree.AbstractTreeTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.listener.CanvasListener;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.*;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.view.HasEventHandlers;
import org.wirez.core.client.view.ShapeView;
import org.wirez.core.client.view.event.MouseClickEvent;
import org.wirez.core.client.view.event.MouseClickHandler;
import org.wirez.core.client.view.event.ViewEventType;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractCanvasHandler<D extends Diagram, C extends AbstractCanvas, L extends CanvasListener> 
        implements CanvasHandler<D, C, L> {

    private static Logger LOGGER = Logger.getLogger(AbstractCanvasHandler.class.getName());

    protected ClientDefinitionManager clientDefinitionManager;
    protected ClientFactoryServices clientFactoryServices;
    protected RuleManager ruleManager;
    protected GraphCommandManager graphCommandManager;
    protected GraphCommandFactory graphCommandFactory;
    protected GraphUtils graphUtils;
    protected IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder;
    protected CanvasCommandFactory commandFactory;
    protected TreeWalkTraverseProcessor treeWalkTraverseProcessor;
    protected ShapeManager shapeManager;
    protected CanvasCommandManager<AbstractCanvasHandler> commandManager;
    
    protected C canvas;
    protected D diagram;
    protected Index<?, ?> graphIndex;
    protected Collection<L> listeners = new LinkedList<L>();

    @Inject
    public AbstractCanvasHandler(final ClientDefinitionManager clientDefinitionManager,
                                 final ClientFactoryServices clientFactoryServices,
                                 final RuleManager ruleManager,
                                 final GraphCommandManager graphCommandManager,
                                 final GraphCommandFactory graphCommandFactory,
                                 final GraphUtils graphUtils,
                                 final IncrementalIndexBuilder indexBuilder,
                                 final CanvasCommandFactory commandFactory,
                                 final TreeWalkTraverseProcessor treeWalkTraverseProcessor, 
                                 final ShapeManager shapeManager,
                                 final CanvasCommandManager<AbstractCanvasHandler> commandManager) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.ruleManager = ruleManager;
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
        this.graphUtils = graphUtils;
        this.indexBuilder = (IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>>) indexBuilder;
        this.commandFactory = commandFactory;
        this.treeWalkTraverseProcessor = treeWalkTraverseProcessor;
        this.shapeManager = shapeManager;
        this.commandManager = commandManager;
    }

    @Override
    public AbstractCanvasHandler<D, C, L> draw(D diagram, C canvas) {
        this.canvas = canvas;
        this.diagram = diagram;

        // Initialize the graph handler that provides processing and querying operations over the graph.
        
        this.graphIndex = indexBuilder.build( diagram.getGraph() );
        
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
        // Fire initialization completed event.
        fireCanvasInitialized();
    }

    @Override
    public C getCanvas() {
        return canvas;
    }

    @Override
    public D getDiagram() {
        return diagram;
    }

    protected void draw() {

        treeWalkTraverseProcessor
                .usePolicy(TreeWalkTraverseProcessor.TraversePolicy.VISIT_EDGE_AFTER_TARGET_NODE)
                .traverse(diagram.getGraph(), new AbstractTreeTraverseCallback<Graph, Node, Edge>() {
            @Override
            public void startGraphTraversal(final Graph graph) {
                
            }

            @Override
            public boolean startNodeTraversal(final Node node) {
                
                if ( node.getContent() instanceof View ) {
                    final View viewContent = (View) node.getContent();
                    final ShapeFactory factory = shapeManager.getFactory(viewContent.getDefinition());

                    // Add the node shape into the canvas.
                    register(factory, node);
                    applyElementMutation(node);
                    
                    return true;
                }
                
                return false;
            }

            @Override
            public boolean startEdgeTraversal(final Edge edge) {
                
                final Object content = edge.getContent();

                if ( content instanceof View ) {

                    final View viewContent = (View) edge.getContent();
                    final ShapeFactory factory = shapeManager.getFactory(viewContent.getDefinition());

                    // Add the edge shape into the canvas.
                    register(factory, edge);
                    applyElementMutation(edge);
                    final String uuid = edge.getUUID();
                    BaseConnector connector = (BaseConnector) getCanvas().getShape(uuid);
                    connector.applyConnections(edge, AbstractCanvasHandler.this);
                    
                    return true;

                } else if ( content instanceof Child ) {

                    final Node child = edge.getTargetNode();
                    final Node parent = edge.getSourceNode();

                    final Object childContent = child.getContent();
                    if (childContent instanceof View) {
                        addChild(parent, child);
                        applyElementMutation(child);
                    }
                    
                    return true;
                } 

                return false;
                
            }

            @Override
            public void endGraphTraversal() {

            }
        });
        
    }

     /*
        ***************************************************************************************
        * Listeners handling
        ***************************************************************************************
     */

    @Override
    public AbstractCanvasHandler<D, C, L> addListener(final L listener) {
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
        assert content instanceof View;
        
        final Object wirez = ( (View) candidate.getContent()).getDefinition();
        final Shape shape = factory.build(wirez, this);

        // Set the same identifier as the graph element's one.
        shape.setId(candidate.getUUID());

        // Selection handling.
        final ShapeView shapeView = shape.getShapeView();
        if ( canvas instanceof SelectionManager && shapeView instanceof HasEventHandlers ) {
            
            final HasEventHandlers hasEventHandlers = (HasEventHandlers) shapeView;
            
            hasEventHandlers.addHandler(ViewEventType.MOUSE_CLICK, new MouseClickHandler() {
                @Override
                public void handle(final MouseClickEvent event) {
                    final boolean isSelected = canvas.isSelected(shape);
                    
                    if (!event.isShiftKeyDown()) {
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

        
        // Add the shapes on canvas and fire events.
        canvas.addShape(shape);
        canvas.draw();

        // Parents can register controls etc here.
        doRegister(shape, candidate, factory);
        
        // Fire updates.
        afterElementAdded(candidate);
    }
    
    protected void doRegister(final Shape shape, final Element element, final ShapeFactory factory) {
        
    }

    public void deregister(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());
        // TODO: Delete connector connections to the node being deleted?
        doDeregister(shape, element);
        canvas.deleteShape(shape);
        canvas.draw();
        afterElementDeleted(element);

    }

    protected void doDeregister(final Shape shape, final Element element) {
        
    }

    public void applyElementMutation(final Element candidate) {
        final Shape shape = canvas.getShape(candidate.getUUID());
        if (shape instanceof HasMutation) {

            final HasMutation hasMutation = (HasMutation) shape;

            if (hasMutation.accepts(MutationType.STATIC)) {

                // The mutation context.
                final GraphContext context = buildGraphContext();

                if (shape instanceof HasGraphElementMutation) {
                    final HasGraphElementMutation hasGraphElement = (HasGraphElementMutation) shape;
                    hasGraphElement.applyElementPosition(candidate, this, context);
                    hasGraphElement.applyElementProperties(candidate, this, context);
                    afterElementUpdated(candidate, hasGraphElement);
                }

            }

        }
    }
    
    public CommandResult<CanvasViolation> allow(final Command<AbstractCanvasHandler, CanvasViolation> command) {
        return commandManager.allow( this, command );
    }

    public CommandResult<CanvasViolation> execute(final Command<AbstractCanvasHandler, CanvasViolation> command) {
        return commandManager.execute( this, command );
    }

    public AbstractCanvasHandler<D, C, L> batch(final Command<AbstractCanvasHandler, CanvasViolation> command) {
        commandManager.batch( command );
        return this;
    }

    public BatchCommandResult<CanvasViolation> executeBatch() {
        return commandManager.executeBatch( this );
    }

    public CommandResult<CanvasViolation> undo() {
        return commandManager.undo( this );
    }
    
    private GraphContext buildGraphContext() {
        
        // The mutation context.
        final Context context = new StaticMutationContext();

        // The graph mutation context.
        final GraphContext graphContext = new GraphContextImpl( context, graphUtils );
        
        return graphContext;
    }
    
    public void addChild(final Element parent, final Element child) {
        assert parent != null && child != null;

        final Shape parentShape = canvas.getShape(parent.getUUID());
        final Shape childShape = canvas.getShape(child.getUUID());
        canvas.addChildShape(parentShape, childShape);
        
    }

    public void removeChild(final Element parent, final Element child) {
        assert parent != null && child != null;

        final Shape parentShape = canvas.getShape(parent.getUUID());
        final Shape childShape = canvas.getShape(child.getUUID());
        canvas.removeChildShape(parentShape, childShape);

    }
    

    public void updateElementPosition(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());
        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;
        
        // The mutation context.
        final GraphContext context = buildGraphContext();
        shapeMutation.applyElementPosition(element, this, context);
        
        canvas.draw();
        
        afterElementUpdated(element, shapeMutation);
    }

    public void updateElementProperties(final Element element) {
        final Shape shape = canvas.getShape(element.getUUID());
        final HasGraphElementMutation shapeMutation = (HasGraphElementMutation) shape;

        // The mutation context.
        final GraphContext context = buildGraphContext();
        shapeMutation.applyElementProperties(element, this, context);

        canvas.draw();

        afterElementUpdated(element, shapeMutation);
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

    public ClientDefinitionManager getClientDefinitionManager() {
        return clientDefinitionManager;
    }

    public ClientFactoryServices getClientFactoryServices() {
        return clientFactoryServices;
    }

    public RuleManager getRuleManager() {
        return ruleManager;
    }

    public GraphCommandManager getGraphCommandManager() {
        return graphCommandManager;
    }

    public GraphCommandFactory getGraphCommandFactory() {
        return graphCommandFactory;
    }

    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    public TreeWalkTraverseProcessor getTreeWalkTraverseProcessor() {
        return treeWalkTraverseProcessor;
    }

    public CanvasCommandFactory getCommandFactory() {
        return commandFactory;
    }

    public Index<?, ?> getGraphIndex() {
        return graphIndex;
    }

    public IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> getIndexBuilder() {
        return indexBuilder;
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
