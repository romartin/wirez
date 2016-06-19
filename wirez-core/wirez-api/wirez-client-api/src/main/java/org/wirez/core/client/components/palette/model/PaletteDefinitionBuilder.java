package org.wirez.core.client.components.palette.model;

public interface PaletteDefinitionBuilder<T, P, E> {

    interface Callback<P, E> {

        void onSuccess( P palette );

        void onError( E error );

    }

    void build( T source, Callback<P, E> callback );

}
