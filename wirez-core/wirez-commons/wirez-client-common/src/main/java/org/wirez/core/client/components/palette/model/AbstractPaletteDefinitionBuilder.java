package org.wirez.core.client.components.palette.model;

import org.wirez.core.client.components.palette.view.PaletteGrid;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPaletteDefinitionBuilder<T, P, E>
        implements PaletteDefinitionBuilder<T, P, E> {

    protected final List<String> exclusions = new LinkedList<>();

    public AbstractPaletteDefinitionBuilder() {
    }

    protected String toValidId(final String s ) {
        return s;
    }

    @Override
    public PaletteDefinitionBuilder<T, P, E> exclude( final String definitionId ) {
        this.exclusions.add(  definitionId );
        return this;
    }

    protected <I extends PaletteItemBuilder> I getItemBuilder( final List<I> items, final String id ) {

        for ( final PaletteItemBuilder item : items ) {

            if ( item.getId().equals( id ) ) {

                return (I) item;

            }

        }

        return null;
    }

}
