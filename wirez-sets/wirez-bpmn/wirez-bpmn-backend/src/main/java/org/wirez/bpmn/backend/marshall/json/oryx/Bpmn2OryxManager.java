package org.wirez.bpmn.backend.marshall.json.oryx;

import org.wirez.bpmn.definition.factory.BPMNDefinitionFactory;
import org.wirez.bpmn.definition.factory.BPMNPropertyFactory;
import org.wirez.bpmn.backend.marshall.json.oryx.property.Bpmn2OryxPropertyManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class Bpmn2OryxManager {

    BPMNDefinitionFactory bpmnDefinitionFactory;
    BPMNPropertyFactory bpmnPropertyFactory;
    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;

    private final List<Class<?>> definitions = new LinkedList<>();
    private final List<Class<?>> properties = new LinkedList<>();
    
    protected Bpmn2OryxManager() {

    }

    @Inject
    public Bpmn2OryxManager(final BPMNDefinitionFactory bpmnDefinitionFactory,
                            final BPMNPropertyFactory bpmnPropertyFactory,
                            final Bpmn2OryxIdMappings oryxIdMappings,
                            final Bpmn2OryxPropertyManager oryxPropertyManager) {
        this.bpmnDefinitionFactory = bpmnDefinitionFactory;
        this.bpmnPropertyFactory = bpmnPropertyFactory;
        this.oryxIdMappings = oryxIdMappings;
        this.oryxPropertyManager = oryxPropertyManager;
    }

    @PostConstruct
    public void init() {

        // Load default & custom mappings for BPMN definitions.
        final Set<Class<?>> defClasses = bpmnDefinitionFactory.getAcceptedClasses();
        definitions.addAll( defClasses );

        // Load default & custom mappings for BPMN properties.
        final Set<Class<?>> propClasses = bpmnPropertyFactory.getAcceptedClasses();
        properties.addAll( propClasses );
        
        // Initialize the manager for the id mappings.
        oryxIdMappings.init( definitions, properties );

    }

    public Bpmn2OryxIdMappings getMappingsManager() {
        return oryxIdMappings;
    }

    public Bpmn2OryxPropertyManager getPropertyManager() {
        return oryxPropertyManager;
    }

    public List<Class<?>> getDefinitions() {
        return definitions;
    }

    public List<Class<?>> getProperties() {
        return properties;
    }
    
    
    
}
