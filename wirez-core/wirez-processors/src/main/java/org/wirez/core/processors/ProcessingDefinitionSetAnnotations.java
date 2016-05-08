package org.wirez.core.processors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProcessingDefinitionSetAnnotations {

    private final Map<String, String> descriptionFieldNames = new HashMap<>();
    private final Set<String> definitionIds = new HashSet<>();
    private final Map<String, String> graphTypes = new HashMap<>();
    private final Map<String, String> graphFactories = new HashMap<>();
    private final Map<String, String> shapeSetThunb = new HashMap<>();

    public Map<String, String> getDescriptionFieldNames() {
        return descriptionFieldNames;
    }

    public Set<String> getDefinitionIds() {
        return definitionIds;
    }

    public Map<String, String> getGraphTypes() {
        return graphTypes;
    }

    public Map<String, String> getGraphFactories() {
        return graphFactories;
    }

    public Map<String, String> getShapeSetThunb() {
        return shapeSetThunb;
    }
}
