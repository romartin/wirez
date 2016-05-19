package org.wirez.bpmn.definition.factory;

import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.core.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNDefinitionSetFactory extends BindableModelFactory<BPMNDefinitionSet> {

    BPMNDefinitionFactory bpmnDefinitionBuilder;

    protected BPMNDefinitionSetFactory() {
    }

    @Inject
    public BPMNDefinitionSetFactory(BPMNDefinitionFactory bpmnDefinitionBuilder) {
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
    }

    private static final Set<Class<?>>  SUPPORTED_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(BPMNDefinitionSet.class);
    }};
    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_CLASSES;
    }

    @Override
    public BPMNDefinitionSet build(final Class<?> clazz) {
        return new BPMNDefinitionSet();
    }


}
