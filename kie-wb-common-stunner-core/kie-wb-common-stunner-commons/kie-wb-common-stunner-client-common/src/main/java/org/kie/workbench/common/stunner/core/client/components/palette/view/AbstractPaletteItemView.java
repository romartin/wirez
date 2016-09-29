package org.kie.workbench.common.stunner.core.client.components.palette.view;

import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteItem;

public abstract class AbstractPaletteItemView<I extends PaletteItem, V>
        implements PaletteItemView<I, V> {

    protected final I item;

    public AbstractPaletteItemView( final I item ) {
        this.item = item;
    }

    @Override
    public I getPaletteItem() {
        return item;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractPaletteItemView) ) {
            return false;
        }

        AbstractPaletteItemView that = (AbstractPaletteItemView) o;

        return item.getId().equals( that.item.getId() );
    }

}
