package org.kie.workbench.common.stunner.bpmn;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.stunner.bpmn.definition.*;
import org.kie.workbench.common.stunner.bpmn.factory.BPMNGraphFactory;
import org.kie.workbench.common.stunner.core.definition.annotation.Description;
import org.kie.workbench.common.stunner.core.definition.annotation.ShapeSet;
import org.kie.workbench.common.stunner.core.definition.annotation.definitionset.DefinitionSet;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;
import org.kie.workbench.common.stunner.core.rule.annotation.CanContain;
import org.kie.workbench.common.stunner.core.rule.annotation.Occurrences;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Bindable
@DefinitionSet(

        graphFactory = BPMNGraphFactory.class,
        definitions = {

                BPMNDiagram.class,
                Lane.class,
                NoneTask.class,
                UserTask.class,
                ScriptTask.class,
                BusinessRuleTask.class,
                StartNoneEvent.class,
                EndNoneEvent.class,
                EndTerminateEvent.class,
                IntermediateTimerEvent.class,
                ParallelGateway.class,
                ExclusiveDatabasedGateway.class,
                ReusableSubprocess.class,
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
