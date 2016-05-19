package org.wirez.core.lookup.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.lookup.AbstractLookupRequest;
import org.wirez.core.lookup.AbstractLookupRequestBuilder;

@Portable
public final class DiagramLookupRequestImpl extends AbstractLookupRequest implements DiagramLookupRequest {
    
    public DiagramLookupRequestImpl(@MapsTo("criteria") String criteria,
                                    @MapsTo("page") int page,
                                    @MapsTo("pageSize") int pageSize) {
        super(criteria, page, pageSize);
    }
    
    // TODO: Add more lookup criteria here.
    @NonPortable
    public static class Builder extends AbstractLookupRequestBuilder<Builder> {

        public DiagramLookupRequest build( ) {
            return new DiagramLookupRequestImpl( "", page, pageSize );
        }

    }
}
