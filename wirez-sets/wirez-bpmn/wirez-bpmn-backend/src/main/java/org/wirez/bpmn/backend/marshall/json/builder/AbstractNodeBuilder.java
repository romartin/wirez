package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.BoundImpl;
import org.wirez.core.api.graph.impl.BoundsImpl;
import org.wirez.core.api.graph.impl.EdgeImpl;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.api.util.UUID;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractNodeBuilder<W extends Definition, T extends Node<ViewContent<W>, Edge>> 
        extends AbstractObjectBuilder<W, T> implements NodeObjectBuilder<W, T> {

    protected Set<String> childNodeIds;
    
    public AbstractNodeBuilder(BPMNGraphObjectBuilderFactory wiresFactory) {
        super(wiresFactory);
        this.childNodeIds = new LinkedHashSet<String>();
    }

    @Override
    public AbstractNodeBuilder<W, T> child(String nodeId) {
        childNodeIds.add(nodeId);
        return this;
    }
    
    protected abstract T buildNode(BuilderContext context, DefinitionService definitionService);
    
    @Override
    protected T doBuild(BuilderContext context) {

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
            Bounds bounds = new BoundsImpl(
                    new BoundImpl( boundLR[0], boundLR[1]),
                    new BoundImpl( boundUL[0], boundUL[1]));
            node.getContent().setBounds(bounds);
            setSize(context, node);
        }
    }

    protected void setSize(BuilderContext context, T node) {
        final Double[] size = ElementUtils.getSize(node.getContent());
        setSize(context, node, size[0], size[1]);
    }

    protected void setSize(BuilderContext context, T node, double width, double height) {
        // Do nothing by default.
    }

    protected void afterNodeBuild(BuilderContext context, T node) {
        
        // Outgoing connections.
        if (outgoingNodeIds != null && !outgoingNodeIds.isEmpty()) {
            for (String outgoingNodeId : outgoingNodeIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No outgoing edge builder for " + outgoingNodeId);
                }

                Edge edge = (Edge) outgoingNodeBuilder.build(context);
                node.getOutEdges().add(edge);
                edge.setSourceNode(node);

                if ( edge.getContent() instanceof ConnectionContent ) {
                    setTargetConnectionMagnetIndex(context, node, edge);
                }
            }
        }

        // Children connections.
        if (childNodeIds != null && !childNodeIds.isEmpty()) {
            for (String childNodeId : childNodeIds) {
                GraphObjectBuilder<?, ?> childNodeBuilder = getBuilder(context, childNodeId);
                if (childNodeBuilder == null) {
                    throw new RuntimeException("No child builder for " + childNodeId);
                }

                if ( childNodeBuilder instanceof NodeObjectBuilder ) {
                    Node childNode = (Node) childNodeBuilder.build(context);
                    final Edge<ParentChildRelationship, Node> childEdge = new EdgeImpl<>(UUID.uuid(), new HashSet<>(), new HashSet<>(), new ParentChildRelationship());
                    childEdge.setSourceNode(node);
                    childEdge.setTargetNode(childNode);
                    context.getGraph().addNode(childNode);
                    node.getOutEdges().add(childEdge);
                    childNode.getInEdges().add(childEdge);
                }
                
            }
        }
    }
   
    public void setSourceConnectionMagnetIndex(BuilderContext context, T node, Edge<ConnectionContent<W>, Node> edge) {
        edge.getContent().setSourceMagnetIndex(3);
    }

    public void setTargetConnectionMagnetIndex(BuilderContext context, T node, Edge<ConnectionContent<W>, Node> edge) {
        edge.getContent().setTargetMagnetIndex(7);
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString()).append(" [childrenIds=").append(childNodeIds).append("] ").toString();
    }
    
}
