package org.wirez.core.registry.definition;

import org.wirez.core.registry.DynamicRegistry;
import org.wirez.core.registry.Registry;

public interface DefinitionRegistry<D> extends DynamicRegistry<D> {

    D getDefinitionById( String id );

}
