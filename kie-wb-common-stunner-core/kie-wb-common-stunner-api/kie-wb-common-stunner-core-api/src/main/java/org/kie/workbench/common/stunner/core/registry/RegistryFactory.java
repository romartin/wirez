package org.kie.workbench.common.stunner.core.registry;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.factory.Factory;
import org.kie.workbench.common.stunner.core.registry.definition.TypeDefinitionSetRegistry;
import org.kie.workbench.common.stunner.core.registry.diagram.DiagramRegistry;
import org.kie.workbench.common.stunner.core.registry.command.CommandRegistry;
import org.kie.workbench.common.stunner.core.registry.definition.AdapterRegistry;
import org.kie.workbench.common.stunner.core.registry.definition.TypeDefinitionRegistry;
import org.kie.workbench.common.stunner.core.registry.factory.FactoryRegistry;

public interface RegistryFactory {

    AdapterRegistry newAdapterRegistry();

    <T> TypeDefinitionSetRegistry<T> newDefinitionSetRegistry();

    <T> TypeDefinitionRegistry<T> newDefinitionRegistry();

    <C extends Command>CommandRegistry<C> newCommandRegistry();

    <T extends Factory<?, ?>> FactoryRegistry<T> newFactoryRegistry();

    <T extends Diagram> DiagramRegistry<T> newDiagramRegistry();

}
