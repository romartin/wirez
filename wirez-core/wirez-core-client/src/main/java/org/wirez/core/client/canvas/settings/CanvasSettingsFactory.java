package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.graph.processing.handler.GraphHandler;
import org.wirez.core.api.graph.processing.visitor.GraphVisitor;
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
    
    GraphHandler graphHandler;
    GraphVisitor graphVisitor;
    CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager;
    RuleManager ruleManager;
    ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor;
    ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor;

    @Inject
    public CanvasSettingsFactory(final GraphHandler graphHandler,
                                 final GraphVisitor graphVisitor,
                                 final CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager,
                                 final @Named( "default" ) RuleManager ruleManager, 
                                 final ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor,
                                 final ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor) {
        this.graphHandler = graphHandler;
        this.graphVisitor = graphVisitor;
        this.commandManager = commandManager;
        this.ruleManager = ruleManager;
        this.connectionAcceptor = connectionAcceptor;
        this.containmentAcceptor = containmentAcceptor;
    }
    
    public CanvasViewSettings getViewSettings() {
        return new CanvasViewSettingsBuilderImpl()
                .graphHandler(graphHandler)
                .graphVisitor(graphVisitor)
                .build();
    }

    public WiresCanvasSettings getDefaultSettings() {
        return new WiresCanvasSettingsBuilderImpl()
                .graphHandler(graphHandler)
                .graphVisitor(graphVisitor)
                .commandManager(commandManager)
                .ruleManager(ruleManager)
                .connectionAcceptor(connectionAcceptor)
                .containmentAcceptor(containmentAcceptor)
                .build();
    }
    
}
