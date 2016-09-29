package org.kie.workbench.common.stunner.core.lookup.rule;

import org.kie.workbench.common.stunner.core.lookup.LookupManager;

public interface RuleLookupRequest extends LookupManager.LookupRequest {
    
    String getDefinitionSetId();
    
}
