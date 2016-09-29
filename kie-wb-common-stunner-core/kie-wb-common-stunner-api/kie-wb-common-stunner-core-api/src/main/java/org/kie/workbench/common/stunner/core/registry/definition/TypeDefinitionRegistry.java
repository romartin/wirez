package org.kie.workbench.common.stunner.core.registry.definition;

public interface TypeDefinitionRegistry<D> extends DefinitionRegistry<D> {

    D getDefinitionByType( Class<D> type );

}
