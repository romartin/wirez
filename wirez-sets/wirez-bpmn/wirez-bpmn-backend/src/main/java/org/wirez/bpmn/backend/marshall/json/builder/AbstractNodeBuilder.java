package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.bpmn.definition.property.Height;
import org.wirez.bpmn.definition.property.Radius;
import org.wirez.bpmn.definition.property.Width;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.content.view.*;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.RuleViolation;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO: Improve error handling.
public abstract class AbstractNodeBuilder<W, T extends Node<View<W>, Edge>> 
        extends AbstractObjectBuilder<W, T> implements NodeObjectBuilder<W, T> {

    protected final Class<?> definitionClass;
    protected Set<String> childNodeIds;
    
    public AbstractNodeBuilder(Class<?> definitionClass) {
        this.definitionClass = definitionClass;
        this.childNodeIds = new LinkedHashSet<String>();
    }

    @Override
    public Class<?> getDefinitionClass() {
        return definitionClass;
    }

    @Override
    public AbstractNodeBuilder<W, T> child(String nodeId) {
        childNodeIds.add(nodeId);
        return this;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected T doBuild(BuilderContext context) {

        FactoryManager factoryManager = context.getFactoryManager();

        // Build the graph node for the definition.
        String definitionId = context.getOryxManager().getMappingsManager().getDefinitionId( definitionClass );
        T result = (T) factoryManager.newElement(this.nodeId, definitionId);
        
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

        Object definition = node.getContent().getDefinition();
        DefinitionAdapter<Object> adapter = context.getDefinitionManager().getDefinitionAdapter( definition.getClass() );
        
        Width w = null;
        Height h = null;

        Set<?> properties = adapter.getProperties( definition );
        if ( null != properties ) {
            
            // Look for w/h or radius and set the values. 
            for ( Object property : properties ) {
                
                if ( property instanceof Radius ) {
                    Radius radius = (Radius) property;
                    double r = getRadius( width, height );
                    radius.setValue( r );
                    break;
                }
                
                if ( property instanceof Width ) {
                    w = (Width) property;
                    w.setValue( width );
                    if ( h != null ) {
                        break;
                    }
                }

                if ( property instanceof Height ) {
                    h = (Height) property;
                    h.setValue( height );
                    if ( w != null ) {
                        break;
                    }
                }
            }
            
        }
        
    }
    
    private double getRadius( double width, double height ) {
        return width / 2;
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

                final List<Command<GraphCommandExecutionContext, RuleViolation>> commands = new LinkedList<>();
                
                // If outgoing element it's a node means that it's docked.
                if ( outgoingBuilder instanceof AbstractNodeBuilder ) {

                    // Command - Create the docked node.
                    Node docked = (Node) outgoingBuilder.build(context);
                    commands.add( context.getCommandFactory().ADD_DOCKED_NODE(context.getGraph(), node, docked) );
                    
                    // Obtain docked position and use those for the docked node.
                    final List<Double[]> dockers = ((AbstractNodeBuilder) outgoingBuilder).dockers;
                    if ( !dockers.isEmpty() ) {
                        // TODO: Use not only first docker coordinates?
                        Double[] dCoords = dockers.get( 0 );
                        double x = dCoords[0];
                        double y = dCoords[1];
                        commands.add( context.getCommandFactory().UPDATE_POSITION( docked, x, y ) );
                        
                    }
                    
                } else {

                    // Create the outgoing edge.
                    Edge edge = (Edge) outgoingBuilder.build(context);

                    // Command - Execute the graph command to set the node as the edge connection's source..  
                    int magnetIdx = getSourceConnectionMagnetIndex(context, node, edge);
                    commands.add( context.getCommandFactory().SET_SOURCE_NODE(node, edge, magnetIdx) );;
                   
                }

                if ( !commands.isEmpty() ) {

                    for ( Command<GraphCommandExecutionContext, RuleViolation> command : commands ) {

                        doExecuteCommand( context, command );
                        
                    }
                    
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

                Command<GraphCommandExecutionContext, RuleViolation> command = null;
                
                if ( childNodeBuilder instanceof NodeObjectBuilder ) {
                    
                    // Command - Create the child node and the parent-child relationship.
                    Node childNode = (Node) childNodeBuilder.build(context);
                    command = context.getCommandFactory().ADD_CHILD_NODE(context.getGraph(), node, childNode);
                    
                }

                if ( null != command ) {

                    doExecuteCommand( context, command );

                }

            }
            
        }
        
    }
    
    private boolean doExecuteCommand( BuilderContext context,
                                      Command<GraphCommandExecutionContext, RuleViolation> command ) {
        
        CommandResult<RuleViolation> results = context.execute( command );
        if ( hasErrors(results) ) {
            throw new RuntimeException("Error building BPMN graph. " +
                    "Command = [" + command.toString() + "] " +
                    " Resutls = [" + results.toString() + "]");
        }
        
        return true;
    }
    
    public int getSourceConnectionMagnetIndex(BuilderContext context, T node, Edge<ViewConnector<W>, Node> edge) {
        return 3;
    }

    public int getTargetConnectionMagnetIndex(BuilderContext context, T node, Edge<ViewConnector<W>, Node> edge) {
        return 7;
    }

    @Override
    public String toString() {
        return super.toString() + " [defClass=" + definitionClass.getName() + "] [childrenIds=" + childNodeIds + "] ";
    }
    
}
