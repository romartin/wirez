package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.*;
import org.wirez.core.api.factory.DefinitionSetFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNDefinitionSetFactory implements DefinitionSetFactory<BPMNDefinitionSet> {

    @Inject
    BPMNDefinitionFactory bpmnDefinitionFactory;
    
    @Override
    public boolean accepts(final String id) {
        return id.equals(BPMNDefinitionSet.ID);
    }

    @Override
    public BPMNDefinitionSet build(final String id) {
        return new BPMNDefinitionSet(bpmnDefinitionFactory.buildBPMNDiagram(),
                bpmnDefinitionFactory.buildStartNoneEvent(),
                bpmnDefinitionFactory.buildEndNoneEvent(),
                bpmnDefinitionFactory.buildTask(),
                bpmnDefinitionFactory.buildSequenceFlow(),
                bpmnDefinitionFactory.buildParallelGateway());
    }
    
    
    
}
