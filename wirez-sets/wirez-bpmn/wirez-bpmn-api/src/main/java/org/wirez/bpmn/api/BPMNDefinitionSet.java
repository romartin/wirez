package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.annotation.definitionset.Definition;
import org.wirez.core.api.annotation.rule.EdgeOccurrences;
import org.wirez.core.api.annotation.rule.EdgeType;
import org.wirez.core.api.annotation.rule.Occurrences;
import org.wirez.core.api.definition.DefinitionSet;

import javax.inject.Inject;

@Portable
@Bindable
@org.wirez.core.api.annotation.definitionset.DefinitionSet
@Occurrences(
        role = "Startevents_all", 
        min = 0,
        value = {
            @EdgeOccurrences(type = EdgeType.INCOMING, edge = "SequenceFlow", max = 0, min = 0)
        }
)
@Occurrences(
        role = "Endevents_all", 
        min = 0,
        value = {
            @EdgeOccurrences(type = EdgeType.OUTGOING, edge = "SequenceFlow", max = 0, min = 0)
        }
)
public class BPMNDefinitionSet implements DefinitionSet {

    public static final String ID = "bpmnDefSet";

    @Definition
    private BPMNDiagram diagram;

    @Definition
    private StartNoneEvent startNoneEvent;

    @Definition
    private EndNoneEvent endNoneEvent;

    @Definition
    private Task task;

    @Definition
    private SequenceFlow sequenceFlow;

    @Definition
    private ParallelGateway parallelGateway;


    public BPMNDefinitionSet() {
    }

    public BPMNDefinitionSet(@MapsTo("diagram") BPMNDiagram diagram,
                             @MapsTo("startNoneEvent") StartNoneEvent startNoneEvent,
                             @MapsTo("endNoneEvent") EndNoneEvent endNoneEvent,
                             @MapsTo("task") Task task,
                             @MapsTo("sequenceFlow") SequenceFlow sequenceFlow,
                             @MapsTo("parallelGateway") ParallelGateway parallelGateway) {
        this.diagram = diagram;
        this.startNoneEvent = startNoneEvent;
        this.endNoneEvent = endNoneEvent;
        this.task = task;
        this.sequenceFlow = sequenceFlow;
        this.parallelGateway = parallelGateway;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDomain() {
        return "bpmn.org";
    }

    @Override
    public String getDescription() {
        return "THe BPMN definition set";
    }

    public BPMNDiagram getDiagram() {
        return diagram;
    }

    public StartNoneEvent getStartNoneEvent() {
        return startNoneEvent;
    }

    public Task getTask() {
        return task;
    }

    public void setDiagram(BPMNDiagram diagram) {
        this.diagram = diagram;
    }

    public void setStartNoneEvent(StartNoneEvent startNoneEvent) {
        this.startNoneEvent = startNoneEvent;
    }

    public EndNoneEvent getEndNoneEvent() {
        return endNoneEvent;
    }

    public void setEndNoneEvent(EndNoneEvent endNoneEvent) {
        this.endNoneEvent = endNoneEvent;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SequenceFlow getSequenceFlow() {
        return sequenceFlow;
    }

    public void setSequenceFlow(SequenceFlow sequenceFlow) {
        this.sequenceFlow = sequenceFlow;
    }

    public ParallelGateway getParallelGateway() {
        return parallelGateway;
    }

    public void setParallelGateway(ParallelGateway parallelGateway) {
        this.parallelGateway = parallelGateway;
    }
}
