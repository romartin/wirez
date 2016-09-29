package org.kie.workbench.common.stunner.core.registry.definition;

public interface TypeDefinitionSetRegistry<T> extends DefinitionSetRegistry<T> {

    T getDefinitionSetByType( Class<T> type );

}
