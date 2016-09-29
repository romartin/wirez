package org.kie.workbench.common.stunner.core.processors;

import java.util.HashMap;
import java.util.Map;

public class ProcessingPropertyAnnotations {

    private final Map<String, String> valueFieldNames = new HashMap<>();
    private final Map<String, String> defaultValueFieldNames = new HashMap<>();
    private final Map<String, String> typeFieldNames = new HashMap<>();
    private final Map<String, String> captionFieldNames = new HashMap<>();
    private final Map<String, String> descriptionFieldNames = new HashMap<>();
    private final Map<String, String> readOnlyFieldNames = new HashMap<>();
    private final Map<String, String> optionalFieldNames = new HashMap<>();
    private final Map<String, String> allowedValuesFieldNames = new HashMap<>();

    public Map<String, String> getValueFieldNames() {
        return valueFieldNames;
    }

    public Map<String, String> getDefaultValueFieldNames() {
        return defaultValueFieldNames;
    }

    public Map<String, String> getTypeFieldNames() {
        return typeFieldNames;
    }

    public Map<String, String> getCaptionFieldNames() {
        return captionFieldNames;
    }

    public Map<String, String> getDescriptionFieldNames() {
        return descriptionFieldNames;
    }

    public Map<String, String> getReadOnlyFieldNames() {
        return readOnlyFieldNames;
    }

    public Map<String, String> getOptionalFieldNames() {
        return optionalFieldNames;
    }

    public Map<String, String> getAllowedValuesFieldNames() {
        return allowedValuesFieldNames;
    }
}
