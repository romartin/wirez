package org.wirez.core.backend;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.BaseFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.ViewElementFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Service
public class BackendFactoryManager extends BaseFactoryManager {

    BeanManager beanManager;
    Instance<ModelFactory<?>> modelBuilderInstances;

    @Inject
    public BackendFactoryManager(Instance<ModelFactory<?>> modelBuilderInstances,
                                 DefinitionManager definitionManager,
                                 BeanManager beanManager) {
        super( definitionManager );
        this.modelBuilderInstances = modelBuilderInstances;
        this.beanManager = beanManager;
    }

    @PostConstruct
    public void init() {
        initModelFactories();
    }


    private void initModelFactories() {
        for (ModelFactory<?> modelBuilder : modelBuilderInstances) {
            modelFactories.add(modelBuilder);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ElementFactory getElementFactory(Object definition,
                                               Class<?> graphElementClass,
                                               String factory) {
        
        String ref = getFactoryReference( graphElementClass, factory );

        Bean<?> bean = beanManager.getBeans(ref).iterator().next();

        if ( null == bean ) {
            throw new RuntimeException( "No factory found for class " + ref );
        }

        // Return the element's factory instance.
        CreationalContext ctx = beanManager.createCreationalContext(bean);
        return (ElementFactory) beanManager.getReference(bean, bean.getBeanClass(), ctx);
        
    }

}
