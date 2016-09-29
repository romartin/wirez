package org.kie.workbench.common.stunner.core.validation.graph;

import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;
import org.kie.workbench.common.stunner.core.validation.Validator;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;

public interface GraphValidator extends Validator<Graph<?, Node<?, Edge>>, GraphValidatorCallback> {

    GraphValidator withRulesManager( GraphRulesManager rulesManager );

}
