package org.wirez.core.client.canvas.controls.toolbox;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.actions.RemoveToolboxCommand;
import org.wirez.core.client.components.toolbox.ToolboxButtonGrid;
import org.wirez.core.client.components.toolbox.ToolboxFactory;
import org.wirez.core.client.components.toolbox.builder.ToolboxBuilder;
import org.wirez.core.client.components.toolbox.builder.ToolboxButtonGridBuilder;
import org.wirez.core.graph.Element;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class ActionsToolboxControlProvider extends AbstractToolboxControlProvider {

    RemoveToolboxCommand removeToolboxCommand;

    protected ActionsToolboxControlProvider() {
        this ( null, null );
    }

    @Inject
    public ActionsToolboxControlProvider( final ToolboxFactory toolboxFactory,
                                          final RemoveToolboxCommand removeToolboxCommand ) {
        super( toolboxFactory );
        this.removeToolboxCommand = removeToolboxCommand;
    }

    @Override
    public boolean supports( final Object definition ) {
        return true;
    }

    @Override
    public ToolboxButtonGrid getGrid( final AbstractCanvasHandler context,
                                      final Element item) {

        final ToolboxButtonGridBuilder buttonGridBuilder = toolboxFactory.toolboxGridBuilder();

        return buttonGridBuilder
                .setRows( 2 )
                .setColumns( 1 )
                .build();
    }

    @Override
    public ToolboxBuilder.Direction getOn() {
        return ToolboxBuilder.Direction.NORTH_WEST;
    }

    @Override
    public ToolboxBuilder.Direction getTowards() {
        return ToolboxBuilder.Direction.SOUTH_WEST;
    }

    @Override
    public List<ToolboxCommand<?, ?>> getCommands( final AbstractCanvasHandler context,
                                                   final Element item ) {

        return new LinkedList<ToolboxCommand<?, ?>>() {{

            add( removeToolboxCommand );

        }};

    }

}
