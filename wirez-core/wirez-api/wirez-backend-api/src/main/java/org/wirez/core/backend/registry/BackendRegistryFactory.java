package org.wirez.core.backend.registry;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.diagram.DiagramRegistry;
import org.wirez.core.registry.RegistryFactory;

public interface BackendRegistryFactory extends RegistryFactory {

    <T extends Diagram> DiagramRegistry<T> newDiagramSynchronizedRegistry();

}
