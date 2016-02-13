package org.wirez.bpmn.backend.marshall.json.builder.nodes;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.service.definition.DefinitionService;

import java.util.Collection;

public class BPMNDiagramBuilder extends AbstractObjectBuilder<BPMNDiagram, DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge>> {

    public BPMNDiagramBuilder(BPMNGraphObjectBuilderFactory bpmnBuilderFactory) {
        super(bpmnBuilderFactory);
    }

    @Override
    protected DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge> doBuild(GraphObjectBuilder.BuilderContext context) {

        DefinitionService definitionService = bpmnGraphFactory.getDefinitionService();
        DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge> result = 
                (DefaultGraph<ViewContent<BPMNDiagram>, Node, Edge>) definitionService.buildGraphElement(this.nodeId, BPMNDiagram.ID);

        setProperties(result.getContent().getDefinition());

        context.init(result);
        
        StartNoneEventBuilder startProcessNodeBuilder = getStartProcessNode(context);
        if (startProcessNodeBuilder == null) {
            throw new RuntimeException("No start process event found!");
        }

        Node startProcessNode = startProcessNodeBuilder.build(context);
        result.addNode(startProcessNode);

        return result;
    }

    protected String getPropertyIdMapping(final String propId) {
        if ( "processn".equals(propId) ) {
            return Name.ID;
        } else {
            return super.getPropertyIdMapping(propId);
        }
    }

    // TODO: Can be multiple
    protected StartNoneEventBuilder getStartProcessNode(BuilderContext context) {
        Collection<GraphObjectBuilder<?, ?>> builders = context.getBuilders();
        if (builders != null && !builders.isEmpty()) {
            for (GraphObjectBuilder<?, ?> builder : builders) {
                try {
                    return (StartNoneEventBuilder) builder;
                } catch (ClassCastException e) {
                    // Not a start event. Continue with the search...
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[NodeBuilder=BPMNDiagramBuilder]" + super.toString();
    }
}
