package org.wirez.core.client.components.palette.model.definition.impl;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.components.palette.model.AbstractPaletteDefinitionBuilder;
import org.wirez.core.client.components.palette.model.PaletteItemBuilder;
import org.wirez.core.client.components.palette.model.definition.DefinitionPaletteCategory;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPaletteBuilder;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.definition.util.DefinitionUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides a palette builder for a DefinitionSetPalette.
 * Build method considers two arguments:
 *  - Object if it's the model object for the definition set
 *  - String if it's the definition set identifier
 *
 */
@Dependent
public class DefinitionSetPaletteBuilderImpl
        extends AbstractPaletteDefinitionBuilder<Object, DefinitionSetPalette, ClientRuntimeError>
        implements DefinitionSetPaletteBuilder {

    DefinitionUtils definitionUtils;
    ClientFactoryServices clientFactoryServices;

    protected DefinitionSetPaletteBuilderImpl() {
        this( null, null );
    }

    @Inject
    public DefinitionSetPaletteBuilderImpl(final DefinitionUtils definitionUtils,
                                           final ClientFactoryServices clientFactoryServices) {

        this.definitionUtils = definitionUtils;
        this.clientFactoryServices = clientFactoryServices;
    }

    public void build( final Object definitionSet,
                       final Callback<DefinitionSetPalette, ClientRuntimeError> callback ) {

        final Object definitionSetObject = definitionSet instanceof String ?
                getDefinitionManager().getDefinitionSet( (String) definitionSet ) : definitionSet;

        final Collection<String> definitions =
                getDefinitionManager().getDefinitionSetAdapter( definitionSetObject.getClass() ).getDefinitions( definitionSetObject );

        if ( null != definitions ) {

            final List<DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder> categoryBuilders = new LinkedList<>();

            for (final String defId : definitions) {

                clientFactoryServices.newDomainObject(defId, new ServiceCallback<Object>() {

                    @Override
                    public void onSuccess( final Object definition ) {

                        final String id = definitionUtils.getDefinitionId( definition );

                        final String category = definitionUtils.getDefinitionCategory( definition );

                        final String categoryId = toValidId( category );

                        DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder categoryGroupBuilder =
                                getItemBuilder( categoryBuilders, categoryId );

                        if ( null == categoryGroupBuilder ) {

                            categoryGroupBuilder =
                                    new DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder( categoryId )
                                    .title( category )
                                    .tooltip( category )
                                    .description( category );

                            categoryBuilders.add( categoryGroupBuilder );
                        }


                        final MorphDefinition morphDefinition = definitionUtils.getMorphDefinition( definition );

                        final boolean hasMorphBase = null != morphDefinition;

                        DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder morphGroupBuilder = null;

                        if ( hasMorphBase ) {

                            final String morphBase = morphDefinition.getBase();

                            final String morphDefault = morphDefinition.getDefault();

                            final String morphBaseId = toValidId( morphBase );

                            morphGroupBuilder = (DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder) categoryGroupBuilder.getItem( morphBaseId );

                            if ( null == morphGroupBuilder ) {

                                // TODO
                                final int cIndex = morphBase.lastIndexOf(".");
                                final String morphTitle = cIndex > -1 ?
                                        morphBase.substring( cIndex + 1, morphBase.length() ) : morphBase;

                                morphGroupBuilder =
                                        new DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder( morphBaseId )
                                                .definitionId( morphDefault )
                                                .title( morphTitle )
                                                .description( morphBase )
                                                .tooltip( morphBase );

                                categoryGroupBuilder.addItem( morphGroupBuilder );

                            }

                        }

                        final String title = definitionUtils.getDefinitionTitle( definition );
                        final String description = definitionUtils.getDefinitionDescription( definition );

                        final DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder itemBuilder =
                                new DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder( id )
                                .definitionId( id )
                                .title( title )
                                .description( description )
                                .tooltip( description );


                        if ( null != morphGroupBuilder ) {

                            morphGroupBuilder.addItem( itemBuilder );

                        } else {

                            categoryGroupBuilder.addItem( itemBuilder );

                        }

                    }

                    @Override
                    public void onError(final ClientRuntimeError error) {

                        callback.onError(error);

                    }

                });

            }

            if ( ! categoryBuilders.isEmpty() ) {


                final List<DefinitionPaletteCategory> categories = new LinkedList<>();

                for ( final DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder builder :
                        categoryBuilders ) {

                    categories.add( builder.build() );

                }

                final DefinitionSetPaletteImpl definitionPalette = new DefinitionSetPaletteImpl( categories );

                callback.onSuccess( definitionPalette );

            } else  {

                callback.onError( new ClientRuntimeError( "No categories found." ) );

            }

        } else {

            callback.onError( new ClientRuntimeError( "Missing definition argument." ) );

        }


    }


    protected DefinitionManager getDefinitionManager() {

        return definitionUtils.getDefinitionManager();

    }


}
