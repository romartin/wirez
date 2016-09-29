package org.kie.workbench.common.stunner.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.workbench.common.stunner.core.lookup.rule.RuleLookupManager;

@Remote
public interface RuleLookupService extends RuleLookupManager {
    
}
