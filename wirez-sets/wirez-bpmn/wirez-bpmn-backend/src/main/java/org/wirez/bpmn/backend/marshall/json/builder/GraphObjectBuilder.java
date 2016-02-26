package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.service.definition.DefinitionService;

import java.util.Collection;

public interface GraphObjectBuilder<W extends Definition, T extends Element<View<W>>> {
    
    String getDefinitionId();
    
    GraphObjectBuilder<W, T> nodeId(String nodeId);

    GraphObjectBuilder<W, T> stencil(String stencilId);

    GraphObjectBuilder<W, T> property(String key, String value);

    GraphObjectBuilder<W, T> out(String nodeId);

    GraphObjectBuilder<W, T> boundUL(Double x, Double y);

    GraphObjectBuilder<W, T> boundLR(Double x, Double y);
    
    // TODO: Dockers.
    
    T build(BuilderContext context);
    
    interface BuilderContext<W extends Definition> {

        void init(Graph<View<W>, Node> graph);

        Graph<View<W>, Node> getGraph();
        
        Collection<GraphObjectBuilder<?, ?>> getBuilders();

        DefinitionManager getDefinitionManager();
        
        DefinitionService getDefinitionService();
        
        GraphCommandFactory getCommandFactory();
        
        CommandResults<RuleViolation> execute (Command<RuleManager, RuleViolation>... command);
        
    }
    
}
