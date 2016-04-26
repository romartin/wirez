package org.wirez.core.backend.diagram.lookup;

import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.lookup.AbstractLookupManager;
import org.wirez.core.api.lookup.diagram.DiagramLookupManager;
import org.wirez.core.api.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.api.lookup.diagram.DiagramRepresentation;
import org.wirez.core.api.lookup.diagram.DiagramRepresentationImpl;
import org.wirez.core.backend.Application;

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
