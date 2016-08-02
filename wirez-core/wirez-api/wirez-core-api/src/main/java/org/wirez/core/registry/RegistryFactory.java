package org.wirez.core.registry;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.factory.Factory;
import org.wirez.core.registry.definition.AdapterRegistry;
import org.wirez.core.registry.definition.TypeDefinitionRegistry;
import org.wirez.core.registry.definition.TypeDefinitionSetRegistry;
import org.wirez.core.registry.diagram.DiagramRegistry;
import org.wirez.core.registry.factory.FactoryRegistry;

public interface RegistryFactory {

    AdapterRegistry newAdapterRegistry();

    <T> TypeDefinitionSetRegistry<T> newDefinitionSetRegistry();

    <T> TypeDefinitionRegistry<T> newDefinitionRegistry();

    <T extends Factory<?, ?>> FactoryRegistry<T> newFactoryRegistry();

    <T extends Diagram> DiagramRegistry<T> newDiagramRegistry();

}
