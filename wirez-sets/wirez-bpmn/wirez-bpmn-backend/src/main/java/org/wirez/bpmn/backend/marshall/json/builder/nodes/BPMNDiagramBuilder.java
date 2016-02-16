package org.wirez.bpmn.backend.marshall.json.builder.nodes;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.BPMNGraph;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.service.definition.DefinitionService;

import java.util.Collection;
import java.util.Set;

// TODO: Bounds.
public class BPMNDiagramBuilder extends AbstractNodeBuilder<BPMNDiagram, Node<ViewContent<BPMNDiagram>, Edge>> {

    public BPMNDiagramBuilder(BPMNGraphObjectBuilderFactory graphObjectBuilderFactory) {
        super(graphObjectBuilderFactory);
    }

    @Override
    protected Node<ViewContent<BPMNDiagram>, Edge> buildNode(BuilderContext context, DefinitionService definitionService) {
        return (Node<ViewContent<BPMNDiagram>, Edge>) definitionService.buildGraphElement(this.nodeId, BPMNDiagram.ID);
    }

    @Override
    protected void afterNodeBuild(BuilderContext context, Node<ViewContent<BPMNDiagram>, Edge> node) {
        super.afterNodeBuild(context, node);

        StartNoneEventBuilder startProcessNodeBuilder = getStartProcessNode(context);
        if (startProcessNodeBuilder == null) {
            throw new RuntimeException("No start process event found!");
        }

        Node startProcessNode = startProcessNodeBuilder.build(context);
        final DefaultGraph<BPMNGraph, Node, Edge> graph = context.getGraph();
        graph.addNode(startProcessNode);
    }

    @Override
    protected void setSize(BuilderContext context, Node<ViewContent<BPMNDiagram>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        BPMNDiagram def = node.getContent().getDefinition();
        def.getWidth().setValue(width);
        def.getHeight().setValue(height);
    }

    @Override
    protected Property getProperty(Set<Property> defProperties, String id) {
        if ( "processn".equals(id) ) {
            return super.getProperty(defProperties, Name.ID);
        } else if ( Name.ID.equals(id)) {
            return null;
        }
        return super.getProperty(defProperties, id);
    }

    // TODO: Support for multiple start nodes.
    private StartNoneEventBuilder getStartProcessNode(BuilderContext context) {
        for (String childNodeId : childNodeIds) {
            GraphObjectBuilder<?, ?>  builder = getBuilder(context, childNodeId);
            if ( null != builder && builder instanceof StartNoneEventBuilder ) {
                return (StartNoneEventBuilder) builder;
            }
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        return "[NodeBuilder=BPMNDiagramBuilder] " + super.toString();
    }

}
