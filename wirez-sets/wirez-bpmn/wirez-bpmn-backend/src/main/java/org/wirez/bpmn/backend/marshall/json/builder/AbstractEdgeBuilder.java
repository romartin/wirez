package org.wirez.bpmn.backend.marshall.json.builder;


import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.command.CommandResult;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.impl.AddNodeCommand;
import org.wirez.core.graph.command.impl.SetConnectionTargetNodeCommand;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.RuleViolation;

// TODO: Improve error handling.
public abstract class AbstractEdgeBuilder<W, T extends Edge<View<W>, Node>> 
        extends AbstractObjectBuilder<W, T> implements EdgeObjectBuilder<W, T> {

    protected final Class<?> definitionClass;
            
    public AbstractEdgeBuilder(Class<?> definitionClass) {
        this.definitionClass = definitionClass;
    }

    @Override
    public Class<?> getDefinitionClass() {
        return definitionClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T doBuild(BuilderContext context) {

        FactoryManager factoryManager = context.getFactoryManager();

        String definitionId = context.getOryxManager().getMappingsManager().getDefinitionId( definitionClass );
        T result = (T) factoryManager.newElement(this.nodeId, definitionId);

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
                
                CommandResult<RuleViolation> results1 = context.execute( addNodeCommand );
                if ( hasErrors(results1) ) {
                    throw new RuntimeException("Error building BPMN graph. Command 'addNodeCommand' execution failed.");
                }

                CommandResult<RuleViolation> results2 = context.execute( setTargetNodeCommand );
                if ( hasErrors(results2) ) {
                    throw new RuntimeException("Error building BPMN graph. Command 'SetConnectionTargetNodeCommand' execution failed.");
                }
            }
        }

    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString()).append(" [defClass=").append(definitionClass.getName()).append("] ").toString();
    }
}
