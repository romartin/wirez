package org.kie.workbench.common.stunner.core.client.components.palette.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPaletteGroupBuilder<B, G, I>  extends AbstractPaletteItemBuilder<B, G>
        implements PaletteGroupBuilder<B, G, PaletteItemBuilder<?, I>> {

    protected String definitionId;
    protected final List<PaletteItemBuilder<?, I>> items = new ArrayList<PaletteItemBuilder<?, I>>();

    public AbstractPaletteGroupBuilder(final String id ) {
        super( id );
    }

    @Override
    @SuppressWarnings("unchecked")
    public B addItem( PaletteItemBuilder item ) {
        items.add( item );
        return (B) this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public B addItem( final int index,
                      final PaletteItemBuilder item ) {

        if ( index < items.size() ) {

            items.add( index, item );
            return (B) this;

        } else {

            return addItem( item );
        }

    }

    @Override
    public PaletteItemBuilder getItem( final String id ) {
        for ( final PaletteItemBuilder<?, I> item : items ) {
            if ( item.getId().equals( id) ) {
                return item;
            }
        }

        return null;
    }

    public AbstractPaletteGroupBuilder<B, G, I> definitionId( final String definitionId ) {
        this.definitionId = definitionId;
        return this;
    }

    protected abstract G doBuild( List<I> items );

    @Override
    public G build() {

        final List<I> result = new LinkedList<>();

        for ( final PaletteItemBuilder<?, I> itemBuilder : items ) {

            result.add( itemBuilder.build() );

        }

        return doBuild( result );
    }

}
