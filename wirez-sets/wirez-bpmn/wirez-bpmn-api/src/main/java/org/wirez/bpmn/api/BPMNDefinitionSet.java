package org.wirez.bpmn.api;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.rule.annotation.CanContain;
import org.wirez.core.api.rule.annotation.Occurrences;

@ApplicationScoped
@Bindable
@DefinitionSet(
        type = Graph.class,
        factory = BPMNAbstractGraphFactory.FACTORY_NAME,
        definitions = {
            BPMNDiagram.class,
            StartNoneEvent.class,
            EndNoneEvent.class,
            Task.class,
            SequenceFlow.class,
            ParallelGateway.class,
            EndTerminateEvent.class,
            Lane.class
        }
)
@CanContain( roles = { "diagram" } )
@Occurrences(
        role = "Startevents_all",
        min = 0
)
@Occurrences(
        role = "Endevents_all",
        min = 0
)
public class BPMNDefinitionSet {

    @Description
    public static final transient String description = "BPMN v2 Set";

    public BPMNDefinitionSet() {

    }

    public String getDescription() {
        return description;
    }

}
