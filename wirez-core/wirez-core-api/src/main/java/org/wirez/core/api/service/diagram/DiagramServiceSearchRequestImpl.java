package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DiagramServiceSearchRequestImpl implements DiagramServiceSearchRequest {
    
    final String query;

    public DiagramServiceSearchRequestImpl(@MapsTo("query") String query) {
        this.query = query;
    }

    @Override
    public String getSearchQuery() {
        return query;
    }
    
}
