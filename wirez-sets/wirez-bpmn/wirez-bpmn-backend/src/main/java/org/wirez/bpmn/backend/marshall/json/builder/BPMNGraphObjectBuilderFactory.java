package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    Instance<GraphObjectBuilder<? ,?>> graphObjectBuilders;
    Bpmn2OryxIdMappings oryxIdMappings;
    
    @Inject
    public BPMNGraphObjectBuilderFactory(Instance<GraphObjectBuilder<?, ?>> graphObjectBuilders,
                                         Bpmn2OryxIdMappings oryxIdMappings) {
        this.graphObjectBuilders = graphObjectBuilders;
        this.oryxIdMappings = oryxIdMappings;
    }

    public BPMNGraphObjectBuilderFactory() {
    }

    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder( this, oryxIdMappings );
    }
    
    public GraphObjectBuilder<?, ?> builderFor(String id) {
        if ( id == null) throw new NullPointerException();
        
        for ( GraphObjectBuilder<? ,?> builder : graphObjectBuilders) {
            if ( id.equals(builder.getOryxDefinitionId()) ) {
                return builder;
            }
        }

        throw new RuntimeException("No builder for definition [" + id + "]");
    }

}
