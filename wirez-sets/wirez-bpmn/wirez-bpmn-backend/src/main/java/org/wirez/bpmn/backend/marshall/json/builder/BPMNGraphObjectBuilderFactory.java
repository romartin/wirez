package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.*;
import org.wirez.bpmn.backend.marshall.json.builder.edges.SequenceFlowBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.BPMNDiagramBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.activities.TaskBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndNoneEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.EdgeFactory;
import org.wirez.core.api.graph.factory.NodeFactory;
import org.wirez.core.api.service.definition.DefinitionService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    @Inject
    DefaultGraphFactory<? extends Definition> graphFactory;

    @Inject
    NodeFactory<? extends Definition> nodeFactory;

    @Inject
    EdgeFactory<? extends Definition> edgeFactory;
    
    @Inject
    DefinitionManager definitionManager;
    
    @Inject
    DefinitionService definitionService;
    
    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder(this);
    }
    
    public GraphObjectBuilder<?, ?> builderFor(String id) {

        /* Nodes */
        if (BPMNDiagram.ID.equals(id)) {
            return new BPMNDiagramBuilder(this);
        } else if (StartNoneEvent.ID.equals(id)) {
            return new StartNoneEventBuilder(this);
        } else if (EndNoneEvent.ID.equals(id)) {
            return new EndNoneEventBuilder(this);
        } else if (Task.ID.equals(id)) {
            return new TaskBuilder(this);
        }

        /* Edges */
        if (SequenceFlow.ID.equals(id)) {
            return new SequenceFlowBuilder(this);
        }
        
        throw new RuntimeException("No builder for stencil '" + id + "'.");
    }

    public DefaultGraphFactory<? extends Definition> getGraphFactory() {
        return graphFactory;
    }

    public NodeFactory<? extends Definition> getNodeFactory() {
        return nodeFactory;
    }

    public EdgeFactory<? extends Definition> getEdgeFactory() {
        return edgeFactory;
    }

    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    public DefinitionService getDefinitionService() {
        return definitionService;
    }
}
