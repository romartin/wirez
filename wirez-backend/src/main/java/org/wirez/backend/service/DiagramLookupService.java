package org.wirez.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.backend.diagram.lookup.ApplicationDiagramLookupManager;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.backend.annotation.Application;

import javax.inject.Inject;

@Service
public class DiagramLookupService extends ApplicationDiagramLookupManager implements org.wirez.core.api.remote.DiagramLookupService {

    @Inject
    public DiagramLookupService(@Application DiagramManager<Diagram> appDiagramManager) {
        super(appDiagramManager);
    }
    
}
