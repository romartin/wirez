package org.wirez.core.api.graph.processing.visitor.tree;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.visitor.VisitorCallback;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Visits the graph by walking on the different tree nodes and their children in a recursive way. 
 */
@Dependent
public class TreeWalkVisitor extends AbstractTreeWalkVisitor<VisitorCallback<Node, Edge, Graph<?, Node>>> {

}
