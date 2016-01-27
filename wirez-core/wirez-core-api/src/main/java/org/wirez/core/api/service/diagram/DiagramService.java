package org.wirez.core.api.service.diagram;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.service.ServiceResponse;

// TODO: update(Diagram diagram, Command... commands) -> to provide incremental storage
@Remote
public interface DiagramService {

    DiagramServiceResponse create(DiagramServiceCreateRequest request);

    DiagramServiceResponse load(DiagramServiceLoadRequest request);

    ServiceResponse save(DiagramServiceSaveRequest request);

    ServiceResponse delete(DiagramServiceDeleteRequest request);
    
}
