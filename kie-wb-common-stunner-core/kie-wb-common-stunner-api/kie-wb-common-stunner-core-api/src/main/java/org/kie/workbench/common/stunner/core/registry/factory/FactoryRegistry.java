package org.kie.workbench.common.stunner.core.registry.factory;

import org.kie.workbench.common.stunner.core.factory.Factory;
import org.kie.workbench.common.stunner.core.factory.definition.DefinitionFactory;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;
import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

public interface FactoryRegistry<F extends Factory<?, ?>> extends DynamicRegistry<F> {

    DefinitionFactory<?> getDefinitionFactory( String id );

    ElementFactory<?, ?> getGraphFactory( Class<? extends ElementFactory> type );

}
