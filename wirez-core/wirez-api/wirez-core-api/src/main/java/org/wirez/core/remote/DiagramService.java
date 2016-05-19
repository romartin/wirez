package org.wirez.core.remote;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.diagram.Diagram;

@Remote
public interface DiagramService extends DiagramManager<Diagram> {

    void saveOrUpdate( Diagram diagram );
    
}
