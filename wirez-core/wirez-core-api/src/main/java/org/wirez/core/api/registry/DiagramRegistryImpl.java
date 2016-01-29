package org.wirez.core.api.registry;

import org.wirez.core.api.diagram.Diagram;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

@Dependent
public class DiagramRegistryImpl<D extends Diagram> extends BaseListRegistry<D> implements DiagramRegistry<D> {
    
    @Override
    protected String getItemId(final D item) {
        return item.getUUID();
    }

    @Override
    public void update(final D diagram) {
        final int index = items.indexOf(diagram);
        final boolean isRemoved = items.remove(diagram);
        if (isRemoved) {
            items.add(index, diagram);
        }
        throw new RuntimeException("Diagram with uuid [" + diagram.getUUID() + "] cannot be updated as it does not exist.");
    }
}
