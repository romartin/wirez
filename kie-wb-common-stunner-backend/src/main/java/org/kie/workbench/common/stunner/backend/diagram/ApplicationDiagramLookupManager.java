package org.kie.workbench.common.stunner.backend.diagram;

import org.kie.workbench.common.stunner.core.api.DiagramManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.lookup.AbstractLookupManager;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupManager;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramLookupRequest;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramRepresentation;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramRepresentationImpl;
import org.kie.workbench.common.stunner.core.backend.annotation.Application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ApplicationDiagramLookupManager 
        extends AbstractLookupManager<Diagram, DiagramRepresentation, DiagramLookupRequest> 
        implements DiagramLookupManager {

    DiagramManager<Diagram> appDiagramManager;

    @Inject
    public ApplicationDiagramLookupManager(@Application DiagramManager<Diagram> appDiagramManager) {
        this.appDiagramManager = appDiagramManager;
    }


    @Override
    protected List<Diagram> getItems( final DiagramLookupRequest request ) {
        return new LinkedList<>( appDiagramManager.getItems() );
    }

    @Override
    protected boolean matches(final String criteria, final Diagram item) {
        return true;
    }

    @Override
    protected DiagramRepresentation buildResult(final Diagram item) {
        return new DiagramRepresentationImpl.DiagramRepresentationBuilder( item ).build();
    }

}
