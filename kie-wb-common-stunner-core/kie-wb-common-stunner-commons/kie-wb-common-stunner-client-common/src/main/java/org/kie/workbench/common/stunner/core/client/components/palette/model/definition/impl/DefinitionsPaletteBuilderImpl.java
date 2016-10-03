package org.kie.workbench.common.stunner.core.client.components.palette.model.definition.impl;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.components.palette.model.AbstractPaletteDefinitionBuilder;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionsPalette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionsPaletteBuilder;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Provides a palette builder for a DefinitionsPalette.
 */
@Dependent
public class DefinitionsPaletteBuilderImpl
        extends AbstractPaletteDefinitionBuilder<Iterable<String>, DefinitionsPalette, ClientRuntimeError>
        implements DefinitionsPaletteBuilder {

    DefinitionUtils definitionUtils;
    ClientFactoryServices clientFactoryServices;

    protected DefinitionsPaletteBuilderImpl() {
        this( null, null );
    }

    @Inject
    public DefinitionsPaletteBuilderImpl(final DefinitionUtils definitionUtils,
                                         final ClientFactoryServices clientFactoryServices) {
        this.definitionUtils = definitionUtils;
        this.clientFactoryServices = clientFactoryServices;
    }


    @Override
    public void build( final Iterable<String> definitions,
                       final Callback<DefinitionsPalette, ClientRuntimeError> callback ) {

        if ( null != definitions ) {

            final List<DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder> builders =
                    new LinkedList<DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder>();

            for (final String definitionId : definitions) {

                if ( !exclusions.contains( definitionId ) ) {

                    clientFactoryServices.newDefinition( definitionId, new ServiceCallback<Object>() {

                        @Override
                        public void onSuccess( final Object definition ) {

                            final String id = toValidId( definitionId );

                            final String title = getDefinitionManager().adapters().forDefinition().getTitle( definition );

                            final String description = getDefinitionManager().adapters().forDefinition().getDescription( definition );

                            final DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder itemBuilder =
                                    new DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder( id )
                                            .definitionId( definitionId )
                                            .title( title )
                                            .description( description )
                                            .tooltip( description );

                            builders.add( itemBuilder );

                        }

                        @Override
                        public void onError(final ClientRuntimeError error) {

                            callback.onError(error);

                        }

                    });

                }

            }

            if ( ! builders.isEmpty() ) {

                final List<DefinitionPaletteItem> paletteItems = new LinkedList<DefinitionPaletteItem>();

                for ( final DefinitionPaletteItemImpl.DefinitionPaletteItemBuilder builder :
                        builders ) {

                    paletteItems.add( builder.build() );

                }

                final DefinitionsPaletteImpl definitionsPalette = new DefinitionsPaletteImpl( paletteItems );

                callback.onSuccess( definitionsPalette );

            } else {

                callback.onError( new ClientRuntimeError( "No categories found." ) );

            }

        } else {

            callback.onError( new ClientRuntimeError( "Missing definitions argument." ) );

        }

    }

    @Override
    public void buildFromDefinitionSet( final String defintionSetId,
                       final Callback<DefinitionsPalette, ClientRuntimeError> callback ) {

        final Object defSet = getDefinitionManager().definitionSets().getDefinitionSetById( defintionSetId );
        final Set<String> definitions = getDefinitionManager().adapters().forDefinitionSet().getDefinitions( defSet );

        build( definitions, callback );

    }

    @Override
    public void buildFromPaletteItems( final List<DefinitionPaletteItem> definitionPaletteItems,
                                       final Callback<DefinitionsPalette, ClientRuntimeError> callback) {

        final DefinitionsPalette result = new DefinitionsPaletteImpl( definitionPaletteItems );

        callback.onSuccess( result );
    }

    protected DefinitionManager getDefinitionManager() {

        return definitionUtils.getDefinitionManager();

    }

}
