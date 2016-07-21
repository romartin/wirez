package org.wirez.core.validation.graph;

import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.rule.graph.GraphRulesManager;
import org.wirez.core.validation.Validator;

public interface GraphValidator extends Validator<Graph<?, Node<?, Edge>>, GraphValidatorCallback> {

    GraphValidator withRulesManager( GraphRulesManager rulesManager );

}
