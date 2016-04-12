package org.wirez.core.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.backend.Application;
import org.wirez.core.backend.diagram.lookup.ApplicationDiagramLookupManager;

import javax.inject.Inject;

@Service
public class DiagramLookupService extends ApplicationDiagramLookupManager implements org.wirez.core.api.remote.DiagramLookupService {

    @Inject
    public DiagramLookupService(@Application DiagramManager<Diagram> appDiagramManager) {
        super(appDiagramManager);
    }
    
}
