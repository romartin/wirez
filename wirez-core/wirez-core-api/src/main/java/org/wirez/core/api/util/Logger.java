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
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.HasView;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.api.graph.processing.GraphVisitor;
import org.wirez.core.api.rule.RuleViolation;

import java.util.List;

/**
 * Just for development use
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

    public static void log(final DefaultGraph graph) {
        new GraphVisitor(graph, new GraphVisitor.Visitor() {
            @Override
            public void visitGraph(DefaultGraph graph) {
                if (graph == null) {
                    error("Graph is null!");
                } else {
                    log("**************************************************************************************");
                    log("Title: " + graph.getDefinition().getContent().getTitle());
                    log("Description: " + graph.getDefinition().getContent().getDescription());
                    log("Starting nodes");
                    log("==============");
                }
            }

            @Override
            public void visitNode(ViewNode node) {
                String graphTitle = node.getDefinition().getContent().getTitle();
                String graphDesc = node.getDefinition().getContent().getDescription();
                log("**************************************************************************************");
                log("Node Id: " + node.getUUID());
                log("Node Title: " + graphTitle);
                log("Node Description: " + graphDesc);

                List<ViewEdge> outEdges = (List<ViewEdge>) node.getOutEdges();
                if (outEdges == null || outEdges.isEmpty()) {
                    log("No outgoing edges found");
                } else {
                    log("Outgoing edges");
                    log("==============");
                }
            }

            @Override
            public void visitEdge(ViewEdge edge) {
                if (edge == null) {
                    error("Edge is null!");
                } else {
                    final String edgeId = edge.getUUID();

                    log("Edge Id: " + edgeId);

                    final ViewNode outNode = (ViewNode) edge.getTargetNode();
                    if (outNode == null) {
                        error("No outgoing node found");
                    } else {
                        log("Outgoing Node");
                        log("==============");
                    }
                }
            }

            @Override
            public void visitUnconnectedEdge(ViewEdge edge) {
                if (edge == null) {
                    error("Edge unconnected is null!");
                } else {
                    final String edgeId = edge.getUUID();

                    log("Edge unconnected Id: " + edgeId);

                    final ViewNode outNode = (ViewNode) edge.getTargetNode();
                    if (outNode == null) {
                        error("No outgoing node found");
                    } else {
                        log("Outgoing Node");
                        log("==============");
                    }
                }
            }

            @Override
            public void end() {
                log("**************************************************************************************");
            }
        }, GraphVisitor.VisitorPolicy.EDGE_FIRST)
                .setBoundsVisitor(new GraphVisitor.BoundsVisitor() {
                    @Override
                    public void visitBounds(HasView element, Bounds.Bound ul, Bounds.Bound lr) {
                        log(" Bound UL [x=" + ul.getX() + ", y=" + ul.getY() + "]");
                        log(" Bound LR [x=" + lr.getX() + ", y=" + lr.getY() + "]");
                    }
                })
                .setPropertyVisitor(new GraphVisitor.PropertyVisitor() {
                    @Override
                    public void visitProperty(Element element, String key, Object value) {
                        log(" Property [key=" + key + ", value=" + value + "]");
                    }
                })
                .run();
    }
    
    public static void log(final String message) {
        GWT.log(message);
    }

    public static void error(final String message) {
        GWT.log("[ERROR] " + message);
    }
}
