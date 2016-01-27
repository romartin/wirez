package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;

import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.NodeObjectBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class StartNoneEventBuilder extends AbstractObjectBuilder<StartNoneEvent, Node<ViewContent<StartNoneEvent>, Edge>> implements NodeObjectBuilder<StartNoneEvent> {
    
    public StartNoneEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public Node build(GraphObjectBuilder.BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();
        
        Node result = (Node) definitionService.buildGraphElement(StartNoneEvent.ID);
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                Edge edge = (Edge) outgoingNodeBuilder.build(context);
                result.getOutEdges().add(edge);
                edge.setSourceNode(result);
                
                // TODO: context.getGraph().addEdge(edge); ?
            }
        }
        
        return result;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=StartProcessNode]" + super.toString();
    }
}
