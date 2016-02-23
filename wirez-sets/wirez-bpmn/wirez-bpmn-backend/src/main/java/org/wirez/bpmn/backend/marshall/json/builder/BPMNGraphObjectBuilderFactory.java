package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.api.*;
import org.wirez.bpmn.backend.marshall.json.builder.edges.SequenceFlowBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.BPMNDiagramBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.activities.ParallelGatewayBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.activities.TaskBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndNoneEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.EndTerminateEventBuilder;
import org.wirez.bpmn.backend.marshall.json.builder.nodes.events.StartNoneEventBuilder;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.service.definition.DefinitionService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    @Inject
    Instance<GraphObjectBuilder<? ,?>> graphObjectBuilders;
    
    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder(this);
    }
    
    public GraphObjectBuilder<?, ?> builderFor(String id) {
        if ( id == null) throw new NullPointerException();
        
        for ( GraphObjectBuilder<? ,?> builder : graphObjectBuilders) {
            if ( id.equals(builder.getDefinitionId()) ) {
                return builder;
            }
        }

        throw new RuntimeException("No builder for definition [" + id + "]");
    }

}
