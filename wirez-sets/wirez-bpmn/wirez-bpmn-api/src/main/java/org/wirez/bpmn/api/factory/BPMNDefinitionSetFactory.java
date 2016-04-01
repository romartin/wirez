package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.core.api.definition.factory.ModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNDefinitionSetFactory implements ModelFactory<BPMNDefinitionSet> {

    BPMNDefinitionFactory bpmnDefinitionBuilder;

    protected BPMNDefinitionSetFactory() {
    }

    @Inject
    public BPMNDefinitionSetFactory(BPMNDefinitionFactory bpmnDefinitionBuilder) {
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
    }

    @Override
    public boolean accepts(final String id) {
        return id.equals(BPMNDefinitionSet.class.getSimpleName());
    }

    @Override
    public BPMNDefinitionSet build(final String id) {
        return new BPMNDefinitionSet();
    }
    
}
