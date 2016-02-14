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

package org.wirez.core.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.*;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.*;
import org.wirez.core.api.graph.processing.visitor.*;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.service.ClientRuntimeError;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Just for development use.
 */
public class WirezLogger {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.util.WirezLogger");

    public static String getErrorMessage(final ClientRuntimeError error) {
        final String message = error.getMessage();
        final Throwable t1 = error.getThrowable();
        final Throwable t2 = t1 != null ? t1.getCause() : null;

        if ( null != t2 ) {
            return t2.getMessage();
        } else if ( null != t1 ) {
            return t1.getMessage();
        } 
        
        return message;
    }
    
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
        public void visitGraphWithViewContent(DefaultGraph<? extends ViewContent, ? extends Node, ? extends Edge> graph) {
            if (graph == null) {
                error("Graph is null!");
            } else {
                log("Graph UUID: " + graph.getUUID());
                log("Graph Id: " + graph.getContent().getDefinition().getId());
                log("  Graph Starting nodes");
                log("  ====================");
            }
        }

        @Override
        public void visitGraph(DefaultGraph graph) {
            if (graph == null) {
                error("Graph is null!");
            } else {
                log("Graph UUID: " + graph.getUUID());
                log("  Graph Starting nodes");
                log("  ====================");
            }
        }

        @Override
        public void visitNode(Node node) {
            log("Node UUID: " + node.getUUID());
            List<Edge> outEdges = (List<Edge>) node.getOutEdges();
            if (outEdges == null || outEdges.isEmpty()) {
                log("  No outgoing edges found");
            } else {
                log("  Outgoing edges");
                log("  ==============");
            }
        }


        @Override
        public void visitNodeWithViewContent(Node<? extends ViewContent, ?> node) {
            log("(View) UUID: " + node.getUUID());
            log("(View) Node Id: " + node.getContent().getDefinition().getId());
            List<Edge> outEdges = (List<Edge>) node.getOutEdges();
            if (outEdges == null || outEdges.isEmpty()) {
                log("  No outgoing edges found");
            } else {
                log("  Outgoing edges");
                log("  ==============");
            }
        }

        @Override
        public void visitEdgeWithViewContent(Edge<? extends ViewContent, ?> edge) {
            log("(View) Edge UUI: " + edge.getUUID());
            log("(View) Edge Id: " + edge.getContent().getDefinition().getId());

            final Node outNode = (Node) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

        @Override
        public void visitEdgeWithParentChildRelationContent(Edge<ParentChildRelationship, ?> edge) {
            log("(Parent-Child= Edge UUI: " + edge.getUUID());

            final Node outNode = (Node) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

        @Override
        public void visitEdge(Edge edge) {
            log("Edge UUI: " + edge.getUUID());

            final Node outNode = (Node) edge.getTargetNode();
            if (outNode == null) {
                log("  No outgoing node found");
            } else {
                log("  Outgoing Node");
                log("  ==============");
            }
        }

    };

    public static void log(final DefaultGraph graph) {
        final DefinitionManager definitionManager = ClientDefinitionManager.get();
        new DefaultGraphVisitorImpl()
                .setDefinitionManager(definitionManager)
                .setBoundsVisitorCallback(new DefaultGraphVisitorCallback.BoundsVisitorCallback() {
                    @Override
                    public void visitBounds(Element<? extends ViewContent> element, Bounds.Bound ul, Bounds.Bound lr) {
                        log(" Bound UL [x=" + ul.getX() + ", y=" + ul.getY() + "]");
                        log(" Bound LR [x=" + lr.getX() + ", y=" + lr.getY() + "]");
                    }
                })
                .setPropertiesVisitorCallback(new DefaultGraphVisitorCallback.PropertyVisitorCallback() {
                    @Override
                    public void visitProperty(Element element, String key, Object value) {
                        log(" Property [key=" + key + ", value=" + value + "]");
                    }
                })
                .visit(graph, VISITOR_CALLBACK, GraphVisitor.GraphVisitorPolicy.EDGE_FIRST);


    }

    public static void resume(final DefaultGraph graph) {
        final DefinitionManager definitionManager = ClientDefinitionManager.get();
        new DefaultGraphVisitorImpl()
                .setDefinitionManager(definitionManager)
                .visit(graph, VISITOR_CALLBACK, GraphVisitor.GraphVisitorPolicy.EDGE_FIRST);
    }

    private static void log(final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(Level.INFO, message);
        }
    }

    private static void error(final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(Level.SEVERE, message);
        }
    }

}
