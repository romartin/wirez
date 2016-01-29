package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.service.definition.DefinitionService;

public abstract class AbstractEdgeBuilder<W extends Definition, T extends Edge<ViewContent<W>, Node>> 
        extends AbstractObjectBuilder<W, T> implements EdgeObjectBuilder<W, T> {

    public AbstractEdgeBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }
    
    protected abstract T buildEdge(BuilderContext context, DefinitionService definitionService);

    @Override
    public T build(BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();

        T result = buildEdge(context, definitionService);

        setProperties((BPMNDefinition) result.getContent().getDefinition());

        afterEdgeBuild(context, result);

        return result;
    }

    protected void afterEdgeBuild(BuilderContext context, T edge) {
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                Node node = (Node) outgoingNodeBuilder.build(context);
                edge.setTargetNode(node);
                node.getInEdges().add(edge);
                context.getGraph().addNode(node);
            }
        }

    }

    @Override
    public String toString() {
        return "[NodeBuilder=BpmnEdgeImplNodeBuilder]" + super.toString();
    }
}
