package org.wirez.core.api.graph.command;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.graph.GraphRulesManager;

@NonPortable
public class GraphCommandExecutionContextImpl implements GraphCommandExecutionContext {

    protected final transient DefinitionManager definitionManager;
    protected final transient FactoryManager factoryManager;
    protected final transient GraphRulesManager rulesManager;
    protected final transient GraphUtils graphUtils;

    public GraphCommandExecutionContextImpl(final DefinitionManager definitionManager, 
                                            final FactoryManager factoryManager, 
                                            final GraphRulesManager rulesManager, 
                                            final GraphUtils graphUtils) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.rulesManager = rulesManager;
        this.graphUtils = graphUtils;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    @Override
    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    @Override
    public FactoryManager getFactoryManager() {
        return factoryManager;
    }

    @Override
    public GraphRulesManager getRulesManager() {
        return rulesManager;
    }

}
