package org.kie.workbench.common.stunner.core.registry.definition;

import org.kie.workbench.common.stunner.core.registry.Registry;

public interface DefinitionSetRegistry<T> extends Registry<T> {

    T getDefinitionSetById( String id );

}
