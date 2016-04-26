package org.wirez.core.api.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.lookup.definition.DefinitionLookupManager;
import org.wirez.core.api.lookup.rule.RuleLookupManager;

@Remote
public interface RuleLookupService extends RuleLookupManager {
    
}
