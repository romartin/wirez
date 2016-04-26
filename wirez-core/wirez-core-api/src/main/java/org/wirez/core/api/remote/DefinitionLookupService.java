package org.wirez.core.api.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.lookup.definition.DefinitionLookupManager;

@Remote
public interface DefinitionLookupService extends DefinitionLookupManager {
    
}
