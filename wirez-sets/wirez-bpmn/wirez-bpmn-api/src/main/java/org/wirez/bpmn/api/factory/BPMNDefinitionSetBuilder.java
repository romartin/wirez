package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.*;
import org.wirez.core.api.factory.DefinitionSetBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNDefinitionSetBuilder implements DefinitionSetBuilder<BPMNDefinitionSet> {

    BPMNDefinitionBuilder bpmnDefinitionBuilder;

    protected BPMNDefinitionSetBuilder() {
    }

    @Inject
    public BPMNDefinitionSetBuilder(BPMNDefinitionBuilder bpmnDefinitionBuilder) {
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
    }

    @Override
    public boolean accepts(final String id) {
        return id.equals(BPMNDefinitionSet.ID);
    }

    @Override
    public BPMNDefinitionSet build(final String id) {
        return new BPMNDefinitionSet(bpmnDefinitionBuilder.buildBPMNGraph(),
                bpmnDefinitionBuilder.buildBPMNDiagram(),
                bpmnDefinitionBuilder.buildStartNoneEvent(),
                bpmnDefinitionBuilder.buildEndNoneEvent(),
                bpmnDefinitionBuilder.buildTask(),
                bpmnDefinitionBuilder.buildSequenceFlow(),
                bpmnDefinitionBuilder.buildParallelGateway(),
                bpmnDefinitionBuilder.buildEndTerminateEvent(),
                bpmnDefinitionBuilder.buildLane());
    }
    
}
