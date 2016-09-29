package org.kie.workbench.common.stunner.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.workbench.common.stunner.core.api.DiagramManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;

@Remote
public interface DiagramService extends DiagramManager<Diagram> {

    void saveOrUpdate( Diagram diagram );
    
}
