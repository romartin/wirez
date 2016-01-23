package org.wirez.core.processors;

import org.wirez.core.api.definition.property.Property;

import java.util.*;

public class ProcessingContext {

    private static ProcessingContext context;
    private ProcessingEntity definitionSet;
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

    public ProcessingEntity getDefinitionSet() {
        return definitionSet;
    }

    public void setDefinitionSet(String packageName, String className) {
        if ( null != this.definitionSet ) {
            throw new RuntimeException("Only a single definition set allowed for a single processing.");
        }
        this.definitionSet = new ProcessingEntity(packageName + "." + className, className);
    }

    public void addRule(String className, String id) {
        rules.add(new ProcessingEntity(className, id));
    }
    
    public List<ProcessingEntity> getRules() {
        return rules;
    }
}
