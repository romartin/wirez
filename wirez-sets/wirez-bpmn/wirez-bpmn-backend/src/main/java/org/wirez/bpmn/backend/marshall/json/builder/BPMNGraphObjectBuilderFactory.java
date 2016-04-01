package org.wirez.bpmn.backend.marshall.json.builder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    Instance<GraphObjectBuilder<? ,?>> graphObjectBuilders;

    @Inject
    public BPMNGraphObjectBuilderFactory(Instance<GraphObjectBuilder<?, ?>> graphObjectBuilders) {
        this.graphObjectBuilders = graphObjectBuilders;
    }

    public BPMNGraphObjectBuilderFactory() {
    }

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
