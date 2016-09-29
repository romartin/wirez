package org.kie.workbench.common.stunner.core.graph.command;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;

@NonPortable
public class EmptyRulesCommandExecutionContext implements GraphCommandExecutionContext {

    protected final transient DefinitionManager definitionManager;
    protected final transient FactoryManager factoryManager;

    public EmptyRulesCommandExecutionContext(final DefinitionManager definitionManager,
                                            final FactoryManager factoryManager) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    @Override
    public FactoryManager getFactoryManager() {
        return factoryManager;
    }

    @Override
    public GraphRulesManager getRulesManager() {
        return null;
    }

}
