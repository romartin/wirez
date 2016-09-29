package org.kie.workbench.common.stunner.core.client.components.palette.model;

import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinition;
import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteItem;

import java.util.List;

public abstract class AbstractPaletteDefinition<I extends PaletteItem> implements PaletteDefinition<I> {

    protected final List<I> items;

    protected AbstractPaletteDefinition( final List<I> groups ) {
        this.items = groups;
    }

    @Override
    public List<I> getItems() {
        return items;
    }

}
