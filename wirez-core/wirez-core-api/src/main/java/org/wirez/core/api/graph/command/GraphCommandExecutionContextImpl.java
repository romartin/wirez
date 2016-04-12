package org.wirez.core.api.graph.command;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RuleManager;

@NonPortable
public class GraphCommandExecutionContextImpl implements GraphCommandExecutionContext {

    protected final transient DefinitionManager definitionManager;
    protected final transient FactoryManager factoryManager;
    protected final transient RuleManager ruleManager;
    protected final transient GraphCommandFactory graphCommandFactory;
    protected final transient GraphUtils graphUtils;

    public GraphCommandExecutionContextImpl(final DefinitionManager definitionManager, 
                                            final FactoryManager factoryManager, 
                                            final RuleManager ruleManager, 
                                            final GraphCommandFactory graphCommandFactory,
                                            final GraphUtils graphUtils) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.ruleManager = ruleManager;
        this.graphCommandFactory = graphCommandFactory;
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
    public RuleManager getRuleManager() {
        return ruleManager;
    }

    @Override
    public GraphCommandFactory getCommandFactory() {
        return graphCommandFactory;
    }
}
