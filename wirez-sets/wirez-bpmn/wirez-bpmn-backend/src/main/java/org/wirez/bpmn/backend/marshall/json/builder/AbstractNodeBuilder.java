package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.AddChildNodeCommand;
import org.wirez.core.api.graph.command.impl.SetConnectionSourceNodeCommand;
import org.wirez.core.api.graph.content.view.*;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.graph.util.GraphUtils;

import java.util.LinkedHashSet;
import java.util.Set;

// TODO: Improve error handling.
public abstract class AbstractNodeBuilder<W, T extends Node<View<W>, Edge>> 
        extends AbstractObjectBuilder<W, T> implements NodeObjectBuilder<W, T> {

    protected Set<String> childNodeIds;
    
    public AbstractNodeBuilder() {
        super();
        this.childNodeIds = new LinkedHashSet<String>();
    }

    @Override
    public AbstractNodeBuilder<W, T> child(String nodeId) {
        childNodeIds.add(nodeId);
        return this;
    }
    
    @Override
    protected T doBuild(BuilderContext context) {

        FactoryManager factoryManager = context.getFactoryManager();

        // Build the graph node for the definition.
        T result = (T) factoryManager.element(this.nodeId, getDefinitionId());
        
        // Set the def properties.
        setProperties(context, (BPMNDefinition) result.getContent().getDefinition());
        
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
        final Double[] size = GraphUtils.getSize(node.getContent());
        setSize(context, node, size[0], size[1]);
    }

    protected void setSize(BuilderContext context, T node, double width, double height) {
        // Do nothing by default.
    }

    @SuppressWarnings("unchecked")
    protected void afterNodeBuild(BuilderContext context, T node) {
        
        // Outgoing connections.
        if (outgoingResourceIds != null && !outgoingResourceIds.isEmpty()) {
            for (String outgoingNodeId : outgoingResourceIds) {
                GraphObjectBuilder<?, ?> outgoingBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingBuilder == null) {
                    throw new RuntimeException("No outgoing edge builder for " + outgoingNodeId);
                }

                // Create the outgoing edge.
                Edge edge = (Edge) outgoingBuilder.build(context);

                // Command - Execute the graph command to set the node as the edge connection's source..  
                int magnetIdx = getSourceConnectionMagnetIndex(context, node, edge);
                SetConnectionSourceNodeCommand setSourceNodeCommand = context.getCommandFactory().SET_SOURCE_NODE(node, edge, magnetIdx);
                CommandResults<RuleViolation> results = context.execute( setSourceNodeCommand );
                if ( hasErrors(results) ) {
                    throw new RuntimeException("Error building BPMN graph. Command execution failed.");
                }
                
            }
        }

        // Children connections.
        if (childNodeIds != null && !childNodeIds.isEmpty()) {
            for (String childNodeId : childNodeIds) {
                GraphObjectBuilder<?, ?> childNodeBuilder = getBuilder(context, childNodeId);
                if (childNodeBuilder == null) {
                    throw new RuntimeException("No child node builder for " + childNodeId);
                }

                if ( childNodeBuilder instanceof NodeObjectBuilder ) {
                    Node childNode = (Node) childNodeBuilder.build(context);
                    
                    // Command - Create the child node and the parent-child relationship.
                    AddChildNodeCommand addChildNodeCommand = context.getCommandFactory().ADD_CHILD_NODE(context.getGraph(), node, childNode);
                    CommandResults<RuleViolation> results = context.execute( addChildNodeCommand );
                    if ( hasErrors(results) ) {
                        throw new RuntimeException("Error building BPMN graph. Command 'AddChildNodeCommand' execution failed.");
                    }
                }
                
            }
        }
    }
    
    public int getSourceConnectionMagnetIndex(BuilderContext context, T node, Edge<ViewConnector<W>, Node> edge) {
        return 3;
    }

    public int getTargetConnectionMagnetIndex(BuilderContext context, T node, Edge<ViewConnector<W>, Node> edge) {
        return 7;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString()).append(" [childrenIds=").append(childNodeIds).append("] ").toString();
    }
    
}
