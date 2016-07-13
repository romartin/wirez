package org.wirez.core.client.components.palette.factory;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.wirez.core.client.service.ClientRuntimeError;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPaletteFactory<I  extends HasPaletteItems, P extends Palette<I>>
    implements PaletteFactory<I, P> {

    private static Logger LOGGER = Logger.getLogger( AbstractPaletteFactory.class.getName() );

    protected ShapeManager shapeManager;

    protected P palette;

    public AbstractPaletteFactory( final ShapeManager shapeManager,
                                   final P palette ) {
        this.shapeManager = shapeManager;
        this.palette = palette;
    }

    protected abstract PaletteDefinitionFactory<PaletteDefinitionBuilder<Object, I, ClientRuntimeError>> getPaletteDefinitionFactory( final String defSetId );

    @Override
    @SuppressWarnings( "unchecked" )
    public P newPalette( final String shapeSetId ) {

        final String defSetId = getShapeSet( shapeSetId ).getDefinitionSetId();

        final PaletteDefinitionFactory<PaletteDefinitionBuilder<Object, I, ClientRuntimeError>> paletteDefinitionFactory = getPaletteDefinitionFactory( defSetId );

        final PaletteDefinitionBuilder<Object, I, ClientRuntimeError> paletteDefinitionBuilder = paletteDefinitionFactory.newBuilder( defSetId );

        paletteDefinitionBuilder.build( defSetId, new PaletteDefinitionBuilder.Callback<I, ClientRuntimeError>() {

            @Override
            public void onSuccess( final I paletteDefinition ) {

                beforeBindPalette( paletteDefinition );

                palette.bind( paletteDefinition );

                afterBindPalette( paletteDefinition );

            }

            @Override
            public void onError( final ClientRuntimeError error ) {
                logError( error );
            }

        } );

        return palette;
    }

    protected void beforeBindPalette( final I paletteDefinition ) {

    }

    protected void afterBindPalette( final I paletteDefinition ) {

    }

    private ShapeSet getShapeSet( final String id) {
        for (final ShapeSet set : shapeManager.getShapeSets()) {
            if (set.getId().equals(id)) {
                return set;
            }
        }
        return null;
    }


    private void logError( final ClientRuntimeError error ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( Level.SEVERE, error.toString() );
        }
    }

}
