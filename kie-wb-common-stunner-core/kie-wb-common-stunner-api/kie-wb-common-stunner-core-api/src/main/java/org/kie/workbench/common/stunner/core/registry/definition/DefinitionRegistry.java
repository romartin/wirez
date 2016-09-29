package org.kie.workbench.common.stunner.core.registry.definition;

import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

public interface DefinitionRegistry<D> extends DynamicRegistry<D> {

    D getDefinitionById( String id );

}
