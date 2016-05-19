package org.wirez.backend.diagram.lookup;

import org.wirez.core.api.DiagramManager;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.lookup.AbstractLookupManager;
import org.wirez.core.lookup.diagram.DiagramLookupManager;
import org.wirez.core.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.lookup.diagram.DiagramRepresentation;
import org.wirez.core.lookup.diagram.DiagramRepresentationImpl;
import org.wirez.core.backend.annotation.Application;

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
