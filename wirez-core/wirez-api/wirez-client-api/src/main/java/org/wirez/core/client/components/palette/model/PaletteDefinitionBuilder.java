package org.wirez.core.client.components.palette.model;

public interface PaletteDefinitionBuilder<T, P, E> {

    interface Callback<P, E> {

        void onSuccess( P palette );

        void onError( E error );

    }

    /**
     * Exclude the given Definition identifier from appearing on the palette.
     * @param definitionId The Definition identifier to exclude.
     * @return The builder instance.
     */
    PaletteDefinitionBuilder<T, P, E> exclude( String definitionId );

    /**
     * Build the palette from source. Results present on the callback argument, as palette definition could be
     * build on server side.
     */
    void build( T source, Callback<P, E> callback );

}
