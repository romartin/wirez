package org.wirez.bpmn.backend.marshall.json.builder;

import org.uberfire.ext.wirez.bpmn.api.*;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.edges.SequenceFlowBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.BPMNDiagramBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.activities.TaskBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndNoneEventBuilder;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder(this);
    }
    
    public GraphObjectBuilder<?, ?> builderFor(String id) {

        /* Nodes */
        if (BPMNDiagram.ID.equals(id)) {
            return new BPMNDiagramBuilder(this);
        } else if (StartNoneEvent.ID.equals(id)) {
            return new StartNoneEventBuilder(this);
        } else if (EndNoneEvent.ID.equals(id)) {
            return new EndNoneEventBuilder(this);
        } else if (Task.ID.equals(id)) {
            return new TaskBuilder(this);
        }

        /* Edges */
        if (SequenceFlow.ID.equals(id)) {
            return new SequenceFlowBuilder(this);
        }
        
        throw new RuntimeException("No builder for stencil '" + id + "'.");
    }
    
}
