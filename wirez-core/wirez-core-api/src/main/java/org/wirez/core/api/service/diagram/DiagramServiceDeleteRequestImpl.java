package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DiagramServiceDeleteRequestImpl implements DiagramServiceDeleteRequest{
    
    final String uuid;

    public DiagramServiceDeleteRequestImpl(@MapsTo("uuid") String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return uuid;
    }
}
