package org.wirez.core.api.lookup.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.lookup.AbstractLookupRequest;

@Portable
public final class DiagramLookupRequestImpl extends AbstractLookupRequest implements DiagramLookupRequest {
    
    public DiagramLookupRequestImpl(@MapsTo("criteria") String criteria,
                                    @MapsTo("page") int page,
                                    @MapsTo("pageSize") int pageSize) {
        super(criteria, page, pageSize);
    }
    
}
