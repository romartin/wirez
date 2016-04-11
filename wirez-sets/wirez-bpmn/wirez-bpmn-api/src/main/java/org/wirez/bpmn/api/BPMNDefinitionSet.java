package org.wirez.bpmn.api;

import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.api.definition.annotation.rule.CanContain;
import org.wirez.core.api.graph.Graph;

import javax.enterprise.context.ApplicationScoped;

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
public class BPMNDefinitionSet {

    @Description
    public static final transient String description = "BPMN2 Definition Set";

    public BPMNDefinitionSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
