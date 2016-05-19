package org.wirez.core.lookup.rule;

import org.wirez.core.lookup.LookupManager;

public interface RuleLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
