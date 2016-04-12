package org.wirez.bpmn.backend.marshall.json.builder.nodes;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.backend.marshall.json.builder.AbstractNodeBuilder;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Set;

@Dependent
public class BPMNDiagramBuilder extends AbstractNodeBuilder<BPMNDiagram, Node<View<BPMNDiagram>, Edge>> {

    @Inject
    public BPMNDiagramBuilder(Bpmn2OryxIdMappings oryxIdMappings) {
        super(oryxIdMappings);
    }

    @Override
    public String getDefinitionId() {
        return oryxIdMappings.getOryxId(BPMNDiagram.class);
    }

    @Override
    protected void setSize(BuilderContext context, Node<View<BPMNDiagram>, Edge> node, double width, double height) {
        super.setSize(context, node, width, height);
        BPMNDiagram def = node.getContent().getDefinition();
        def.getWidth().setValue(width);
        def.getHeight().setValue(height);
    }

    @Override
    protected Object getProperty(final BuilderContext context,
                                 final Set<?> defProperties, 
                                 final String id) {
        if ( "processn".equals(id) ) {
            return super.getProperty(context, defProperties, Name.ID);
        } else if ( oryxIdMappings.getOryxPropertyId(Name.ID).equals(id)) {
            return null;
        }
        return super.getProperty(context, defProperties, id);
    }

    @Override
    public String toString() {
        return "[NodeBuilder=BPMNDiagramBuilder] " + super.toString();
    }

}
