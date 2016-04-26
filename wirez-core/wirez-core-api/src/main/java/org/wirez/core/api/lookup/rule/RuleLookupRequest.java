package org.wirez.core.api.lookup.rule;

import org.wirez.core.api.lookup.LookupManager;

public interface RuleLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
