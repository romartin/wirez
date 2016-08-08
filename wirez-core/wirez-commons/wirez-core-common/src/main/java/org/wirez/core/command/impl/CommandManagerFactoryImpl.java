package org.wirez.core.command.impl;

import org.wirez.core.command.CommandManager;
import org.wirez.core.command.CommandManagerFactory;
import org.wirez.core.command.batch.BatchCommandManager;
import org.wirez.core.command.stack.StackCommandManager;
import org.wirez.core.registry.RegistryFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommandManagerFactoryImpl implements CommandManagerFactory {

    private final RegistryFactory registryFactory;

    protected CommandManagerFactoryImpl() {
        this( null );
    }

    @Inject
    public CommandManagerFactoryImpl( final RegistryFactory registryFactory ) {
        this.registryFactory = registryFactory;
    }

    @Override
    public <C, V> CommandManager<C, V> newCommandManager() {
        return new CommandManagerImpl<C, V>();
    }

    @Override
    public <C, V> BatchCommandManager<C, V> newBatchCommandManager() {
        return new BatchCommandManagerImpl<C, V>( this );
    }

    @Override
    public <C, V> StackCommandManager<C, V> newStackCommandManagerFor( final BatchCommandManager<C, V> commandManager ) {
        return new StackCommandManagerImpl<C, V>( registryFactory, commandManager );
    }
}
