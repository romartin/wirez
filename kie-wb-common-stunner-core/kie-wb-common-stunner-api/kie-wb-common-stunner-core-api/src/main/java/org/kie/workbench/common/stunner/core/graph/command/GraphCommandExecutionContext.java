package org.kie.workbench.common.stunner.core.graph.command;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;
import org.kie.workbench.common.stunner.core.api.FactoryManager;

/**
 * The context beans for the execution of graph commands.
 */
public interface GraphCommandExecutionContext {
    
    DefinitionManager getDefinitionManager();

    FactoryManager getFactoryManager();
    
    GraphRulesManager getRulesManager();

}
