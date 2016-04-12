package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.AddNodeCommand;
import org.wirez.core.api.graph.command.impl.SetConnectionTargetNodeCommand;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleViolation;

// TODO: Improve error handling.
public abstract class AbstractEdgeBuilder<W, T extends Edge<View<W>, Node>> 
        extends AbstractObjectBuilder<W, T> implements EdgeObjectBuilder<W, T> {

    protected Bpmn2OryxIdMappings oryxIdMappings;
    
    public AbstractEdgeBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super();
        this.oryxIdMappings = oryxIdMappings;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected T doBuild(BuilderContext context) {

        FactoryManager factoryManager = context.getFactoryManager();

        T result = (T) factoryManager.element(this.nodeId, getDefinitionId());

        setProperties(context, (BPMNDefinition) result.getContent().getDefinition());

        afterEdgeBuild(context, result);

        return result;
    }

    @SuppressWarnings("unchecked")
    protected void afterEdgeBuild(BuilderContext context, T edge) {
        
        // Outgoing connections.
        if (outgoingResourceIds != null && !outgoingResourceIds.isEmpty()) {
            for (String outgoingNodeId : outgoingResourceIds) {
                GraphObjectBuilder<?, ?> outgoingNodeBuilder = getBuilder(context, outgoingNodeId);
                if (outgoingNodeBuilder == null) {
                    throw new RuntimeException("No edge for " + outgoingNodeId);
                }

                Node node = (Node) outgoingNodeBuilder.build(context);

                // Command - Add the node into the graph store.
                AddNodeCommand addNodeCommand = context.getCommandFactory().ADD_NODE(context.getGraph(), node);

                // Command - Set the edge connection's target node.
                int magnetIdx = ( (AbstractNodeBuilder) outgoingNodeBuilder).getTargetConnectionMagnetIndex(context, node, edge);
                SetConnectionTargetNodeCommand setTargetNodeCommand = context.getCommandFactory().SET_TARGET_NODE(node, edge, magnetIdx);
                
                CommandResults<RuleViolation> results = context.execute( addNodeCommand, setTargetNodeCommand );
                if ( hasErrors(results) ) {
                    throw new RuntimeException("Error building BPMN graph. Commands 'addNodeCommand'/'SetConnectionTargetNodeCommand' execution failed.");
                }
            }
        }

    }

    @Override
    public String toString() {
        return "[NodeBuilder=BpmnEdgeImplNodeBuilder]" + super.toString();
    }
}
