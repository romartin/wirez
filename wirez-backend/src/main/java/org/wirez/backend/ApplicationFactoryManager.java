package org.wirez.backend;

import org.wirez.core.api.AbstractFactoryManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.backend.annotation.Application;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Application
public class ApplicationFactoryManager extends AbstractFactoryManager {

    BeanManager beanManager;
    Instance<ModelFactory<?>> modelBuilderInstances;

    @Inject
    public ApplicationFactoryManager(Instance<ModelFactory<?>> modelBuilderInstances,
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
        
        Bean<?> bean = getFactoryBean( graphElementClass, factory );
        
        // Return the element's factory instance.
        return getFactory( bean );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected GraphFactory getGraphFactory(Object definition,
                                             Class<?> graphElementClass,
                                             String factory) {

        Bean<?> bean = getFactoryBean( graphElementClass, factory );

        // Return the element's factory instance.
        return getFactory( bean );
    }
    
    protected Bean<?> getFactoryBean(Class<?> graphElementClass,
                                      String factory) {

        String ref = getFactoryReference( graphElementClass, factory );

        Bean<?> bean = beanManager.getBeans(ref).iterator().next();

        if ( null == bean ) {
            throw new RuntimeException( "No factory found for class " + ref );
        }
        
        return bean;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getFactory(Bean<?> bean) {
        CreationalContext ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, bean.getBeanClass(), ctx);
    }

    @Override
    public <G extends Graph, S extends Settings> Diagram<G, S> newDiagram(String uuid, G graph, S settings) {
        return (Diagram<G, S>) new DiagramImpl( uuid, graph, settings );
    }
}
