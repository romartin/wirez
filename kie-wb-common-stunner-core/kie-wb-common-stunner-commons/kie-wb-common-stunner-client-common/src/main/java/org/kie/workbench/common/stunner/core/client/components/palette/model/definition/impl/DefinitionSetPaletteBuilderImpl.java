package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteDefinitionBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteCategory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionSetPaletteBuilder;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinition;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;

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

    private PaletteCategoryProvider paletteCategoryProvider;
    private PaletteMorphGroupProvider paletteMorphGroupProvider;

    protected DefinitionSetPaletteBuilderImpl() {
        this( null, null );
    }

    @Inject
    public DefinitionSetPaletteBuilderImpl(final DefinitionUtils definitionUtils,
                                           final ClientFactoryServices clientFactoryServices) {

        this.definitionUtils = definitionUtils;
        this.clientFactoryServices = clientFactoryServices;
        this.paletteCategoryProvider = CATEGORY_PROVIDER;
        this.paletteMorphGroupProvider = MORPH_GROUP_PROVIDER;
    }

    public void build( final Object definitionSet,
                       final Callback<DefinitionSetPalette, ClientRuntimeError> callback ) {

        final Object definitionSetObject = definitionSet instanceof String ?
                getDefinitionManager().definitionSets().getDefinitionSetById( (String) definitionSet ) : definitionSet;

        final String defSetId = getDefinitionManager().adapters().forDefinitionSet().getId( definitionSetObject );

        final Collection<String> definitions = getDefinitionManager().adapters().forDefinitionSet().getDefinitions( definitionSetObject );

        if ( null != definitions ) {

            final List<DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder> categoryBuilders = new LinkedList<>();

            for (final String defId : definitions) {

                if ( !exclusions.contains( defId ) ) {

                    clientFactoryServices.newDefinition(defId, new ServiceCallback<Object>() {

                        @Override
                        public void onSuccess( final Object definition ) {

                            final String id = getDefinitionManager().adapters().forDefinition().getId( definition );

                            final String category = getDefinitionManager().adapters().forDefinition().getCategory( definition );

                            final String categoryId = toValidId( category );

                            DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder categoryGroupBuilder =
                                    getItemBuilder( categoryBuilders, categoryId );

                            if ( null == categoryGroupBuilder ) {

                                categoryGroupBuilder =
                                        new DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder( categoryId )
                                                .title( paletteCategoryProvider.getTitle( categoryId ) )
                                                .tooltip( paletteCategoryProvider.getTitle( categoryId ) )
                                                .description( paletteCategoryProvider.getDescription( categoryId ) );

                                categoryBuilders.add( categoryGroupBuilder );
                            }


                            final MorphDefinition morphDefinition = definitionUtils.getMorphDefinition( definition );

                            final boolean hasMorphBase = null != morphDefinition;

                            DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder morphGroupBuilder = null;

                            String morphDefault = null;

                            if ( hasMorphBase ) {

                                final String morphBase = morphDefinition.getBase();

                                morphDefault = morphDefinition.getDefault();

                                final String morphBaseId = toValidId( morphBase );

                                morphGroupBuilder = (DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder) categoryGroupBuilder.getItem( morphBaseId );

                                if ( null == morphGroupBuilder ) {

                                    morphGroupBuilder =
                                            new DefinitionPaletteGroupImpl.DefinitionPaletteGroupBuilder( morphBaseId )
                                                    .definitionId( morphDefault )
                                                    .title( paletteMorphGroupProvider.getTitle( morphBase, morphDefinition ) )
                                                    .description( paletteMorphGroupProvider.getDescription( morphBase, morphDefinition ) )
                                                    .tooltip( paletteMorphGroupProvider.getTitle( morphBase, morphDefinition ) );

                                    categoryGroupBuilder.addItem( morphGroupBuilder );

                                }

                            }

                            final String title = getDefinitionManager().adapters().forDefinition().getTitle( definition );
                            final String description = getDefinitionManager().adapters().forDefinition().getDescription( definition );

                            final DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder itemBuilder =
                                    new DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder( id )
                                            .definitionId( id )
                                            .title( title )
                                            .description( description )
                                            .tooltip( description );


                            if ( null != morphGroupBuilder ) {

                                if ( null != morphDefault && morphDefault.equals( id ) ) {

                                    morphGroupBuilder.addItem( 0, itemBuilder );

                                } else {

                                    morphGroupBuilder.addItem( itemBuilder );

                                }

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

            }

            if ( ! categoryBuilders.isEmpty() ) {


                final List<DefinitionPaletteCategory> categories = new LinkedList<>();

                for ( final DefinitionPaletteCategoryImpl.DefinitionPaletteCategoryBuilder builder :
                        categoryBuilders ) {

                    categories.add( builder.build() );

                }

                final DefinitionSetPaletteImpl definitionPalette = new DefinitionSetPaletteImpl( categories, defSetId );

                callback.onSuccess( definitionPalette );

            } else  {

                callback.onError( new ClientRuntimeError( "No categories found." ) );

            }

        } else {

            callback.onError( new ClientRuntimeError( "Missing definition argument." ) );

        }


    }

    static final PaletteCategoryProvider CATEGORY_PROVIDER = new PaletteCategoryProvider() {

        @Override
        public String getTitle( final String id ) {
            return id;
        }

        @Override
        public String getDescription( final String id ) {
            return id;
        }

    };

    static final PaletteMorphGroupProvider MORPH_GROUP_PROVIDER = new PaletteMorphGroupProvider() {

        @Override
        public String getTitle( final String morphBaseId,
                                final Object definition ) {

            return morphBaseId;
        }

        @Override
        public String getDescription( final String morphBaseId,
                                      final Object definition ) {
            return morphBaseId;
        }

    };

    protected DefinitionManager getDefinitionManager() {

        return definitionUtils.getDefinitionManager();

    }


    @Override
    public DefinitionSetPaletteBuilder setCategoryProvider( final PaletteCategoryProvider categoryProvider ) {
        this.paletteCategoryProvider = categoryProvider;
        return this;
    }

    @Override
    public DefinitionSetPaletteBuilder setMorphGroupProvider( final PaletteMorphGroupProvider groupProvider ) {
        this.paletteMorphGroupProvider = groupProvider;
        return this;
    }

}
