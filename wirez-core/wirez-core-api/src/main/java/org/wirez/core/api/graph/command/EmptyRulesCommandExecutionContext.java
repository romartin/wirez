package org.wirez.core.api.graph.command;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RulesManager;
import org.wirez.core.api.rule.impl.empty.EmptyRulesManagerImpl;

@NonPortable
public class EmptyRulesCommandExecutionContext implements GraphCommandExecutionContext {
    
    private final transient GraphCommandExecutionContext wrapped;

    public EmptyRulesCommandExecutionContext(final GraphCommandExecutionContext wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return wrapped.getDefinitionManager();
    }

    @Override
    public GraphUtils getGraphUtils() {
        return wrapped.getGraphUtils();
    }

    @Override
    public FactoryManager getFactoryManager() {
        return wrapped.getFactoryManager();
    }

    @Override
    public RulesManager getRulesManager() {
        return EmptyRulesManagerImpl.INSTANCE;
    }

}
