package org.wirez.core.registry.command;

import org.wirez.core.command.Command;
import org.wirez.core.registry.DynamicRegistry;
import org.wirez.core.registry.SizeConstrainedRegistry;

import java.util.Collection;
import java.util.Stack;

public interface CommandRegistry<C extends Command> extends DynamicRegistry<C>, SizeConstrainedRegistry {

    void register( Collection<C> command );

    Iterable<Iterable<C>> getCommandHistory();

    Iterable<C> peek();

    Iterable<C> pop();

    int getCommandHistorySize();

}
