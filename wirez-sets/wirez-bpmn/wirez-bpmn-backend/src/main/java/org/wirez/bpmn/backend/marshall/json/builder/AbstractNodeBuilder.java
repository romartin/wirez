package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultBound;
import org.wirez.core.api.graph.impl.DefaultBounds;
import org.wirez.core.api.service.definition.DefinitionService;

public abstract class AbstractNodeBuilder<W extends Definition, T extends Node<ViewContent<W>, Edge>> 
        extends AbstractObjectBuilder<W, T> implements NodeObjectBuilder<W, T> {
    
    public AbstractNodeBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
    }

    protected abstract T buildNode(BuilderContext context, DefinitionService definitionService);
    
    @Override
    public T build(BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();

        // Build the graph node for the definition.
        T result = buildNode(context, definitionService);
        
        // Set the def properties.
        setProperties((BPMNDefinition) result.getContent().getDefinition());
        
        // View Bounds.
        setBounds(context, result);
        
        // Post processing.
        afterNodeBuild(context, result);
        
        return result;
    }
    
    protected void setBounds(BuilderContext context, T node) {
        if ( null != boundUL && null != boundLR ) {
            Bounds bounds = new DefaultBounds(
                    new DefaultBound( boundLR[0], boundLR[1]),
                    new DefaultBound( boundUL[0], boundUL[1]));
            node.getContent().setBounds(bounds);
        }
    }

    protected void afterNodeBuild(BuilderContext context, T node) {
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                Edge edge = (Edge) outgoingNodeBuilder.build(context);
                node.getOutEdges().add(edge);
                edge.setSourceNode(node);

                if ( edge.getContent() instanceof ConnectionContent ) {
                    setTargetConnectionMagnetIndex(context, node, edge);
                }
                
                // TODO: context.getGraph().addEdge(edge); ?
            }
        }
    }

    public void setSourceConnectionMagnetIndex(BuilderContext context, T node, Edge<ConnectionContent<W>, Node> edge) {

    }

    public void setTargetConnectionMagnetIndex(BuilderContext context, T node, Edge<ConnectionContent<W>, Node> edge) {
        
    }
    
}
