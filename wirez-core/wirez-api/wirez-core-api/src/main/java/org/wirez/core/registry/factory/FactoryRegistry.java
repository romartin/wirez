package org.wirez.core.registry.factory;

import org.wirez.core.factory.Factory;
import org.wirez.core.factory.definition.DefinitionFactory;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.registry.DynamicRegistry;

public interface FactoryRegistry<F extends Factory<?, ?>> extends DynamicRegistry<F> {

    DefinitionFactory<?> getDefinitionFactory( String id );

    ElementFactory<?, ?> getGraphFactory( Class<? extends ElementFactory> type );

}
