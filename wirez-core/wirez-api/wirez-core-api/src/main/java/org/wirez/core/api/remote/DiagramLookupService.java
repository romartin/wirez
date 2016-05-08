package org.wirez.core.api.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.lookup.diagram.DiagramLookupManager;

@Remote
public interface DiagramLookupService extends DiagramLookupManager {
    
}
