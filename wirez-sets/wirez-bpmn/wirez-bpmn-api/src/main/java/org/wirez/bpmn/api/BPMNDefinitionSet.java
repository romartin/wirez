package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.DefinitionSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Portable
public class BPMNDefinitionSet implements DefinitionSet {

    @Inject
    BPMNDiagram diagram;

    @Inject
    StartNoneEvent startNoneEvent;

    @Inject
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

    @org.wirez.core.api.annotation.definitionset.Definition
    public BPMNDiagram getDiagram() {
        return diagram;
    }

    @org.wirez.core.api.annotation.definitionset.Definition
    public StartNoneEvent getStartNoneEvent() {
        return startNoneEvent;
    }

    @org.wirez.core.api.annotation.definitionset.Definition
    public Task getTask() {
        return task;
    }

}
