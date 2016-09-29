package org.kie.workbench.common.stunner.core.processors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProcessingDefinitionSetAnnotations {

    private final Map<String, String> descriptionFieldNames = new HashMap<>();
    private final Set<String> definitionIds = new HashSet<>();
    private final Map<String, String> builderFieldNames = new HashMap<>();
    private final Map<String, String> graphTypes = new HashMap<>();
    private boolean hasShapeSet = false;

    public Map<String, String> getDescriptionFieldNames() {
        return descriptionFieldNames;
    }

    public Set<String> getDefinitionIds() {
        return definitionIds;
    }

    public Map<String, String> getBuilderFieldNames() {
        return builderFieldNames;
    }

    public Map<String, String> getGraphFactoryTypes() {
        return graphTypes;
    }

    public boolean hasShapeSet() {
        return hasShapeSet;
    }

    public void setHasShapeSet(boolean hasShapeSet) {
        this.hasShapeSet = hasShapeSet;
    }
}
