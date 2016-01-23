package org.wirez.core.processors;

import org.wirez.core.api.definition.property.Property;

import java.util.*;

public class ProcessingContext {

    private static ProcessingContext context;
    private String definitionSetClassName;
    private static final List<ProcessingEntity> rules = new ArrayList<>();
    private static final Map<String, String> propertyValueFieldNames = new HashMap<>();
    private static final Map<String, String> propertyDefaultValueFieldNames = new HashMap<>();

    public synchronized static ProcessingContext getInstance() {
        if ( null == context ) {
            context = new ProcessingContext();
        }
        
        return context;
    }

    public ProcessingContext() {
    }
    
    public void addValueFieldName(String propClassName, String valueFieldName) {
        propertyValueFieldNames.put(propClassName, valueFieldName);
    }

    public Map<String, String> getValueFieldNames() {
        return propertyValueFieldNames;
    }

    public void addDefaultValueFieldName(String propClassName, String valueFieldName) {
        propertyDefaultValueFieldNames.put(propClassName, valueFieldName);
    }

    public Map<String, String> getDefaultValueFieldNames() {
        return propertyDefaultValueFieldNames;
    }

    public String getDefinitionSetClassName() {
        return definitionSetClassName;
    }

    public void setDefinitionSetClassName(String definitionSetClassName) {
        if ( null != this.definitionSetClassName ) {
            throw new RuntimeException("Only a single definition set allowed for a single processing.");
        }
        this.definitionSetClassName = definitionSetClassName;
    }

    public void addRule(String className, String id) {
        rules.add(new ProcessingEntity(className, id));
    }
    
    public List<ProcessingEntity> getRules() {
        return rules;
    }
}
