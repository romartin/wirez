package org.wirez.bpmn.backend.marshall.json.builder.nodes.activities;

import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.NodeObjectBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class TaskBuilder extends AbstractObjectBuilder<Task, Node<ViewContent<Task>, Edge>> implements NodeObjectBuilder<Task> {

    public TaskBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    public Node<ViewContent<Task>, Edge> build(BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();

        Node result = (Node) definitionService.buildGraphElement(Task.ID);
        

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
        return "[NodeBuilder=TaskNodeBuilder]" + super.toString();
    }
}
