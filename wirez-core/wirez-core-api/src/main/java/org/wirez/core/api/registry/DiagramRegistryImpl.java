package org.wirez.core.api.registry;

import org.wirez.core.api.diagram.Diagram;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DiagramRegistryImpl extends BaseListRegistry<Diagram> implements DiagramRegistry<Diagram> {
    
    @Override
    protected String getItemId(final Diagram item) {
        return item.getUUID();
    }

    @Override
    public void update(final Diagram diagram) {
        final int index = items.indexOf(diagram);
        final boolean isRemoved = items.remove(diagram);
        if (isRemoved) {
            items.add(index, diagram);
        }
        throw new RuntimeException("Diagram with uuid [" + diagram.getUUID() + "] cannot be updated as it does not exist.");
    }
}
