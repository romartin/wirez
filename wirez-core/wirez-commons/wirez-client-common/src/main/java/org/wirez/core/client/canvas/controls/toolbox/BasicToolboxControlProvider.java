package org.wirez.core.client.canvas.controls.toolbox;

import org.wirez.core.client.canvas.controls.toolbox.command.NameToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.RemoveToolboxCommand;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class BasicToolboxControlProvider implements ToolboxControlProvider<Object, Object> {

    protected NameToolboxCommand nameToolboxCommand;
    protected RemoveToolboxCommand removeToolboxCommand;

    @Inject
    public BasicToolboxControlProvider(final NameToolboxCommand nameToolboxCommand,
                                          final RemoveToolboxCommand removeToolboxCommand) {
        this.nameToolboxCommand = nameToolboxCommand;
        this.removeToolboxCommand = removeToolboxCommand;
    }

    @Override
    public boolean supports(final Object definition) {
        return true;
    }
    
    @Override
    public List<ToolboxCommand<?, Object>> getCommands(final Object item) {
        return new LinkedList<ToolboxCommand<?, Object>>() {{
            add( nameToolboxCommand );
            add( removeToolboxCommand );
        }};
    }

}
