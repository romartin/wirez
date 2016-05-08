package org.wirez.core.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessingPropertySetAnnotations {

    private final Map<String, String> nameFieldNames = new HashMap<>();
    private final Map<String, Set<String>> propertiesFieldNames = new HashMap<>();

    public Map<String, String> getNameFieldNames() {
        return nameFieldNames;
    }

    public Map<String, Set<String>> getPropertiesFieldNames() {
        return propertiesFieldNames;
    }

}
