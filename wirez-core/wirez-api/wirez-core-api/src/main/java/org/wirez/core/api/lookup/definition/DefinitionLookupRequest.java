package org.wirez.core.api.lookup.definition;

import org.wirez.core.api.lookup.LookupManager;

public interface DefinitionLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
