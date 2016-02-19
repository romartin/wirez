package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.graph.processing.index.map.MapIndexBuilder;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkContentVisitor;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The default canvas handler's settings producer.
 */
@ApplicationScoped
public class CanvasSettingsFactory {
    
    TreeWalkContentVisitor visitor;
    IncrementalIndexBuilder indexBuilder;
    CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager;
    RuleManager ruleManager;
    ConnectionAcceptor<WiresCanvasHandler> connectionAcceptor;
    ContainmentAcceptor<WiresCanvasHandler> containmentAcceptor;

    @Inject
    public CanvasSettingsFactory(final TreeWalkContentVisitor visitor,
                                 final IncrementalIndexBuilder indexBuilder,
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
                .indexBuilder( (IndexBuilder<?, ?, ? ,?>) indexBuilder)
                .visitor(visitor)
                .build();
    }

    public WiresCanvasSettings getDefaultSettings() {
        return new WiresCanvasSettingsBuilderImpl()
                .indexBuilder( (IndexBuilder<?, ?, ? ,?>) indexBuilder)
                .visitor(visitor)
                .commandManager(commandManager)
                .ruleManager(ruleManager)
                .connectionAcceptor(connectionAcceptor)
                .containmentAcceptor(containmentAcceptor)
                .build();
    }
    
}
