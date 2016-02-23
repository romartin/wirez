package org.wirez.bpmn.backend.marshall.json.builder.nodes;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.BPMNGraph;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.builder.GraphObjectBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.service.definition.DefinitionService;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class BPMNDiagramBuilder extends AbstractNodeBuilder<BPMNDiagram, Node<View<BPMNDiagram>, Edge>> {

    public BPMNDiagramBuilder() {
        super();
    }

    @Override
    public String getDefinitionId() {
        return BPMNDiagram.ID;
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<BPMNDiagram>, Edge> node, double width, double height) {
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

    @Override
    public String toString() {
        return "[NodeBuilder=BPMNDiagramBuilder] " + super.toString();
    }

}
