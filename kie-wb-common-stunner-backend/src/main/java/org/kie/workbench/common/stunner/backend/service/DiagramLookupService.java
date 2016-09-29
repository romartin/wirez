package org.kie.workbench.common.stunner.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.workbench.common.stunner.backend.diagram.ApplicationDiagramLookupManager;
import org.kie.workbench.common.stunner.core.api.DiagramManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.backend.annotation.Application;

import javax.inject.Inject;

@Service
public class DiagramLookupService extends ApplicationDiagramLookupManager implements org.kie.workbench.common.stunner.core.remote.DiagramLookupService {

    @Inject
    public DiagramLookupService(@Application DiagramManager<Diagram> appDiagramManager) {
        super(appDiagramManager);
    }
    
}
