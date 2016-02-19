package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.graph.processing.index.GraphIndex;
import org.wirez.core.api.graph.processing.index.map.MapGraphIndexBuilder;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkContentVisitor;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class CanvasSettingsFactory {
    
    TreeWalkContentVisitor visitor;
    MapGraphIndexBuilder indexBuilder;
    CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager;
    RuleManager ruleManager;
    ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor;
    ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor;

    @Inject
    public CanvasSettingsFactory(final TreeWalkContentVisitor visitor,
                                 final MapGraphIndexBuilder indexBuilder,
                                 final CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager,
                                 final @Named( "default" ) RuleManager ruleManager, 
                                 final ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor,
                                 final ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor) {
        this.visitor = visitor;
        this.indexBuilder = indexBuilder;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
        this.connectionAcceptor = connectionAcceptor;
        this.containmentAcceptor = containmentAcceptor;
    }
    
    public CanvasViewSettings getViewSettings() {
        return new CanvasViewSettingsBuilderImpl()
                .indexBuilder( (GraphIndex<?, ?>) indexBuilder)
                .visitor(visitor)
                .build();
    }

    public WiresCanvasSettings getDefaultSettings() {
        return new WiresCanvasSettingsBuilderImpl()
                .indexBuilder( (GraphIndex<?, ?>) indexBuilder)
                .visitor(visitor)
                .commandManager(commandManager)
                .ruleManager(ruleManager)
                .connectionAcceptor(connectionAcceptor)
                .containmentAcceptor(containmentAcceptor)
                .build();
    }
    
}
