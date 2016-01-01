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
import com.google.gwt.core.client.GWT;
import org.uberfire.mvp.Command;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.impl.ChildRelationship;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.api.graph.processing.AbstractVisitor;
import org.wirez.core.api.graph.processing.GraphVisitor;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeHighlightAnimation;
import org.wirez.core.client.canvas.CanvasHandler;

import java.util.LinkedList;
import java.util.List;

public class CanvasHighlightVisitor {

    private static final double ALPHA_INIT = 0.1;
    private static final double ALPHA_END = 1;
    private static final double DURATION = 1000;


    private CanvasHandler canvasHandler;
    private final List<Shape> shapes = new LinkedList<Shape>();

    public CanvasHighlightVisitor(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
    }

    public void run() {
        assert canvasHandler != null;
        assert canvasHandler.getGraph() != null;
      
        prepareSimulation(new Command() {
            @Override
            public void execute() {
                animate(0, new Command() {
                    @Override
                    public void execute() {
                        GWT.log("GraphCanvasSimulator - FINISHED");
                        
                    }
                });
            }
        });
    }
    
    
    private void animate(final int index, final Command callback) {
        if (index < shapes.size()) {
            final Shape shape = shapes.get(index);
            new ShapeHighlightAnimation(shape).setColor(ColorName.BLUE).setCallback(new ShapeAnimation.AnimationCallback() {
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
                
            }).setDuration(500).run();
            
        } else {
            
            callback.execute();
            
        }
    }
    
    private void prepareSimulation(final Command command) {

        final DefaultGraph graph = canvasHandler.getGraph();

        shapes.clear();

        new GraphVisitor(graph, new AbstractVisitor() {

            @Override
            public void visitViewNode(ViewNode node) {
                super.visitViewNode(node);
                addShape(node.getUUID());
            }

            @Override
            public void visitNode(Node node) {
                super.visitNode(node);
                addShape(node.getUUID());
            }

            @Override
            public void visitViewEdge(ViewEdge edge) {
                super.visitViewEdge(edge);
                addShape(edge.getUUID());
            }

            @Override
            public void visitEdge(Edge edge) {
                super.visitEdge(edge);
                addShape(edge.getUUID());
            }

            @Override
            public void visitUnconnectedEdge(Edge edge) {
                super.visitUnconnectedEdge(edge);
                addShape(edge.getUUID());
            }

            @Override
            public void endVisit() {
                super.endVisit();
                command.execute();
            }

            @Override
            public void visitChildRelationship(ChildRelationship childRelationship) {
                super.visitChildRelationship(childRelationship);
            }
            
            private void addShape(String uuid) {
                final Shape shape = canvasHandler.getSettings().getCanvas().getShape(uuid);
                if ( null != shape ) {
                    shapes.add(shape);
                }
            }
            
        }, GraphVisitor.VisitorPolicy.EDGE_FIRST).run();

    }
    
}
