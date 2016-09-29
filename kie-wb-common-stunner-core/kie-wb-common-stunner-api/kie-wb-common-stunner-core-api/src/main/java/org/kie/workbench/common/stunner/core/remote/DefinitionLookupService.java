package org.kie.workbench.common.stunner.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.workbench.common.stunner.core.lookup.definition.DefinitionLookupManager;

@Remote
public interface DefinitionLookupService extends DefinitionLookupManager {
    
}
