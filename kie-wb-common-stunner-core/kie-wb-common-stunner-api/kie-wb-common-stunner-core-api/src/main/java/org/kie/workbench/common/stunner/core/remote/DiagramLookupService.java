package org.kie.workbench.common.stunner.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupManager;

@Remote
public interface DiagramLookupService extends DiagramLookupManager {
    
}
