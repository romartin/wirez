package org.wirez.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.lookup.definition.DefinitionLookupManager;

@Remote
public interface DefinitionLookupService extends DefinitionLookupManager {
    
}
