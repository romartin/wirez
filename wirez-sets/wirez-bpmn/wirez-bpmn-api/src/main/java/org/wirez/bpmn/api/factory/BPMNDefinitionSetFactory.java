package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.*;
import org.wirez.core.api.factory.DefinitionSetFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNDefinitionSetFactory implements DefinitionSetFactory<BPMNDefinitionSet> {

    BPMNDefinitionFactory bpmnDefinitionFactory;

    protected BPMNDefinitionSetFactory() {
    }

    @Inject
    public BPMNDefinitionSetFactory(BPMNDefinitionFactory bpmnDefinitionFactory) {
        this.bpmnDefinitionFactory = bpmnDefinitionFactory;
    }

    @Override
    public boolean accepts(final String id) {
        return id.equals(BPMNDefinitionSet.ID);
    }

    @Override
    public BPMNDefinitionSet build(final String id) {
        return new BPMNDefinitionSet(bpmnDefinitionFactory.buildBPMNGraph(), 
                bpmnDefinitionFactory.buildBPMNDiagram(),
                bpmnDefinitionFactory.buildStartNoneEvent(),
                bpmnDefinitionFactory.buildEndNoneEvent(),
                bpmnDefinitionFactory.buildTask(),
                bpmnDefinitionFactory.buildSequenceFlow(),
                bpmnDefinitionFactory.buildParallelGateway(),
                bpmnDefinitionFactory.buildEndTerminateEvent(),
                bpmnDefinitionFactory.buildLane());
    }
    
}
