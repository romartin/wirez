package org.wirez.bpmn.backend.marshall.json.oryx;

import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.backend.marshall.json.oryx.property.Bpmn2OryxPropertyManager;
import org.wirez.core.backend.definition.utils.BackendBindableDefinitionUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class Bpmn2OryxManager {

    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;

    private final List<Class<?>> definitions = new LinkedList<>();
    
    protected Bpmn2OryxManager() {

    }

    @Inject
    public Bpmn2OryxManager(final Bpmn2OryxIdMappings oryxIdMappings,
                            final Bpmn2OryxPropertyManager oryxPropertyManager) {
        this.oryxIdMappings = oryxIdMappings;
        this.oryxPropertyManager = oryxPropertyManager;
    }

    @PostConstruct
    public void init() {

        final BPMNDefinitionSet set = new BPMNDefinitionSet.BPMNDefinitionSetBuilder().build();
        
        // Load default & custom mappings for BPMN definitions.
        final Set<Class<?>> defClasses = BackendBindableDefinitionUtils.getDefinitions( set );
        definitions.addAll( defClasses );

        // Initialize the manager for the id mappings.
        oryxIdMappings.init( definitions  );

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

}
