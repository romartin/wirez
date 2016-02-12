package org.wirez.bpmn.backend.marshall.json.builder.nodes.events;


import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.api.EndTerminateEvent;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public class EndTerminateEventBuilder extends AbstractNodeBuilder<EndTerminateEvent, Node<ViewContent<EndTerminateEvent>, Edge>> {
    
    public EndTerminateEventBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    @Override
    protected Node<ViewContent<EndTerminateEvent>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<EndTerminateEvent>, Edge>) definitionService.buildGraphElement(EndTerminateEvent.ID);
    }

    @Override
    protected void setSize(BuilderContext context, Node<ViewContent<EndTerminateEvent>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        EndTerminateEvent def = node.getContent().getDefinition();
        def.getRadius().setValue(width  / 2);
    }
    
    @Override
    protected void afterNodeBuild(BuilderContext context, Node<ViewContent<EndTerminateEvent>, Edge> node) {
        // Do nothing. No outgoing connections expected.
    }

    @Override
    public void setSourceConnectionMagnetIndex(BuilderContext context, Node<ViewContent<EndTerminateEvent>, Edge> node, Edge<ConnectionContent<EndTerminateEvent>, Node> edge) {
        super.setSourceConnectionMagnetIndex(context, node, edge);
        edge.getContent().setSourceMagnetIndex(3);

    }

    @Override
    public void setTargetConnectionMagnetIndex(BuilderContext context, Node<ViewContent<EndTerminateEvent>, Edge> node, Edge<ConnectionContent<EndTerminateEvent>, Node> edge) {
        super.setTargetConnectionMagnetIndex(context, node, edge);
        edge.getContent().setTargetMagnetIndex(7);
    }


    @Override
    public String toString() {
        return "[NodeBuilder=EndTerminateEventBuilder]" + super.toString();
    }
}
