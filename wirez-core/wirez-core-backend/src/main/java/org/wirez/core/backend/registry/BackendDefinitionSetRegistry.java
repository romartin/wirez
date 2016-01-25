package org.wirez.core.backend.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.registry.BaseListRegistry;
import org.wirez.core.api.registry.DefinitionSetRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BackendDefinitionSetRegistry extends BaseListRegistry<DefinitionSet> implements DefinitionSetRegistry {

    private static final Logger LOG = LoggerFactory.getLogger(BackendDefinitionSetRegistry.class);

    Instance<DefinitionSet> definitionSetInstances;

    @Inject
    public BackendDefinitionSetRegistry(Instance<DefinitionSet> definitionSetInstances) {
        this.definitionSetInstances = definitionSetInstances;
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
    }

    private void initDefinitionSets() {
        for (DefinitionSet definitionSet : definitionSetInstances) {
            items.add(definitionSet);
        }
    }
    
    @Override
    protected String getItemId(final DefinitionSet item) {
        return item.getId();
    }
    
}
