package org.wirez.core.backend.diagram.lookup;

import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.lookup.LookupResponseImpl;
import org.wirez.core.api.lookup.diagram.DiagramLookupManager;
import org.wirez.core.api.lookup.diagram.DiagramLookupRequest;
import org.wirez.core.api.lookup.diagram.DiagramRepresentation;
import org.wirez.core.api.lookup.diagram.DiagramRepresentationImpl;
import org.wirez.core.backend.Application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ApplicationDiagramLookupManager implements DiagramLookupManager {
    
    DiagramManager<Diagram> appDiagramManager;

    @Inject
    public ApplicationDiagramLookupManager(@Application DiagramManager<Diagram> appDiagramManager) {
        this.appDiagramManager = appDiagramManager;
    }

    @Override
    public LookupResponse<DiagramRepresentation> lookup( DiagramLookupRequest request ) {
        
        String criteria = request.getCriteria();
        boolean isLookupAll = criteria == null || criteria.trim().length() == 0;
        
        if ( isLookupAll ) {

            List<Diagram> diagrams = new LinkedList<>( appDiagramManager.getItems() );

            if ( !diagrams.isEmpty() ) {
                final int page = request.getPage();
                final int pageSize = request.getPageSize();
                final int from = page * pageSize;
                if ( diagrams.size() < from ) {
                    throw new IllegalArgumentException("Specified lookup request page [" + from + "] cannot be used, as there are no many results.");
                }
                final int to = diagrams.size() < ( from + pageSize ) ? diagrams.size() : ( from + pageSize );

                List<Diagram> result = diagrams.subList( from, to );

                List<DiagramRepresentation> representations = new LinkedList<>();
                for ( Diagram diagram : result ) {

                    DiagramRepresentationImpl.DiagramRepresentationBuilder builder = 
                            new DiagramRepresentationImpl.DiagramRepresentationBuilder( diagram);
                    
                    DiagramRepresentation representation = builder.build();
                    
                    representations.add( representation );
                }

                return new LookupResponseImpl<DiagramRepresentation>( representations, representations.size(),
                        diagrams.size() > to , request.getCriteria(), request.getPage(), request.getPageSize() );
                
            }
            
        } else {
            
            throw new UnsupportedOperationException("Lookup using a concrete criteria is not supported yet.");
            
        }


        return buildEmptyResponse( request );
    }
    
    private static LookupResponse<DiagramRepresentation> buildEmptyResponse( DiagramLookupRequest request ) {
        
        return new LookupResponseImpl<DiagramRepresentation>( new ArrayList<DiagramRepresentation>(), 
                0, false, request.getCriteria(), request.getPage(), request.getPageSize());
        
    }
    
}
