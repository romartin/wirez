package org.wirez.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.lookup.diagram.DiagramLookupManager;

@Remote
public interface DiagramLookupService extends DiagramLookupManager {
    
}
