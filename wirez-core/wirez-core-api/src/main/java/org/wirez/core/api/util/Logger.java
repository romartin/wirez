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

package org.wirez.core.api.util;

import com.google.gwt.core.client.GWT;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.graph.processing.visitor.*;
import org.wirez.core.api.rule.RuleViolation;

import java.util.List;

/**
 * Just for development use.
 */
public class Logger {

    public static void logCommandResults(final Iterable<CommandResult> results) {
        if (results == null) {
            log("Results is null");
        } else {
            for (CommandResult result : results) {
                logCommandResult(result);
            }
        }
    }

    public static void logCommandResult(final CommandResult result) {
        String message = result.getMessage();
        CommandResult.Type type = result.getType();
        log("Command Result [message=" + message + "] [type" + type.name());
        Iterable<RuleViolation> violations = result.getRuleViolations();
        logRuleViolations(violations);
    }

    public static void logRuleViolations(final Iterable<RuleViolation> violations) {
        if (violations == null) {
            log("Violations is null");
        } else {
            for (RuleViolation result : violations) {
                logRuleViolation(result);
            }
        }
    }

    public static void logRuleViolation(final RuleViolation violation) {
        String message = violation.getMessage();
        RuleViolation.Type type = violation.getViolationType();
        log("Rule Violation [message=" + message + "] [type" + type.name());
    }
    
    private static final AbstractGraphVisitorCallback VISITOR_CALLBACK = new AbstractGraphVisitorCallback() {

        @Override
        public void visitGraph(DefaultGraph graph) {
            if (graph == null) {
                error("Graph is null!");
            } else {
                log("Graph UUID: " + graph.getUUID());
                log("Graph Id: " + graph.getDefinition().getId());
                log("  Graph Starting nodes");
                log("  ====================");
            }
        }

        @Override
        public void visitNode(Node node) {
            log("Node UUID: " + node.getUUID());
            List<ViewEdge> outEdges = (List<ViewEdge>) node.getOutEdges();
            if (outEdges == null || outEdges.isEmpty()) {
                log("  No outgoing edges found");
            } else {
                log("  Outgoing edges");
                log("  ==============");
            }
        }

        @Override
        public void visitViewNode(ViewNode node) {
            log("View Node UUID: " + node.getUUID());
            log("View Node Id: " + node.getDefinition().getId());

            List<ViewEdge> outEdges = (List<ViewEdge>) node.getOutEdges();
            if (outEdges == null || outEdges.isEmpty()) {
                log("  No outgoing edges found");
            } else {
                log("  Outgoing edges");
                log("  ==============");
            }
        }

        @Override
        public void visitDefaultNode(final DefaultNode node) {
            log("Default Node UUID: " + node.getUUID());
            List<ViewEdge> outEdges = (List<ViewEdge>) node.getOutEdges();
            if (outEdges == null || outEdges.isEmpty()) {
                log("  No outgoing edges found");
            } else {
                log("  Outgoing edges");
                log("  ==============");
            }
        }

        @Override
        public void visitEdge(Edge edge) {
            log("Edge UUI: " + edge.getUUID());

            final ViewNode outNode = (ViewNode) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

        @Override
        public void visitViewEdge(ViewEdge edge) {
            log("View Edge UUI: " + edge.getUUID());
            log("View Edge Id: " + edge.getDefinition().getId());

            final ViewNode outNode = (ViewNode) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

        @Override
        public void visitDefaultEdge(DefaultEdge edge) {
            log("Default Edge UUI: " + edge.getUUID());

            final ViewNode outNode = (ViewNode) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

        @Override
        public void visitChildRelationEdge(ChildRelationEdge edge) {
            log("Child-Relation Edge UUI: " + edge.getUUID());

            final ViewNode outNode = (ViewNode) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

    };

    public static void log(final DefaultGraph graph) {
        new DefaultGraphVisitorImpl()
                .setBoundsVisitorCallback(new GraphVisitorCallback.BoundsVisitorCallback() {
                    @Override
                    public void visitBounds(HasView element, Bounds.Bound ul, Bounds.Bound lr) {
                        log(" Bound UL [x=" + ul.getX() + ", y=" + ul.getY() + "]");
                        log(" Bound LR [x=" + lr.getX() + ", y=" + lr.getY() + "]");
                    }
                })

                .setPropertiesVisitorCallback(new GraphVisitorCallback.PropertyVisitorCallback() {
                    @Override
                    public void visitProperty(Element element, String key, Object value) {
                        log(" Property [key=" + key + ", value=" + value + "]");
                    }
                })
                .run(graph, VISITOR_CALLBACK, GraphVisitor.GraphVisitorPolicy.EDGE_FIRST);


    }

    public static void resume(final DefaultGraph graph) {
        new DefaultGraphVisitorImpl().run(graph, VISITOR_CALLBACK, GraphVisitor.GraphVisitorPolicy.EDGE_FIRST);
    }
    
    public static void log(final String message) {
        GWT.log(message);
    }

    public static void error(final String message) {
        GWT.log("[ERROR] " + message);
    }
}
