package org.wirez.core.client.canvas.controls.toolbox;

import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.palette.AbstractPaletteMorphCommand;
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
public class MorphToolboxControlProvider extends AbstractToolboxControlProvider {

    AbstractPaletteMorphCommand morphCommand;

    protected MorphToolboxControlProvider() {
        this ( null, null );
    }

    @Inject
    public MorphToolboxControlProvider(final ToolboxFactory toolboxFactory,
                                       final AbstractPaletteMorphCommand morphCommand ) {
        super( toolboxFactory );
        this.morphCommand = morphCommand;
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
                .setRows( 1 )
                .setColumns( 1 )
                .build();
    }

    @Override
    public ToolboxBuilder.Direction getOn() {
        return ToolboxBuilder.Direction.SOUTH_WEST;
    }

    @Override
    public ToolboxBuilder.Direction getTowards() {
        return ToolboxBuilder.Direction.SOUTH_EAST;
    }

    @Override
    public List<ToolboxCommand<?, ?>> getCommands( final AbstractCanvasHandler context,
                                                   final Element item ) {

        return new LinkedList<ToolboxCommand<?, ?>>() {{

            add( morphCommand );

        }};

    }

}
