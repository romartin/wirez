package org.wirez.core.processors;

import org.wirez.core.api.definition.property.Property;

import java.util.HashMap;
import java.util.Map;

public class ProcessingContext {

    private static ProcessingContext context;
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
}
