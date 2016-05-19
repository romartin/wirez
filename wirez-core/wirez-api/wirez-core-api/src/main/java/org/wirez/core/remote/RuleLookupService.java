package org.wirez.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.lookup.rule.RuleLookupManager;

@Remote
public interface RuleLookupService extends RuleLookupManager {
    
}
