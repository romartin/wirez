package org.kie.workbench.common.stunner.core.registry.impl;

import java.util.List;

import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.registry.diagram.DiagramRegistry;

public abstract class AbstractDiagramListRegistry<D extends Diagram>
        extends AbstractDynamicRegistryWrapper<D, ListRegistry<D>>
        implements DiagramRegistry<D> {

    public AbstractDiagramListRegistry(final List<D> items) {
        super(new ListRegistry<>(Diagram::getUUID, items));
    }

    @Override
    public D getDiagramByUUID(final String uuid) {
        return getWrapped().getItemByKey(uuid);
    }

    @Override
    public void update(final D diagram) {

        final int index = getWrapped().indexOf(diagram);

        if (index != -1) {

            final boolean isRemoved = remove(diagram);

            if (isRemoved) {
                getWrapped().add(index, diagram);
            }

        } else {

            throw new RuntimeException("Diagram with uuid [" + diagram.getUUID() + "] cannot be updated as it does not exist.");

        }

    }

}
