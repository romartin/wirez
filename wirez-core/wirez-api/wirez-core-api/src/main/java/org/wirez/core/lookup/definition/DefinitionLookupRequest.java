package org.wirez.core.lookup.definition;

import org.wirez.core.lookup.LookupManager;

public interface DefinitionLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
