package org.kie.workbench.common.stunner.core.registry.factory;

import org.kie.workbench.common.stunner.core.factory.Factory;
import org.kie.workbench.common.stunner.core.factory.definition.DefinitionFactory;

public interface TypeFactoryRegistry<F extends Factory<?, ?>> extends FactoryRegistry<F> {

    DefinitionFactory<?> getDefinitionFactory( Class<?> type );

}
