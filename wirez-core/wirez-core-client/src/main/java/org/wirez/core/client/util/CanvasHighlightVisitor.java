/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.util;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Timer;
import org.uberfire.mvp.Command;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.visitor.AbstractContentVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkContentVisitor;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeHighlightAnimation;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.mutation.HasCanvasStateMutation;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Visits the graph and highlights elements while visiting. Just for development use.
 */
public class CanvasHighlightVisitor {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.util.CanvasHighlightVisitor");
    private static final long DURATION = 500;

    private CanvasHandler canvasHandler;
    private final List<Shape> shapes = new LinkedList<Shape>();

    public CanvasHighlightVisitor(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    public void run() {
        assert canvasHandler != null;
      
        prepareSimulation(() -> animate(0, () -> log(Level.FINE, "CanvasHighlightVisitor - FINISHED")));
    }
    
    private void animate(final int index, final Command callback) {
        if (index < shapes.size()) {
            final Shape shape = shapes.get(index);

            if (shape instanceof HasCanvasStateMutation) {
                
                final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;

                Timer t = new Timer() {
                    @Override
                    public void run() {
                        canvasStateMutation.applyState(ShapeState.UNHIGHLIGHT);
                        animate(index + 1, callback);
                    }
                };

                canvasStateMutation.applyState(ShapeState.HIGHLIGHT);
                t.schedule( (int) DURATION );
                
            } else if (shape instanceof HasDecorators) {

                new ShapeHighlightAnimation(shape)
                        .setColor(ColorName.BLUE)
                        .setCallback(new ShapeAnimation.AnimationCallback() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFrame() {

                            }

                            @Override
                            public void onComplete() {
                                animate(index + 1, callback);
                            }

                        }).setDuration(DURATION)
                        .setCanvas(canvasHandler.getCanvas())
                        .run();
                
            }
            
        } else {
            
            callback.execute();
            
        }
        
    }
    
    private void prepareSimulation(final Command command) {

        final Graph graph = canvasHandler.getDiagram().getGraph();

        shapes.clear();

        final DefinitionManager definitionManager = ClientDefinitionManager.get();
        new TreeWalkContentVisitor().visit(graph, new AbstractContentVisitorCallback() {

            @Override
            public void visitNodeWithViewContent(Node<? extends View, ?> node) {
                super.visitNodeWithViewContent(node);
                addShape(node.getUUID());
                
            }

            @Override
            public void visitEdgeWithViewContent(Edge<? extends View, ?> edge) {
                super.visitEdgeWithViewContent(edge);
                addShape(edge.getUUID());
            }

            @Override
            public void visitNode(Node node) {
                super.visitNode(node);
                addShape(node.getUUID());
            }

            @Override
            public void visitEdge(Edge edge) {
                super.visitEdge(edge);
                addShape(edge.getUUID());
            }

            @Override
            public void visitGraph(Graph graph) {
                super.visitGraph(graph);
            }

            @Override
            public void endVisit() {
                super.endVisit();
                command.execute();
            }

            private void addShape(String uuid) {
                final Shape shape = canvasHandler.getCanvas().getShape(uuid);
                if ( null != shape ) {
                    shapes.add(shape);
                }
            }

        }, VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE);
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
