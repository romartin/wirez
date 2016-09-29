package org.kie.workbench.common.stunner.core.lookup.definition;

import org.kie.workbench.common.stunner.core.lookup.LookupManager;

public interface DefinitionLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
