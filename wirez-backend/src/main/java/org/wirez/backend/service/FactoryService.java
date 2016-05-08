package org.wirez.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.backend.ApplicationFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.factory.ModelFactory;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Service
public class FactoryService extends ApplicationFactoryManager implements org.wirez.core.api.remote.FactoryService {
    
    @Inject
    public FactoryService(Instance<ModelFactory<?>> modelBuilderInstances,
                          DefinitionManager definitionManager,
                          BeanManager beanManager) {
        super(modelBuilderInstances, definitionManager, beanManager);
    }
    
}
