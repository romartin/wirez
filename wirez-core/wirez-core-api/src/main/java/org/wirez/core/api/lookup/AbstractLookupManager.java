package org.wirez.core.api.lookup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractLookupManager<I, T, R extends LookupManager.LookupRequest> implements LookupManager<T, R> {
    
    protected abstract List<I> getItems( R request ); 
    
    protected abstract boolean matches( String criteria, I item );
    
    protected abstract T buildResult( I item );
    
    @Override
    public LookupResponse<T> lookup(final R request) {
        
        final List<I> items = getItems( request );
        if ( null != items ) {

            if ( !items.isEmpty() ) {
                final int page = request.getPage();
                final int pageSize = request.getPageSize();
                final int from = page * pageSize;
                final String criteria = request.getCriteria();
                
                // Criteria filtering.
                List<I> result = new LinkedList<>();
                for ( I item : items ) {
                    if ( matches( criteria, item) ) {
                        result.add( item );
                    }
                }

                // Pagination checks.
                if ( result.size() < from ) {
                    throw new IllegalArgumentException("Specified lookup request page [" + from + "] cannot be used, as there are no many results.");
                }
                final int to = result.size() < ( from + pageSize ) ? result.size() : ( from + pageSize );
                
                // Apply pagination.
                result = result.subList( from, to );

                // Build results.
                List<T> representations = new LinkedList<>();
                for ( I item : result ) {
                    T rep = buildResult( item );
                    representations.add( rep );
                }

                return new LookupResponseImpl<T>( representations, representations.size(),
                        items.size() > to , request.getCriteria(), request.getPage(), request.getPageSize() );

            }
            
        }
        
        return buildEmptyResponse( request );
    }
    
    protected LookupResponse<T> buildEmptyResponse(R request ) {

        return new LookupResponseImpl<T>( new ArrayList<T>(),
                0, false, request.getCriteria(), request.getPage(), request.getPageSize());

    }
    
}
