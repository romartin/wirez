package org.wirez.core.api.service.diagram;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;

// TODO: update(Diagram diagram, Command... commands) -> to provide incremental storage
@Remote
public interface DiagramService {

    DiagramServiceResponse create(String defSetId, String shapeSetId, String title);

    DiagramServiceResponse get(String uuid);
    
    void save(Diagram diagram);
    
    Diagram delete(String uuid);
    
}
