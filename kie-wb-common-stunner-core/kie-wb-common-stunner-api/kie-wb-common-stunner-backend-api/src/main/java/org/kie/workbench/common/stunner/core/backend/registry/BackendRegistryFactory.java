package org.kie.workbench.common.stunner.core.backend.registry;

import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.registry.diagram.DiagramRegistry;
import org.kie.workbench.common.stunner.core.registry.RegistryFactory;

public interface BackendRegistryFactory extends RegistryFactory {

    <T extends Diagram> DiagramRegistry<T> newDiagramSynchronizedRegistry();

}
