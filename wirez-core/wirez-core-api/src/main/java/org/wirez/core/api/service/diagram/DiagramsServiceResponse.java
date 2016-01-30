package org.wirez.core.api.service.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.service.ServiceResponse;

import java.util.Collection;

// TODO: Pagination, etc
public interface DiagramsServiceResponse extends ServiceResponse {
    
    Collection<Diagram> getDiagrams();
    
}
