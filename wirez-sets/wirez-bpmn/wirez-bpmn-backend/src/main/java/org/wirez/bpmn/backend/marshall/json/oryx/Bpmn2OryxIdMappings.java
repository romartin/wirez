package org.wirez.bpmn.backend.marshall.json.oryx;

import org.apache.commons.lang3.StringUtils;
import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.bpmn.api.factory.BPMNPropertyFactory;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.bpmn.api.property.simulation.CatchEventAttributes;
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains the mappings for the different stencil identifiers that are different from 
 * the patterns used in this tool.
 */
@ApplicationScoped
public class Bpmn2OryxIdMappings {

    BPMNDefinitionFactory bpmnDefinitionFactory;
    BPMNPropertyFactory bpmnPropertyFactory;

    private static final Map<Class<?>, String> defMappings = new HashMap<Class<?>, String>();
    private static final Map<Class<?>, String> propMappings = new HashMap<Class<?>, String>();
    private static final Map<Class<?>, String> customMappings = new HashMap<Class<?>, String>() {{
        // Add here class <-> oryxId mappings, if any.
        // No custom mappings, for now.
    }};

    protected Bpmn2OryxIdMappings() {

    }

    @Inject
    public Bpmn2OryxIdMappings(final BPMNDefinitionFactory bpmnDefinitionFactory,
                               final BPMNPropertyFactory bpmnPropertyFactory) {
        this.bpmnDefinitionFactory = bpmnDefinitionFactory;
        this.bpmnPropertyFactory = bpmnPropertyFactory;
    }

    @PostConstruct
    public void init() {

        // Load default & custom mappings for BPMN definitions.
        final Set<Class<?>> defClasses = bpmnDefinitionFactory.getAcceptedClasses();
        for ( final Class<?> defClass : defClasses ) {
            String customMapping = customMappings.get( defClass );
            String orxId = customMapping != null ? customMapping : getDefaultOryxDefinitionId( defClass );
            defMappings.put( defClass, orxId );
        }

        // Load default & custom mappings for BPMN properties.
        final Set<Class<?>> propClasses = bpmnPropertyFactory.getAcceptedClasses();
        for ( final Class<?> propClass : propClasses ) {
            String customMapping = customMappings.get( propClass );
            String orxId = customMapping != null ? customMapping : getDefaultOryxPropertyId( propClass );
            propMappings.put( propClass, orxId );
        }

    }


    public String getOryxDefinitionId( Class<?> clazz ) {
        return defMappings.get( clazz );
    }

    public String getOryxPropertyId( Class<?> clazz ) {
        return propMappings.get(clazz);
    }

    public String getPropertyId( String oryxId ) {
        return getId(oryxId, propMappings);
    }

    public String getDefinitionId( String oryxId ) {
        return getId(oryxId, defMappings);
    }

    private String getId( String oryxId, Map<Class<?>, String> map ) {

        Set<Map.Entry<Class<?>, String>> entrySet = map.entrySet();
        for ( Map.Entry<Class<?>, String> entry : entrySet ) {
            String oId = entry.getValue();

            if ( oId.equals(oryxId)) {
                return entry.getValue();
            }
        }

        return null;

    }

    private String getDefaultOryxDefinitionId( Class<?> clazz ) {
        return clazz.getSimpleName();
    }

    private String getDefaultOryxPropertyId( Class<?> clazz ) {
        return StringUtils.uncapitalize(clazz.getSimpleName() );
    }

}
