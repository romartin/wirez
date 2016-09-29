package org.kie.workbench.common.stunner.core.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessingMorphingAnnotations {

    private final Map<String, String> baseDefaultTypes = new HashMap<>();
    private final Map<String, Set<String>> baseTargets = new HashMap<>();
    private final Map<String, List<ProcessingMorphProperty>> baseMorphProperties = new HashMap<>();

    public Map<String, String> getBaseDefaultTypes() {
        return baseDefaultTypes;
    }

    public Map<String, Set<String>> getBaseTargets() {
        return baseTargets;
    }

    public Map<String, List<ProcessingMorphProperty>> getBaseMorphProperties() {
        return baseMorphProperties;
    }
}
