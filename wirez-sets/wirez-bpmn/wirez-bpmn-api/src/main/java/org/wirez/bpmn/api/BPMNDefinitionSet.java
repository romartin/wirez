package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.annotation.definitionset.Definition;
import org.wirez.core.api.definition.DefinitionSet;

import javax.inject.Inject;

@Portable
@Bindable
@org.wirez.core.api.annotation.definitionset.DefinitionSet
public class BPMNDefinitionSet implements DefinitionSet {

    @Inject
    @Definition
    BPMNDiagram diagram;

    @Inject
    @Definition
    StartNoneEvent startNoneEvent;

    @Inject
    @Definition
    Task task;
    
    @Override
    public String getId() {
        return "bpmnDefSet";
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

    public void setTask(Task task) {
        this.task = task;
    }
}
