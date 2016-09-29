package org.kie.workbench.common.stunner.core.registry.command;

import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.registry.SizeConstrainedRegistry;
import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

import java.util.Collection;

public interface CommandRegistry<C extends Command> extends DynamicRegistry<C>, SizeConstrainedRegistry {

    void register( Collection<C> command );

    Iterable<Iterable<C>> getCommandHistory();

    Iterable<C> peek();

    Iterable<C> pop();

    int getCommandHistorySize();

}
