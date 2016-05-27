package org.wirez.bpmn;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.*;
import org.wirez.bpmn.definition.factory.BPMNAbstractGraphFactory;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.ShapeSet;
import org.wirez.core.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.definition.factory.Builder;
import org.wirez.core.graph.Graph;
import org.wirez.core.rule.annotation.CanContain;
import org.wirez.core.rule.annotation.Occurrences;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Bindable
@DefinitionSet(
        
        type = Graph.class,
        factory = BPMNAbstractGraphFactory.FACTORY_NAME,
        definitions = {

                BPMNDiagram.class,
                Lane.class,
                Task.class,
                StartNoneEvent.class,
                EndNoneEvent.class,
                EndTerminateEvent.class,
                ParallelGateway.class,
                ExclusiveDatabasedGateway.class,
                SequenceFlow.class

        },
        builder = BPMNDefinitionSet.BPMNDefinitionSetBuilder.class 
)
@CanContain( roles = {"diagram"} )
@Occurrences(
        role = "Startevents_all",
        min = 0
)
@Occurrences(
        role = "Endevents_all",
        min = 0
)
@ShapeSet
public class BPMNDefinitionSet {

    @Description
    public static final transient String description = "BPMN2";

    @NonPortable
    public static class BPMNDefinitionSetBuilder implements Builder<BPMNDefinitionSet> {

        @Override
        public BPMNDefinitionSet build() {
            return new BPMNDefinitionSet();
        }

    }
    
    public BPMNDefinitionSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
