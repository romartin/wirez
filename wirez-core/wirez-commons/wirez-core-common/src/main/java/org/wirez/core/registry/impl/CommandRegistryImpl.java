package org.wirez.core.registry.impl;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import org.wirez.core.command.Command;
import org.wirez.core.registry.command.CommandRegistry;
import org.wirez.core.registry.exception.RegistrySizeExceededException;

/**
 * The Stack class behavior when using the iterator is not the expected one, so used
 * ArrayDeque instead of an Stack to provide right iteration order.
 */
public class CommandRegistryImpl<C extends Command> implements CommandRegistry<C> {

    private final Deque<Iterable<C>> commands = new ArrayDeque<>();
    private int maxStackSize = 50;

    @Override
    public void setMaxSize(final int size) {
        this.maxStackSize = size;
    }

    @Override
    public void register(final Collection<C> commands) {
        addIntoStack(commands);
    }

    @Override
    public void register(final C command) {
        addIntoStack(command);
    }

    @Override
    public boolean remove(final C command) {
        throw new UnsupportedOperationException("Remove not implemented yet.");
    }

    @Override
    public void clear() {
        commands.clear();
    }

    @Override
    public boolean contains(final C item) {
        throw new UnsupportedOperationException("Contains not implemented yet.");
    }

    @Override
    public Iterable<Iterable<C>> getCommandHistory() {
        return commands;
    }

    @Override
    public Iterable<C> peek() {
        return commands.peek();
    }

    @Override
    public Iterable<C> pop() {
        return commands.pop();
    }

    @Override
    public int getCommandHistorySize() {
        return commands.size();
    }

    @Override
    public Collection<C> getItems() {
        throw new UnsupportedOperationException("getItems not implemented yet. Use getCommandHistory() for now.");
    }

    private void addIntoStack(final C command) {
        if (null != command) {
            if ((commands.size() + 1) > maxStackSize) {
                stackSizeExceeded();
            }
            final Deque<C> s = new ArrayDeque<>();
            s.push(command);
            commands.push(s);
        }
    }

    private void addIntoStack(final Collection<C> _commands) {
        if (null != _commands && !_commands.isEmpty()) {
            if ((commands.size() + _commands.size()) > maxStackSize) {
                stackSizeExceeded();
            }
            final Deque<C> s = new ArrayDeque<>();
            _commands.forEach(s::push);
            commands.push(s);
        }
    }

    private void stackSizeExceeded() {
        throw new RegistrySizeExceededException(maxStackSize);
    }

}
