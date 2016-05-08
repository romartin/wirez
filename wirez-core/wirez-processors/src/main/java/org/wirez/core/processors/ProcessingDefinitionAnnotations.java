package org.wirez.core.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessingDefinitionAnnotations {

    private final Map<String, Set<String>> propertySetFieldNames = new HashMap<>();
    private final Map<String, Set<String>> propertyFieldNames = new HashMap<>();
    private final Map<String, String> graphElementFieldNames = new HashMap<>();
    private final Map<String, String> elementFactoryFieldNames = new HashMap<>();
    private final Map<String, String> labelsFieldNames = new HashMap<>();
    private final Map<String, String> titleFieldNames = new HashMap<>();
    private final Map<String, String> categoryFieldNames = new HashMap<>();
    private final Map<String, String> descriptionFieldNames = new HashMap<>();
    private final Map<String, String[]> shapeProxies = new HashMap<>();

    public Map<String, Set<String>> getPropertySetFieldNames() {
        return propertySetFieldNames;
    }

    public Map<String, Set<String>> getPropertyFieldNames() {
        return propertyFieldNames;
    }

    public Map<String, String> getGraphElementFieldNames() {
        return graphElementFieldNames;
    }

    public Map<String, String> getElementFactoryFieldNames() {
        return elementFactoryFieldNames;
    }

    public Map<String, String> getLabelsFieldNames() {
        return labelsFieldNames;
    }

    public Map<String, String> getTitleFieldNames() {
        return titleFieldNames;
    }

    public Map<String, String> getCategoryFieldNames() {
        return categoryFieldNames;
    }

    public Map<String, String> getDescriptionFieldNames() {
        return descriptionFieldNames;
    }

    public Map<String, String[]> getShapeProxies() {
        return shapeProxies;
    }
    
}
