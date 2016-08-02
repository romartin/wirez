package org.wirez.core.registry.factory;

import org.wirez.core.factory.Factory;
import org.wirez.core.factory.definition.DefinitionFactory;

public interface TypeFactoryRegistry<F extends Factory<?, ?>> extends FactoryRegistry<F> {

    DefinitionFactory<?> getDefinitionFactory( Class<?> type );

}
