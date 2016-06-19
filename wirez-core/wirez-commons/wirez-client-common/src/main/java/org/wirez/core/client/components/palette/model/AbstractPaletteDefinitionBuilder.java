package org.wirez.core.client.components.palette.model;

import org.wirez.core.client.components.palette.view.PaletteGrid;

import java.util.List;

public abstract class AbstractPaletteDefinitionBuilder<T, P, E>
        implements PaletteDefinitionBuilder<T, P, E> {

    public AbstractPaletteDefinitionBuilder() {
    }

    protected String toValidId(final String s ) {
        return s;
    }

    protected <I extends PaletteItemBuilder> I getItemBuilder(final List<I> items, final String id ) {

        for ( final PaletteItemBuilder item : items ) {

            if ( item.getId().equals( id ) ) {

                return (I) item;

            }

        }

        return null;
    }

}
