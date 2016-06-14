package org.wirez.core.graph.command;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.rule.graph.GraphRulesManager;

/**
 * The context beans for the execution of graph commands.
 */
public interface GraphCommandExecutionContext {
    
    DefinitionManager getDefinitionManager();

    FactoryManager getFactoryManager();
    
    GraphRulesManager getRulesManager();

}
