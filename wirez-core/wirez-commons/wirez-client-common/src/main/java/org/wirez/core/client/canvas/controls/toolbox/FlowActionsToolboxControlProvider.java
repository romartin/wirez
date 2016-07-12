package org.wirez.core.client.canvas.controls.toolbox;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.DefaultToolboxCommandFactory;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewConnectorCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewNodeCommand;
import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;
import org.wirez.core.client.components.toolbox.ToolboxFactory;
import org.wirez.core.client.components.toolbox.builder.ToolboxBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.lookup.util.CommonLookups;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class FlowActionsToolboxControlProvider extends AbstractToolboxControlProvider {

    private static Logger LOGGER = Logger.getLogger( FlowActionsToolboxControlProvider.class.getName() );

    DefinitionUtils definitionUtils;
    CommonLookups commonLookups;
    DefaultToolboxCommandFactory defaultToolboxCommandFactory;

    protected FlowActionsToolboxControlProvider() {
        this ( null, null, null, null );
    }

    @Inject
    public FlowActionsToolboxControlProvider(final ToolboxFactory toolboxFactory,
                                             final DefinitionUtils definitionUtils,
                                             final DefaultToolboxCommandFactory defaultToolboxCommandFactory,
                                             final CommonLookups commonLookups ) {
        super( toolboxFactory );
        this.definitionUtils = definitionUtils;
        this.defaultToolboxCommandFactory = defaultToolboxCommandFactory;
        this.commonLookups = commonLookups;
    }

    @Override
    public boolean supports( final Object definition ) {
        return true;
    }

    @Override
    public ToolboxButtonGrid getGrid( final AbstractCanvasHandler context,
                                      final Element item ) {

        final ToolboxButtonGridBuilder buttonGridBuilder = toolboxFactory.toolboxGridBuilder();

        return buttonGridBuilder
                .setRows( 5 )
                .setColumns( 2 )
                .setIconSize( 12 )
                .setPadding( 5 )
                .build();
    }

    @Override
    public ToolboxBuilder.Direction getOn() {
        return ToolboxBuilder.Direction.NORTH_EAST;
    }

    @Override
    public ToolboxBuilder.Direction getTowards() {
        return ToolboxBuilder.Direction.SOUTH_EAST;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ToolboxCommand<?, ?>> getCommands( final AbstractCanvasHandler context,
                                                   final Element item ) {

        final Diagram diagram = context.getDiagram();

        final String defSetId = diagram.getSettings().getDefinitionSetId();

        final String defaultConnectorId = definitionUtils.getDefaultConnectorId( defSetId );

        if ( null != defaultConnectorId ) {

            final List<ToolboxCommand<?, ?>> commands = new LinkedList<>();

            final NewConnectorCommand<?> newConnectorCommand = defaultToolboxCommandFactory.newConnectorCommand();
            newConnectorCommand.setEdgeIdentifier( defaultConnectorId );

            commands.add( newConnectorCommand );

            // TODO: Handle all response buckets/pages.
            final Set<String> allowedMorphDefaultDefinitionIds =
                    commonLookups.getAllowedMorphDefaultDefinitions(
                            defSetId,
                            diagram.getGraph(),
                            (Node<? extends Definition<Object>, ? extends Edge>) item,
                            defaultConnectorId,
                            0,
                            10
                    );

            if ( null != allowedMorphDefaultDefinitionIds && !allowedMorphDefaultDefinitionIds.isEmpty() ) {

                for ( final String allowedDefId : allowedMorphDefaultDefinitionIds ) {

                    final NewNodeCommand newNodeCommand = defaultToolboxCommandFactory.newNodeCommand();
                    newNodeCommand.setDefinitionIdentifier( allowedDefId );

                    commands.add( newNodeCommand );

                }


            }

            return commands;

        }

        return null;

    }

    private void log( final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( Level.SEVERE, "** FLOW-ACTIONS-TOOLBOX ** " + message );
        }
    }

}
